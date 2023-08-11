package com.sky.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sky.constant.OrdersConstant;
import com.sky.entity.Orders;
import com.sky.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/15 17:41:08
 * @Description
 */
@Slf4j
@Component
public class OrderTask {


    @Autowired
    private OrdersService ordersService;


    /**
     * 处理支付超时订单
     * 定时任务，待支付处理，获取全部待支付的订单，
     * 和当前时间>30 min , 设置为取消
     * 每3分钟执行一次
     */
  /*  @Scheduled(cron = "* 0/3 * * * ? ")*/
    public void orderPayStatusOn_1() {
        ArrayList<Orders> updateOrderList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now().plusMinutes(-30);
        List<Orders> ordersList = ordersService.listByStatus(OrdersConstant.STATUS_1);
        if (CollUtil.isNotEmpty(ordersList)) {
            //判断，下单时间 + 30 min 是否小于 当前时间，判断是否超时支付 30 min
            ordersList.forEach(order -> {
                LocalDateTime orderTime = order.getOrderTime();
                LocalDateTime plus30OrderTime = orderTime.plusMinutes(30);
                if (now.isAfter(plus30OrderTime)) {
                    order.setStatus(OrdersConstant.STATUS_6);
                    order.setPayStatus(OrdersConstant.PAYSTATUS_0);
                }
                updateOrderList.add(order);
            });
        }
        log.info("定时任务自动取消未支付订单：{}", updateOrderList.toString());
        boolean cronOk = ordersService.updateBatchById(ordersList);
        log.info("定时任务自动取消未支付订单是否成功：{}", cronOk);
    }


    /**
     * 处理派送中订单
     * 1个小时执行一次, 如果超时12个小时，设置为已完成
     */
 /*   @Scheduled(cron = "* * 0/1 * * ? ")*/
    public void orderPayStatusOn_4() {
        ArrayList<Orders> updateOrderList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        List<Orders> ordersList = ordersService.listByStatus(OrdersConstant.STATUS_4);
        if (CollUtil.isNotEmpty(ordersList)) {
            ordersList.forEach(order -> {
                LocalDateTime estimatedDeliveryTime = order.getEstimatedDeliveryTime();
                if (ObjectUtil.isNotNull(estimatedDeliveryTime)) {
                    LocalDateTime estimatedDeliveryTimeAfter12 = estimatedDeliveryTime.plusHours(12);
                    if (now.isAfter(estimatedDeliveryTimeAfter12)) {
                        order.setStatus(OrdersConstant.STATUS_5);
                        updateOrderList.add(order);
                    }
                }
            });
        }
        log.info("定时任务自动送达订单：{}", updateOrderList.toString());
        boolean cronOk = ordersService.updateBatchById(ordersList);
        log.info("定时任务自动送达订单是否成功：{}", cronOk);
    }

}
