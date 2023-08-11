package com.sky.biz;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/5 00:12:04
 * @Description
 */
public interface DishBizService {
    /**
     * 菜品管理 - 分页
     *
     * @param dto
     * @return
     */
    PageResult pageByQueryDTO(DishPageQueryDTO dto);

    /**
     * 菜品管理 - 新增
     *
     * @param dishDTO
     * @return
     */
    Boolean saveDishDTO(DishDTO dishDTO);

    /**
     * 菜品管理 - 根据 ID 主键集合进行删除
     *
     * @param idList ID主键集合
     * @return
     */
    Integer deleteByIdList(List<Long> idList);

    /**
     * 菜品管理 - 根据ID查询
     *
     * @param id 菜品ID
     * @return
     */
    DishDTO getById(Long id);

    /**
     * 菜品管理-菜品起售||停售
     *
     * @param status  状态
     * @param id      菜品ID
     * @return
     */
    Boolean updateStatus(Integer status, Long id);


    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    List<Dish> listByCategoryId(Integer categoryId);

    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    List<DishVO> listDTOByCategoryId(Integer categoryId);

    /**
     * 菜品管理 - 修改
     *
     * @param dishDTO
     * @return
     */
    Boolean update(DishDTO dishDTO);
}
