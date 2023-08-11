package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.OrderDetail;

import java.util.List;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 根据订单ID 查询订单详情
     *
     * @param orderId 订单ID
     * @return
     */
    List<OrderDetail> listByOrderId(Long orderId);
}
