package com.sky.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-28
 */
public interface OrdersDao extends BaseMapper<Orders> {

    /**
     * 查询销量排名top10接口
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    List<Map<String, Object>> top10LimitNumDesc(@Param("begin") String begin, @Param("end") String end,  @Param("limit")int limit);
}
