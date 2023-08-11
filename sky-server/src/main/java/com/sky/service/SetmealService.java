package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 套餐 - 分页
     *
     * @param page        当前页码
     * @param pageSize    分页数
     * @param setmealDTO  查询对象
     * @return
     */
    Page<Setmeal> pageBySetmealDTO(int page, int pageSize, SetmealDTO setmealDTO);

    /**
     * 套餐管理 - 根据分类ID查询套餐列表
     *
     * @param categoryId 分类ID
     * @return
     */
    List<Setmeal> listByCategoryId(Integer categoryId);


    /**
     * 根据状态值查询数量
     *
     * @param status 状态值
     * @return
     */
    Integer countByStatus(Integer status);
}
