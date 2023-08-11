package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;

import java.util.List;


/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分页 - 根据category相关参数
     *
     * @param pageNum       当前页码
     * @param pageSize      分页数
     * @param categoryDTO   分页查询条件对象
     * @return
     */
    Page<Category> pageByCategoryDto(int pageNum, int pageSize, CategoryDTO categoryDTO);


    /**
     * 查询 - 根据 CategoryPageQueryDTO 对象属性进行查询
     *
     * @param dto CategoryPageQueryDTO
     * @return
     */
    List<Category> listByCategoryPageQueryDTO(CategoryPageQueryDTO dto);
}
