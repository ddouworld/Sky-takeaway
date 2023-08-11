package com.sky.biz.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.sky.biz.WorkspaceBizService;
import com.sky.constant.OrdersConstant;
import com.sky.constant.StatusConstant;
import com.sky.service.DishService;
import com.sky.service.OrdersService;
import com.sky.service.SetmealService;
import com.sky.service.UserService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 尹志伟
 * @date 2023/7/17 01:18:37
 * @Description
 */
@Slf4j
@Service
public class WorkspaceBizServiceImpl implements WorkspaceBizService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 查询今日运营数据
     *
     * @return
     */
    @Override
    public BusinessDataVO businessData() {
        //获取今日日期
        DateTime date = DateUtil.date();
        String begin = date + " 00:00:00";
        String end = date + " 23:59:59";
        //新增用户数
        Integer newUsers = userService.countByDate(begin, end);
        //获取订单总数
        Integer totalOrderCount = ordersService.countByDateAndStatus(begin, end, null);
        //获取有效订单数量
        Integer validOrderCount = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_5);
        //订单完成率
        double completionRateOrder = 0.0;
        if (totalOrderCount > 0) {
            completionRateOrder = (double) ((validOrderCount / totalOrderCount));
        }
        //今日营业额
        BigDecimal sumAmount = ordersService.getSumAmountByDateAndStatus(begin, end, OrdersConstant.STATUS_5);
        //平均客单价
        double unitPrice = 0.0;
        if (newUsers > 0 && sumAmount.intValue() > 0) {
            unitPrice = sumAmount.doubleValue() / newUsers;
        }

        //构造VO返回
        return BusinessDataVO.builder()
                .newUsers(newUsers)
                .orderCompletionRate(completionRateOrder)
                .turnover(sumAmount.doubleValue())
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount).build();
    }

    /**
     * 根据日期查询运营数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime beginTime, LocalDateTime endTime) {
        String begin = beginTime.toString();
        String end = endTime.toString();
        //新增用户数
        Integer newUsers = userService.countByDate(begin, end);
        //获取订单总数
        Integer totalOrderCount = ordersService.countByDateAndStatus(begin, end, null);
        //获取有效订单数量
        Integer validOrderCount = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_5);
        //订单完成率
        double completionRateOrder = 0.0;
        if (totalOrderCount > 0) {
            completionRateOrder = (double) ((validOrderCount / totalOrderCount));
        }
        //今日营业额
        BigDecimal sumAmount = ordersService.getSumAmountByDateAndStatus(begin, end, OrdersConstant.STATUS_5);
        //平均客单价
        double unitPrice = 0.0;
        if (newUsers > 0 && sumAmount.intValue() > 0) {
            unitPrice = sumAmount.doubleValue() / newUsers;
        }

        //构造VO返回
        return BusinessDataVO.builder()
                .newUsers(newUsers)
                .orderCompletionRate(completionRateOrder)
                .turnover(sumAmount.doubleValue())
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount).build();
    }

    /**
     * 查询订单管理数据
     *
     * @return
     */
    @Override
    public OrderOverViewVO overviewOrders() {
        //获取今日日期
        DateTime date = DateUtil.date();
        String begin = date + " 00:00:00";
        String end = date + " 23:59:59";
        //全部订单
        Integer allOrders = ordersService.countByDateAndStatus(begin, end, null);
        //已取消数量
        Integer cancelledOrders = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_6);
        //已完成数量
        Integer completedOrders = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_5);
        //待派送数量
        Integer deliveredOrders = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_3);
        //待接单数量
        Integer waitingOrders = ordersService.countByDateAndStatus(begin, end, OrdersConstant.STATUS_2);
        return OrderOverViewVO.builder()
                .allOrders(allOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders)
                .waitingOrders(waitingOrders).build();
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    @Override
    public DishOverViewVO overviewDishes() {
        return DishOverViewVO.builder().discontinued(dishService.countByStatus(StatusConstant.DISABLE))
                .sold(dishService.countByStatus(StatusConstant.ENABLE)).build();
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    @Override
    public SetmealOverViewVO overviewSetmeals() {
        return SetmealOverViewVO.builder().discontinued(setmealService.countByStatus(StatusConstant.DISABLE))
                .sold(setmealService.countByStatus(StatusConstant.ENABLE)).build();
    }
}
