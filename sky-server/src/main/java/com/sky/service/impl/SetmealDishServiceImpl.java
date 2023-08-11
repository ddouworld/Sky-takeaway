package com.sky.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishDao;
import com.sky.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 套餐菜品关系 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishDao, SetmealDish> implements SetmealDishService {

    @Autowired
    private SetmealDishDao setmealDishDao;

    /**
     * 根据 dishIds 查询套餐数量
     *
     * @param dishIds
     * @return
     */
    @Override
    public Integer countByDishIds(List<Long> dishIds) {
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (CollUtil.isNotEmpty(dishIds)) {
            lambdaQueryWrapper.in(SetmealDish::getDishId, dishIds);
            return count(lambdaQueryWrapper);
        }
        return 0;
    }


    /**
     * 根据套餐ID 查询口味
     *
     * @param setmealId 套餐ID
     * @return
     */
    @Override
    public List<SetmealDish> listBySetmealId(Long setmealId) {
        if (ObjectUtil.isNotNull(setmealId)) {
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
            return list(queryWrapper);
        }
        return null;
    }
}
