package com.sky.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.biz.SetmealBizService;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.custom.SetmealException;
import com.sky.exception.enums.SetmealExceptionEnum;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 尹志伟
 * @date 2023/7/5 20:43:11
 * @Description
 */
@Service
@Slf4j
public class SetmealBizServiceImpl implements SetmealBizService {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishService dishService;

    /**
     * 套餐管理 - 分页
     *
     * @param dto 套餐查询对象
     * @return
     */
    @Override
    public PageResult pageByQueryDTO(SetmealPageQueryDTO dto) {
        PageResult pageResult = new PageResult();
        if (ObjectUtil.isNotNull(dto)) {
            SetmealDTO setmealDTO = new SetmealDTO();
            Integer categoryId = dto.getCategoryId();
            if (ObjectUtil.isNotNull(categoryId)) {
                setmealDTO.setCategoryId(Long.valueOf(categoryId));
            }
            setmealDTO.setName(dto.getName());
            setmealDTO.setStatus(dto.getStatus());
            Page<Setmeal> page = setmealService.pageBySetmealDTO(dto.getPage(), dto.getPageSize(), setmealDTO);
            if (ObjectUtil.isNotNull(page)) {
                pageResult.setTotal(page.getTotal());
                pageResult.setRecords(page.getRecords().stream().map(this::SetmealToVo).collect(Collectors.toList()));
            }
        }
        return pageResult;
    }


    /**
     * 套餐管理-起售、停售
     *
     * @param status 状态值
     * @param id     ID值
     * @return
     */
    @Override
    @CacheEvict(value = "setmealCache",allEntries = true)
    public Boolean updateStatus(Integer status, Long id) {
        if (ObjectUtil.isNotNull(id)) {
            Setmeal setmeal = setmealService.getById(id);
            if (ObjectUtil.isNull(setmeal)) {
                throw new SetmealException(SetmealExceptionEnum.FIND_SETMEAL_BY_ID_NULL);
            }
            setmeal.setStatus(status);
            return setmealService.updateById(setmeal);
        }
        return false;
    }

    /**
     * 根据ID进行删除套餐
     *
     * @param idList
     */
    @Override
    @CacheEvict(value = "setmealCache",allEntries = true)
    public void deleteByIdList(List<Long> idList) {
        if (CollUtil.isNotEmpty(idList)) {
            //TODO  需要判断套餐和口味的关联关系，这里不做其他判断了..直接删除
            setmealService.removeByIds(idList);
        }
    }

    /**
     * 套餐管理 - 新增
     *
     * @param setmealDTO
     * @return
     */
    @Override
    @CacheEvict(value = "setmealCache",key = "#setmealDTO.categoryId")
    public Boolean save(SetmealDTO setmealDTO) {
        if (ObjectUtil.isNotNull(setmealDTO)) {
            Setmeal setmeal = new Setmeal();
            setmeal.setCategoryId(setmealDTO.getCategoryId());
            setmeal.setName(setmealDTO.getName());
            setmeal.setPrice(setmealDTO.getPrice());
            setmeal.setStatus(setmealDTO.getStatus());
            setmeal.setDescription(setmealDTO.getDescription());
            setmeal.setImage(setmealDTO.getImage());
            setmeal.setStatus(StatusConstant.ENABLE);
            boolean saveOk = setmealService.save(setmeal);
            if (!saveOk) {
                throw new SetmealException(SetmealExceptionEnum.SAVE_SETMEAL_ERROR);
            }
            //保存口味关系
            List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
            if (CollUtil.isNotEmpty(setmealDishes)) {
                setmealDishes.stream().forEach(u -> {
                    u.setSetmealId(setmeal.getId());
                });
                setmealDishService.saveBatch(setmealDishes);
            }
        }
        return false;
    }

    /**
     * 套餐管理 - 根据 ID 查询
     *
     * @param id 主键 ID
     * @return
     */
    @Override
    public SetmealVO findById(Integer id) {
        if (ObjectUtil.isNotNull(id)) {
            Setmeal setmeal = setmealService.getById(id);
            if (ObjectUtil.isNull(setmeal)) {
                throw new SetmealException(SetmealExceptionEnum.FIND_SETMEAL_BY_ID_NULL);
            }
            return SetmealToVo(setmeal);
        }
        return null;
    }

    /**
     * 套餐管理 - 根据分类ID查询套餐列表
     *
     * @param categoryId 分类ID
     * @return
     */
    @Override
    @Cacheable(value = "setmealCache",key = "#categoryId")
    public List<SetmealVO> listByCategoryId(Integer categoryId) {
        List<Setmeal> list = setmealService.listByCategoryId(categoryId);
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(this::SetmealToVo).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * C端-根据套餐ID查询包含的菜品信息
     *
     * @param id 套餐ID
     * @return
     */
    @Override
    public List<DishItemVO> listDishById(Long id) {
        if (ObjectUtil.isNotNull(id)) {
            List<SetmealDish> sdList = setmealDishService.listBySetmealId(id);
            if (CollUtil.isNotEmpty(sdList)) {
                List<DishItemVO> voList = sdList.stream().map(u -> {
                    Dish dish = dishService.getById(u.getDishId());
                    DishItemVO vo = DishItemVO.builder()
                            .image(dish.getImage())
                            .copies(u.getCopies())
                            .name(u.getName())
                            .description(dish.getDescription()).build();
                    return vo;
                }).collect(Collectors.toList());
                return voList;
            }
        }
        return null;
    }


    //========================================== 私有方法 begin ========================================


    /**
     * DDD 类型转换
     * Setmeal -> SetmealVO
     *
     * @param setmeal
     * @return
     */
    private SetmealVO SetmealToVo(Setmeal setmeal) {
        SetmealVO vo = new SetmealVO();
        BeanUtil.copyProperties(setmeal, vo);
        //封装分类名称
        Long categoryId = vo.getCategoryId();
        if (ObjectUtil.isNotNull(categoryId)) {
            Category category = categoryService.getById(categoryId);
            if (ObjectUtil.isNotNull(category)) {
                vo.setCategoryName(category.getName());
            }
        }
        //封装口味
        vo.setSetmealDishes(setmealDishService.listBySetmealId(vo.getId()));
        return vo;
    }


    //========================================== 私有方法 end ========================================

}
