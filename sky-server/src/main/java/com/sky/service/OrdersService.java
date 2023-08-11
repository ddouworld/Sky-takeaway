package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.Orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     * @return
     */
    Orders findByOrderNumber(String orderNumber);

    /**
     * 查询历史订单
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param status   状态值
     * @return
     */
    Page<Orders> pageInfoHistoryOrders(Integer page, Integer pageSize, Long userId, Integer status);

    /**
     * 根据状态查询全部订单
     *
     * @param status 状态值
     * @return
     */
    List<Orders> listByStatus(Integer status);

    /**
     * 计算日期范围内的 总营业额
     *
     * @param dateStrBegin 开始时间
     * @param dateStrEnd   结束时间
     * @return
     */
    BigDecimal getSumAmountByDateAndStatus(String dateStrBegin, String dateStrEnd, Integer orderStatus);


    /**
     * 获取数量，根据日期范围 和状态值
     *
     * @param begin
     * @param end
     * @param status
     * @return
     */
    Integer countByDateAndStatus(String begin, String end, Integer status);

    /**
     * 查询销量排名top10接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    List<Map<String, Object>> top10LimitNumDesc(String begin, String end, int i);
}
