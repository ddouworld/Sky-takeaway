package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.DishFlavor;

import java.util.List;


/**
 * <p>
 * 菜品口味关系表 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface DishFlavorService extends IService<DishFlavor> {

    /**
     * 查询口味 - 根据菜品ID
     *
     * @param dishId 菜品ID
     * @return
     */
    List<DishFlavor> listByDishId(Long dishId);

    /**
     * 口味  - 根据 Dish菜品ID 进行删除
     *
     * @param idList
     */
    void removeByDishList(List<Long> idList);

    /**
     * 口味 - 根据口味ID进行删除数据
     *
     * @param dishId
     */
    void deleteByDishId(Long dishId);
}
