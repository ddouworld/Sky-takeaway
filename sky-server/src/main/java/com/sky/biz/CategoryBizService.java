package com.sky.biz;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @author: 尹志伟
 * @date: 2023-06-30 16:53
 * @Description TODO
 **/
public interface CategoryBizService {
    /**
     * 分类管理 - 分页
     *
     * @param dto
     * @return
     */
    PageResult page(CategoryPageQueryDTO dto);

    /**
     * 分类管理-新增
     *
     * @param categoryDTO 新增对象
     * @return
     */
    Boolean save(CategoryDTO categoryDTO);

    /**
     * 分类管理 - 根据ID修改状态
     *
     * @param status 状态值
     * @param id     分类ID
     * @return
     */
    Boolean updateStatusById(Integer status, Integer id);

    /**
     * 分类管理 - 根据ID删除
     *
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);

    /**
     * 分类管理-修改
     *
     * @return
     */
    Boolean updateById(CategoryDTO categoryDTO);


    /**
     * 根据 CategoryPageQueryDTO 条件查询
     *
     * @param dto
     * @return
     */
    List<CategoryDTO> listByCategoryPageQueryDTO(CategoryPageQueryDTO dto);
}
