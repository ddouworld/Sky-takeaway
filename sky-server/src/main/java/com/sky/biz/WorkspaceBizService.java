package com.sky.biz;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

/**
 * @author 尹志伟
 * @date 2023/7/17 01:18:28
 * @Description
 */
public interface WorkspaceBizService {

    /**
     * 查询今日运营数据
     *
     * @return
     */
    BusinessDataVO businessData();

    /**
     * 根据日期查询运营数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 查询订单管理数据
     *
     * @return
     */
    OrderOverViewVO overviewOrders();

    /**
     * 查询菜品总览
     *
     * @return
     */
    DishOverViewVO overviewDishes();

    /**
     * 查询套餐总览
     *
     * @return
     */
    SetmealOverViewVO overviewSetmeals();


}
