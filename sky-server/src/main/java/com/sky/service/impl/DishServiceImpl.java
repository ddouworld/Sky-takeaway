package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishDao;
import com.sky.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜品 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {

    @Autowired
    private DishDao dishDao;

    /**
     * 菜品管理 - 分页
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param dishDTO  查询对象 DTO
     * @return
     */
    @Override
    public Page<Dish> pageByDishDTO(int page, int pageSize, DishDTO dishDTO) {
        Page<Dish> pageDB = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(dishDTO)) {
            String name = dishDTO.getName();
            Long categoryId = dishDTO.getCategoryId();
            Integer status = dishDTO.getStatus();
            queryWrapper.like(StrUtil.isNotBlank(name), Dish::getName, name)
                    .eq(ObjectUtil.isNotNull(categoryId), Dish::getCategoryId, categoryId)
                    .eq(ObjectUtil.isNotNull(status), Dish::getStatus, status)
                    .orderByDesc(Dish::getId, Dish::getCreateTime);
        }
        return dishDao.selectPage(pageDB, queryWrapper);
    }


    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    @Override
    public List<Dish> listByCategoryId(Integer categoryId) {
        if (ObjectUtil.isNotNull(categoryId)) {
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Dish::getCategoryId, categoryId)
                    .orderByDesc(Dish::getId, Dish::getCreateTime);
            return list(queryWrapper);
        }
        return null;
    }

    /**
     * 根据菜品ID查询图片
     *
     * @param dishId 菜品ID
     * @return
     */
    @Override
    public String getImageById(Long dishId) {
        if (ObjectUtil.isNotNull(dishId)) {
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(Dish::getImage)
                    .eq(Dish::getId, dishId);
            Dish dish = dishDao.selectOne(queryWrapper);
            if (ObjectUtil.isNotNull(dish)) {
                return dish.getImage();
            }
        }
        return null;
    }

    /**
     * 根据状态值查询数量
     *
     * @param status 状态值
     * @return
     */
    @Override
    public Integer countByStatus(Integer status) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getStatus, status);
        return count(queryWrapper);
    }
}
