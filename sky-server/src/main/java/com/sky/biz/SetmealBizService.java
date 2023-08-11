package com.sky.biz;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/5 20:42:54
 * @Description 套餐管理
 */
public interface SetmealBizService {
    /**
     * 套餐管理 - 分页
     *
     * @param dto 套餐查询对象
     * @return
     */
    PageResult pageByQueryDTO(SetmealPageQueryDTO dto);

    /**
     * 套餐管理 - 修改
     *
     * @param status
     * @param id
     * @return
     */
    Boolean updateStatus(Integer status, Long id);

    /**
     * 根据ID进行删除套餐
     *
     * @param idList
     */
    void deleteByIdList(List<Long> idList);

    /**
     * 套餐管理 - 新增
     *
     * @param setmealDTO
     * @return
     */
    Boolean save(SetmealDTO setmealDTO);

    /**
     * 套餐管理 - 根据 ID 查询
     *
     * @param id 主键 ID
     * @return
     */
    SetmealVO findById(Integer id);

    /**
     * 套餐管理 - 根据分类ID查询套餐列表
     *
     * @param categoryId 分类ID
     * @return
     */
    List<SetmealVO> listByCategoryId(Integer categoryId);

    /**
     * 根据套餐ID查询包含的菜品信息
     *
     * @param id 套餐ID
     * @return
     */
    List<DishItemVO> listDishById(Long id);
}
