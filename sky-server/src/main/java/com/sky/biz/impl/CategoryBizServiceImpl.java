package com.sky.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.biz.CategoryBizService;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.custom.CategoryException;
import com.sky.exception.enums.CategoryExceptionEnum;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 尹志伟
 * @date: 2023-06-30 16:54
 * @Description TODO
 **/
@Service
@Slf4j
public class CategoryBizServiceImpl implements CategoryBizService {
    @Autowired
    private CategoryService categoryService;


    /**
     * 分类管理 - 分页
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO dto) {
        PageResult pageResult = new PageResult();
        if (ObjectUtil.isNotNull(dto)) {
            CategoryDTO category = new CategoryDTO();
            category.setName(dto.getName());
            category.setType(dto.getType());
            Page<Category> page = categoryService.pageByCategoryDto(dto.getPage(), dto.getPageSize(), category);
            if (ObjectUtil.isNotNull(page)) {
                pageResult.setTotal(page.getTotal());
                pageResult.setRecords(page.getRecords());
            }
        }
        return pageResult;
    }

    /**
     * 分类管理-新增
     *
     * @param categoryDTO 新增对象
     * @return
     */
    @Override
    public Boolean save(CategoryDTO categoryDTO) {
        if (ObjectUtil.isNotNull(categoryDTO)) {
            Category category = new Category();
            category.setType(categoryDTO.getType());
            category.setSort(categoryDTO.getSort());
            category.setStatus(StatusConstant.ENABLE);
            category.setName(categoryDTO.getName());
            return categoryService.save(category);
        }
        return false;
    }


    /**
     * 分类管理 - 根据ID修改状态
     *
     * @param status 状态值
     * @param id     分类ID
     * @return
     */
    @Override
    public Boolean updateStatusById(Integer status, Integer id) {
        if (ObjectUtil.isNotNull(id) && ObjectUtil.isNotNull(status)) {
            Category category = categoryService.getById(id);
            if (ObjectUtil.isNull(category)) {
                throw new CategoryException(CategoryExceptionEnum.FIND_CATEGORY_BY_ID_NULL);
            }
            category.setStatus(status);
            return categoryService.updateById(category);
        }
        return false;
    }


    /**
     * 分类管理 - 根据ID删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(Integer id) {
        if (ObjectUtil.isNotNull(id)) {
            return categoryService.removeById(id);
        }
        return false;
    }

    /**
     * 分类管理-修改
     *
     * @return
     */
    @Override
    public Boolean updateById(CategoryDTO categoryDTO) {
        if (ObjectUtil.isNotNull(categoryDTO)) {
            Long id = categoryDTO.getId();
            Category category = categoryService.getById(id);
            if (ObjectUtil.isNull(category)) {
                throw new CategoryException(CategoryExceptionEnum.FIND_CATEGORY_BY_ID_NULL);
            }
            category.setType(categoryDTO.getType());
            category.setName(categoryDTO.getName());
            category.setSort(categoryDTO.getSort());
            return categoryService.updateById(category);
        }
        return false;
    }


    /**
     * 根据 CategoryPageQueryDTO 条件查询
     *
     * @param dto
     * @return
     */
    @Override
    public List<CategoryDTO> listByCategoryPageQueryDTO(CategoryPageQueryDTO dto) {
        if (ObjectUtil.isNotNull(dto)) {
            return categoryService.listByCategoryPageQueryDTO(dto).stream()
                    .map(this::categoryToDTO).collect(Collectors.toList());
        }
        return null;
    }


    // ====================================== 私有方法 begin  ====================================

    /**
     * 类型转换方法
     * category -> categoryToDTO
     *
     * @param category
     * @return
     */
    private CategoryDTO categoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtil.copyProperties(category, categoryDTO);
        return categoryDTO;
    }


    // ====================================== 私有方法 end  ======================================

}
