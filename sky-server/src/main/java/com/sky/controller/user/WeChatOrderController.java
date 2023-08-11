package com.sky.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.base.BaseController;
import com.sky.biz.OrderBizService;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.result.Result;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description C端 - 订单
 */
@RestController
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "C端 - 订单")
public class WeChatOrderController extends BaseController {

    @Autowired
    private OrderBizService orderBizService;

    /**
     * C端-用户下订单
     *
     * @param ordersDTO
     * @return
     */
    @ApiOperation("C端-用户下订单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersDTO ordersDTO) {
        log.info("weChat submitOrder：{}, userID:{}", ordersDTO.toString(), BaseContext.getCurrentId());
        return Result.success(orderBizService.submitOrder(ordersDTO));
    }


    /**
     * C端-用户支付  本质是微信支付，由于缺少商户号，支付功能不做，直接写死支付，流程查询文档
     *
     * @param ordersPaymentDTO
     * @return
     */
    @ApiOperation("C端-用户支付")
    @PutMapping("/payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        log.info("weChat payment：{}, userID:{}", ordersPaymentDTO.toString(), BaseContext.getCurrentId());
        OrderPaymentVO orderPaymentVO = orderBizService.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }


    /**
     * C端-查询历史订单
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param status   状态值
     * @return
     */
    @ApiOperation("C端-查询历史订单")
    @GetMapping("/historyOrders")
    public Result<Page<OrderVO>> historyOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                               @RequestParam(value = "status", required = false) Integer status) {
        Long userId = BaseContext.getCurrentId();
        log.info("weChat historyOrders  page：{}, pageSize:{}, status:{} userID:{}", page, pageSize, status, userId);
        return Result.success(orderBizService.pageInfoHistoryOrders(page, pageSize, userId, status));
    }

    /**
     * C端-查询订单详情
     *
     * @param id 订单ID
     * @return
     */
    @ApiOperation("C端-查询订单详情")
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> orderDetailById(@PathVariable("id") Long id) {
        return Result.success(orderBizService.orderDetailById(id));
    }

    /**
     * C端-取消退款
     *
     * @param id 订单ID
     * @return
     */
    @ApiOperation("C端-取消退款")
    @PutMapping("/cancel/{id}")
    public Result<Boolean> cancelById(@PathVariable("id") Long id) {
        log.info("weChat cancelById  id:{}", id);
        return orderBizService.cancelById(id) ?
                successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * C端-催单
     *
     * @param id
     * @return
     */
    @ApiOperation("C端-催单")
    @GetMapping("/reminder/{id}")
    public Result<Boolean> reminder(@PathVariable("id") Long id) {
        orderBizService.reminder(id);
        return Result.success(Boolean.TRUE);
    }
}
