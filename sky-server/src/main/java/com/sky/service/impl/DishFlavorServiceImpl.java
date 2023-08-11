package com.sky.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorDao;
import com.sky.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜品口味关系表 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorDao, DishFlavor> implements DishFlavorService {

    @Autowired
    private DishFlavorDao dishFlavorDao;

    /**
     * 查询口味 - 根据菜品ID
     *
     * @param dishId 菜品ID
     * @return
     */
    @Override
    public List<DishFlavor> listByDishId(Long dishId) {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(dishId), DishFlavor::getDishId, dishId);
        return list(queryWrapper);
    }

    /**
     * 口味  - 根据 Dish菜品ID 进行删除
     *
     * @param idList
     */
    @Override
    public void removeByDishList(List<Long> idList) {
        //防止误删除，集合长度<0,不进行删除数据，不然全部没了
        if (CollUtil.isNotEmpty(idList)) {
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(DishFlavor::getDishId, idList);
            remove(queryWrapper);
        }
    }

    /**
     * 口味 - 根据口味ID进行删除数据
     *
     * @param dishId
     */
    @Override
    public void deleteByDishId(Long dishId) {
        if (ObjectUtil.isNotNull(dishId)) {
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,dishId);
            remove(queryWrapper);
        }
    }
}
