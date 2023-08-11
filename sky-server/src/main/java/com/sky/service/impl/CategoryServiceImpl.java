package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryDao;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 分页 - 根据category相关参数
     *
     * @param pageNum     当前页码
     * @param pageSize    分页数
     * @param categoryDTO 分页查询条件对象
     * @return
     */
    @Override
    public Page<Category> pageByCategoryDto(int pageNum, int pageSize, CategoryDTO categoryDTO) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(categoryDTO)) {
            String name = categoryDTO.getName();
            Integer type = categoryDTO.getType();
            queryWrapper.eq(ObjectUtil.isNotNull(type), Category::getType, type)
                    .like(StrUtil.isNotBlank(name), Category::getName, name)
                    .orderByDesc(Category::getSort);
        }
        return page(page, queryWrapper);
    }


    /**
     * 查询 - 根据 CategoryPageQueryDTO 对象属性进行查询
     *
     * @param dto CategoryPageQueryDTO
     * @return
     */
    @Override
    public List<Category> listByCategoryPageQueryDTO(CategoryPageQueryDTO dto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(dto)) {
            Integer type = dto.getType();
            String name = dto.getName();
            queryWrapper.eq(ObjectUtil.isNotNull(type), Category::getType, type)
                    .like(StrUtil.isNotBlank(name), Category::getName, name)
                    .orderByDesc(Category::getSort);
        }
        return list(queryWrapper);
    }


}
