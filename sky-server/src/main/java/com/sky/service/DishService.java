package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

import java.util.List;


/**
 * <p>
 * 菜品 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface DishService extends IService<Dish> {

    /**
     * 菜品管理 - 分页
     *
     * @param page     当前页码
     * @param pageSize 分页数
     * @param dishDTO  查询对象 DTO
     * @return
     */
    Page<Dish> pageByDishDTO(int page, int pageSize, DishDTO dishDTO);

    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    List<Dish> listByCategoryId(Integer categoryId);

    /**
     * 根据菜品ID查询图片
     *
     * @param dishId 菜品ID
     * @return
     */
    String getImageById(Long dishId);

    /**
     * 根据状态值查询数量
     *
     * @param status 状态值
     * @return
     */
    Integer countByStatus(Integer status);
}
