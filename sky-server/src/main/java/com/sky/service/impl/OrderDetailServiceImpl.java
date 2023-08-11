package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailDao;
import com.sky.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    /**
     * 根据订单ID 查询订单详情
     *
     * @param orderId 订单ID
     * @return
     */
    @Override
    public List<OrderDetail> listByOrderId(Long orderId) {
        if (ObjectUtil.isNotNull(orderId)) {
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId, orderId);
            return list(queryWrapper);
        }
        return null;
    }
}
