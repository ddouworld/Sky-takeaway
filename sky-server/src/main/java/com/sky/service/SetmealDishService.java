package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.SetmealDish;

import java.util.List;


/**
 * <p>
 * 套餐菜品关系 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface SetmealDishService extends IService<SetmealDish> {

    /**
     * 根据 dishIds 查询套餐数量
     *
     * @param dishIds
     * @return
     */
    Integer countByDishIds(List<Long> dishIds);

    /**
     * 根据套餐ID 查询口味
     *
     * @param setmealId 套餐ID
     * @return
     */
    List<SetmealDish> listBySetmealId(Long setmealId);
}
