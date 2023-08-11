package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Orders;
import com.sky.mapper.OrdersDao;
import com.sky.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;


    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     * @return
     */
    @Override
    public Orders findByOrderNumber(String orderNumber) {
        if (StrUtil.isNotBlank(orderNumber)) {
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getNumber, orderNumber);
            return getOne(queryWrapper);
        }
        return null;
    }


    /**
     * 查询历史订单
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param status   状态值
     * @return
     */
    @Override
    public Page<Orders> pageInfoHistoryOrders(Integer page, Integer pageSize, Long userId, Integer status) {
        if (ObjectUtil.isNotNull(userId)) {
            Page<Orders> pageInfo = new Page<>(page, pageSize);
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getUserId, userId)
                    .eq(ObjectUtil.isNotNull(status), Orders::getStatus, status)
                    .orderByDesc(Orders::getOrderTime, Orders::getId);
            return page(pageInfo, queryWrapper);
        }
        return null;
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
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotNull(status), Orders::getStatus, status)
                    .orderByDesc(Orders::getOrderTime, Orders::getId);
            return list(queryWrapper);
        }
        return null;
    }

    /**
     * 计算日期范围内的 总营业额
     *
     * @param dateStrBegin 开始时间
     * @param dateStrEnd   结束时间
     * @return
     */
    @Override
    public BigDecimal getSumAmountByDateAndStatus(String dateStrBegin, String dateStrEnd, Integer orderStatus) {
        BigDecimal amount = null;
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(amount) as amount");
        queryWrapper.between("order_time", dateStrBegin, dateStrEnd);
        queryWrapper.eq("status", orderStatus);
        Map<String, Object> map = getMap(queryWrapper);
        if (ObjectUtil.isNotNull(map)) {
            amount = (BigDecimal) map.get("amount");
        } else {
            amount = BigDecimal.valueOf(0);
        }
        return amount;
    }


    /**
     * 获取数量，根据日期范围 和状态值
     *
     * @param begin
     * @param end
     * @param status
     * @return
     */
    @Override
    public Integer countByDateAndStatus(String begin, String end, Integer status) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(status), Orders::getStatus, status)
                .between(Orders::getOrderTime, begin, end);
        return count(queryWrapper);
    }


    /**
     * 查询销量排名top10接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public List<Map<String, Object>> top10LimitNumDesc(String begin, String end, int limit) {
        return ordersDao.top10LimitNumDesc(begin, end, limit);
    }
}
