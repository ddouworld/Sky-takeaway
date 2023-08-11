package com.sky.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/12 22:02:34
 * @Description
 */
public interface OrderBizService {

    /**
     * C端-用户下订单
     *
     * @param ordersDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersDTO ordersDTO);


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
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);


    /**
     * C端-查询历史订单
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param status   状态值
     * @return
     */
    Page<OrderVO> pageInfoHistoryOrders(Integer page, Integer pageSize, Long userId, Integer status);

    /**
     * C端-查询订单详情
     *
     * @param id 订单ID
     * @return
     */
    OrderVO orderDetailById(Long id);

    /**
     * C端-取消退款
     *
     * @param id 订单ID
     * @return
     */
    Boolean cancelById(Long id);


    /**
     * 根据状态查询全部订单
     *
     * @param status  状态值
     * @return
     */
    List<Orders> listByStatus(Integer status);

    /**
     * C端-催单
     *
     * @param id
     * @return
     */
    void reminder(Long id);
}
