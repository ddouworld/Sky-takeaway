package com.sky.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.biz.OrderBizService;
import com.sky.constant.OrdersConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.custom.OrdersException;
import com.sky.exception.enums.OrdersExceptionEnum;
import com.sky.service.AddressBookService;
import com.sky.service.OrderDetailService;
import com.sky.service.OrdersService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 尹志伟
 * @date 2023/7/12 22:02:44
 * @Description
 */
@Slf4j
@Service
public class OrderBizServiceImpl implements OrderBizService {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * C端-用户下订单
     *
     * @param ordersDTO
     * @return
     */
    @Override
    public OrderSubmitVO submitOrder(OrdersDTO ordersDTO) {
        OrderSubmitVO vo = new OrderSubmitVO();
        if (ObjectUtil.isNotNull(ordersDTO)) {
            //验证订单地址ID，相关信息
            Long addressBookId = ordersDTO.getAddressBookId();
            if (ObjectUtil.isNull(addressBookId)) {
                throw new OrdersException(OrdersExceptionEnum.SUBMIT_ORDER_ERROR_ADDRESSBOOK_ID_IS_NULL);
            }
            AddressBook addressBook = addressBookService.getById(addressBookId);
            if (ObjectUtil.isNull(addressBook)) {
                throw new OrdersException(OrdersExceptionEnum.SUBMIT_ORDER_ERROR_ADDRESSBOOK_IS_NULL);
            }
            Orders orders = new Orders();
            orders.setNumber(IdUtil.simpleUUID());
            orders.setStatus(ordersDTO.getStatus());
            orders.setUserId(BaseContext.getCurrentId());
            //待付款
            orders.setStatus(OrdersConstant.STATUS_1);
            //微信
            orders.setPayMethod(OrdersConstant.PAYMETHOD_1);
            //未支付
            orders.setPayStatus(OrdersConstant.PAYSTATUS_0);
            orders.setAddressBookId(addressBookId);
            orders.setCheckoutTime(ordersDTO.getCheckoutTime());
            orders.setPayMethod(ordersDTO.getPayMethod());
            orders.setAmount(ordersDTO.getAmount());
            orders.setRemark(ordersDTO.getRemark());
            orders.setPhone(ordersDTO.getPhone());
            orders.setAddress(ordersDTO.getAddress());
            orders.setUserName(ordersDTO.getUserName());
            orders.setOrderTime(LocalDateTime.now());
            orders.setConsignee(ordersDTO.getConsignee());
            if (!ordersService.save(orders)) {
                log.error("submitOrder error, orders:{}", orders.toString());
                throw new OrdersException(OrdersExceptionEnum.SUBMIT_ORDER_ERROR_OF_DB);
            }
            //获取购物车数据
            List<ShoppingCart> cartList = shoppingCartService.listByUserId(orders.getUserId());
            if (CollUtil.isEmpty(cartList)) {
                throw new OrdersException(OrdersExceptionEnum.SUBMIT_ORDER_ERROR_SHOP_CART_IS_NULL);
            }
            ArrayList<OrderDetail> detailArrayList = new ArrayList<>(cartList.size());
            for (ShoppingCart shoppingCart : cartList) {
                OrderDetail detail = new OrderDetail();
                detail.setName(shoppingCart.getName());
                detail.setImage(shoppingCart.getImage());
                detail.setOrderId(orders.getId());
                detail.setDishId(shoppingCart.getDishId());
                detail.setSetmealId(shoppingCart.getSetmealId());
                detail.setDishFlavor(shoppingCart.getDishFlavor());
                detail.setNumber(shoppingCart.getNumber());
                detail.setAmount(shoppingCart.getAmount());
                detailArrayList.add(detail);
            }
            orderDetailService.saveBatch(detailArrayList);
            //清空购物车
            shoppingCartService.deleteByUserId(orders.getUserId());
            //构造VO返回
            vo.setId(orders.getId());
            vo.setOrderNumber(orders.getNumber());
            vo.setOrderTime(orders.getOrderTime());
            vo.setOrderAmount(orders.getAmount());
        }
        return vo;
    }


    /**
     * 微信支付
     * <p>
     * 1.微信用户，下完订单，点击付款，生成付款页面，请求自己系统的付款后台接口，返回自己生成的订单号(1.2.3 步)
     * 2.获取返回的订单号（自己系统生成的uuid,或者雪花），去调用微信统一下单接口，
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml 参考地址
     * 使用POST方式，去请求该接口，并且按照固定的参数进行传递，（参数如下图），请求成功返回
     * 预支付交易会话标识[prepay_id],预支付交易会话标识。用于后续接口调用中使用，该值有效期为2小时
     * 3.将prepay_id和其他的小程序支付所需要的参数一起加密处理，返回给小程序，调起微信小程序支付
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_4.shtml ,
     * 小程序就可以使用 调用wx.requestPayment(OBJECT)发起微信支付
     * 4.用户支付成功，会回调我们的接口，notify_url（必须是公网地址），会携带订单号和微信支付标识回来，我们
     * 可以通过此字段进行业务修改，业务处理完成之后，我们必须响应 成功 给微信端，不然会持续回调
     *
     * @param ordersPaymentDTO 支付签名数据：具体请看  weChatPayUtil.pay 方法
     * @return
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
        //@TODO 微信支付流程,其中需要再微信支付成功之后，回调里面，里面修改订单的状态的，我们在此地方进行修改，
        String orderNumber = ordersPaymentDTO.getOrderNumber();
        if (StrUtil.isNotBlank(orderNumber)) {
            Orders orders = ordersService.findByOrderNumber(orderNumber);
            if (ObjectUtil.isNotNull(orders)) {
                //已支付，待接单
                log.info("payment ok orderNumber:{} update status", orderNumber);
                orders.setPayStatus(OrdersConstant.PAYSTATUS_1);
                orders.setStatus(OrdersConstant.STATUS_2);
                ordersService.updateById(orders);
            }
            //webSocket 发送消息
            HashMap<String, Object> map = new HashMap<>(3);
            map.put("type", 1);
            map.put("orderId", orders.getId());
            map.put("content", "来单提醒：订单号:" + orderNumber + ",来单拉~请及时派送!");
            String message = JSON.toJSONString(map);
            webSocketServer.sendToAllClient(message);
        }
        return OrderPaymentVO.builder()
                .build();
    }


    /**
     * C端-查询历史订单
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param status   状态值
     * @return
     */
    @Override
    public Page<OrderVO> pageInfoHistoryOrders(Integer page, Integer pageSize, Long userId, Integer status) {
        Page<OrderVO> orderVOPage = new Page<>();
        if (ObjectUtil.isNotNull(userId)) {
            Page<Orders> ordersPage = ordersService.pageInfoHistoryOrders(page, pageSize, userId, status);
            if (ObjectUtil.isNotNull(ordersPage)) {
                BeanUtil.copyProperties(ordersPage, orderVOPage);
                List<Orders> ordersList = ordersPage.getRecords();
                if (CollUtil.isNotEmpty(ordersList)) {
                    orderVOPage.setRecords(ordersList.stream().map(this::orderToVo).collect(Collectors.toList()));
                }
            }
        }
        return orderVOPage;
    }

    /**
     * C端-查询订单详情
     *
     * @param id 订单ID
     * @return
     */
    @Override
    public OrderVO orderDetailById(Long id) {
        if (ObjectUtil.isNotNull(id)) {
            Orders orders = ordersService.getById(id);
            if (ObjectUtil.isNotNull(orders)) {
                return orderToVo(orders);
            }
        }
        return null;
    }

    /**
     * C端-取消退款
     *
     * @param id 订单ID
     * @return
     */
    @Override
    public Boolean cancelById(Long id) {
        //调用微信退款功能，TODO，在退款成功的回调里面去修改订单状态，这里就不操作了，直接修改状态
        Orders orders = ordersService.getById(id);
        if (ObjectUtil.isNotNull(orders)) {
            orders.setPayStatus(OrdersConstant.PAYSTATUS_2);
            orders.setStatus(OrdersConstant.STATUS_7);
            return ordersService.updateById(orders);
        }
        return Boolean.FALSE;
    }

    /**
     * 根据状态查询全部订单
     *
     * @param status 状态值
     * @return
     */
    @Override
    public List<Orders> listByStatus(Integer status) {
        if (ObjectUtil.isNotNull(status)) {
            return ordersService.listByStatus(status);
        }
        return null;
    }

    /**
     * C端-催单
     *
     * @param id
     * @return
     */
    @Override
    public void reminder(Long id) {
        Orders orders = ordersService.getById(id);
        if (ObjectUtil.isNotNull(orders)) {
            //webSocket 发送消息
            HashMap<String, Object> map = new HashMap<>(3);
            map.put("type", 2);
            map.put("orderId", orders.getId());
            map.put("content", "催单提醒：订单号:" + orders.getNumber() + ",催单拉~请及时处理此订单!");
            String message = JSON.toJSONString(map);
            webSocketServer.sendToAllClient(message);
        }
    }

    /**
     * 类型转换，Orders - OrderVO
     *
     * @param orders
     * @return
     */
    private OrderVO orderToVo(Orders orders) {
        OrderVO vo = new OrderVO();
        BeanUtil.copyProperties(orders, vo);
        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailService.listByOrderId(vo.getId());
        vo.setOrderDetailList(orderDetailList);
        return vo;
    }
}
