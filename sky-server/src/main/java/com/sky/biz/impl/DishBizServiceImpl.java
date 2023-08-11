package com.sky.biz.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.biz.DishBizService;
import com.sky.constant.RedisKeyConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.custom.DishException;
import com.sky.exception.enums.DishExceptionEnum;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import com.sky.service.SetmealDishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 尹志伟
 * @date 2023/7/5 00:12:14
 * @Description
 */
@Service
@Slf4j
public class DishBizServiceImpl implements DishBizService {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 菜品管理 - 分页
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult pageByQueryDTO(DishPageQueryDTO dto) {
        PageResult pageResult = new PageResult();
        if (ObjectUtil.isNotNull(dto)) {
            DishDTO dishDTO = new DishDTO();
            Integer categoryId = dto.getCategoryId();
            if (ObjectUtil.isNotNull(categoryId)) {
                dishDTO.setCategoryId(Long.valueOf(categoryId));
            }
            dishDTO.setName(dto.getName());
            dishDTO.setStatus(dto.getStatus());
            Page<Dish> pageDish = dishService.pageByDishDTO(dto.getPage(), dto.getPageSize(), dishDTO);
            if (ObjectUtil.isNotNull(pageDish)) {
                pageResult.setTotal(pageDish.getTotal());
                pageResult.setRecords(pageDish.getRecords().stream().map(this::DishToVo).collect(Collectors.toList()));
            }

        }
        return pageResult;
    }

    /**
     * 菜品管理 - 新增
     *
     * @param dishDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Boolean saveDishDTO(DishDTO dishDTO) {
        if (ObjectUtil.isNotNull(dishDTO)) {
            Dish dish = new Dish();
            dish.setName(dishDTO.getName());
            dish.setCategoryId(dishDTO.getCategoryId());
            dish.setPrice(dishDTO.getPrice());
            dish.setImage(dishDTO.getImage());
            dish.setDescription(dishDTO.getDescription());
            dish.setStatus(StatusConstant.ENABLE);
            //保存菜品
            boolean isOk = dishService.save(dish);
            if (!isOk) {
                throw new DishException(DishExceptionEnum.SAVE_DISH_ERROR);
            }
            //保存中间关系
            List<DishFlavor> flavorList = dishDTO.getFlavors();
            if (CollUtil.isNotEmpty(flavorList) && ObjectUtil.isNotNull(dish.getId())) {
                flavorList.stream().forEach(flavor -> flavor.setDishId(dish.getId()));
                dishFlavorService.saveBatch(flavorList);
            }

            //清除Redis数据
            String redisKey = RedisKeyConstant.REDIS_DISH_KEY + dishDTO.getCategoryId();
            cleanCache(redisKey);

            return true;
        }
        return false;
    }

    /**
     * 菜品管理 - 根据 ID 主键集合进行删除
     *
     * @param idList ID主键集合
     * @return
     */
    @Override
    public Integer deleteByIdList(List<Long> idList) {
        if (CollUtil.isNotEmpty(idList)) {
            //查询菜品是否关联了套餐
            Integer count = setmealDishService.countByDishIds(idList);
            if (count > 0) {
                throw new DishException(DishExceptionEnum.DELETE_DISH_ERROR_SETMEAL_DISH_IS_NOT_NULL);
            }
            //先删除菜品
            dishService.removeByIds(idList);
            //再删除关联的口味
            dishFlavorService.removeByDishList(idList);

            //将所有的菜品缓存数据清理掉，所有以dish_开头的key
            cleanCache("dish_*");
            return idList.size();
        }
        return 0;
    }


    /**
     * 菜品管理 - 根据ID查询
     *
     * @param id 菜品ID
     * @return
     */
    @Override
    public DishDTO getById(Long id) {
        DishDTO dishDTO = new DishDTO();
        if (ObjectUtil.isNotNull(id)) {
            Dish dish = dishService.getById(id);
            if (ObjectUtil.isNotNull(dish)) {
                dishDTO.setId(dish.getId());
                dishDTO.setName(dish.getName());
                dishDTO.setCategoryId(dish.getCategoryId());
                dishDTO.setPrice(dish.getPrice());
                dishDTO.setImage(dish.getImage());
                dishDTO.setDescription(dish.getDescription());
                dishDTO.setStatus(dish.getStatus());
                //查询口味
                dishDTO.setFlavors(dishFlavorService.listByDishId(dish.getId()));
            }
        }
        return dishDTO;
    }

    /**
     * 菜品管理-菜品起售||停售
     *
     * @param status 状态
     * @param id     菜品ID
     * @return
     */
    @Override
    public Boolean updateStatus(Integer status, Long id) {
        if (ObjectUtil.isNull(id)) {
            log.info("updateStatus id:{} is null", id);
            return false;
        }
        Dish dish = dishService.getById(id);
        if (ObjectUtil.isNotNull(dish)) {
            dish.setStatus(status);

            //将所有的菜品缓存数据清理掉，所有以dish_开头的key
            cleanCache(RedisKeyConstant.REDIS_DISH_KEY+dish.getCategoryId());
            return dishService.updateById(dish);
        }
        return false;
    }

    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    @Override
    public List<Dish> listByCategoryId(Integer categoryId) {
        return dishService.listByCategoryId(categoryId);
    }

    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    @Override
    public List<DishVO> listDTOByCategoryId(Integer categoryId) {
        List<Dish> list = dishService.listByCategoryId(categoryId);
        return list.stream().map(this::DishToVo).collect(Collectors.toList());
    }

    /**
     * 菜品管理 - 修改
     *
     * @param dishDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(DishDTO dishDTO) {
        if (ObjectUtil.isNotNull(dishDTO)) {
            Long id = dishDTO.getId();
            Dish dish = dishService.getById(id);
            if (ObjectUtil.isNull(dish)) {
                throw new DishException(DishExceptionEnum.FIND_DISH_BY_ID_NULL);
            }
            dish.setName(dishDTO.getName());
            dish.setCategoryId(dishDTO.getCategoryId());
            dish.setPrice(dishDTO.getPrice());
            dish.setImage(dishDTO.getImage());
            dish.setDescription(dishDTO.getDescription());
            dish.setStatus(dishDTO.getStatus());
            boolean updateOk = dishService.updateById(dish);
            if (!updateOk) {
                throw new DishException(DishExceptionEnum.UPDATE_DISH_ERROR);
            }
            //修改口味相关，先删除，再新增
            dishFlavorService.deleteByDishId(dish.getId());
            List<DishFlavor> flavorList = dishDTO.getFlavors();
            if (CollUtil.isNotEmpty(flavorList) && ObjectUtil.isNotNull(dish.getId())) {
                flavorList.stream().forEach(flavor -> flavor.setDishId(dish.getId()));
                dishFlavorService.saveBatch(flavorList);
            }
            //将所有的菜品缓存数据清理掉，所有以dish_开头的key
            cleanCache(RedisKeyConstant.REDIS_DISH_KEY+dish.getCategoryId());
        }
        return false;
    }

    //========================================== 私有方法 begin ========================================

    /**
     * DDD 类型转换
     * Dish -> Vo
     *
     * @param dish
     * @return
     */
    private DishVO DishToVo(Dish dish) {
        DishVO vo = new DishVO();
        BeanUtil.copyProperties(dish, vo);
        //封装分类名称
        Long categoryId = vo.getCategoryId();
        if (ObjectUtil.isNotNull(categoryId)) {
            Category category = categoryService.getById(categoryId);
            if (ObjectUtil.isNotNull(category)) {
                vo.setCategoryName(category.getName());
            }
        }
        //封装关联的口味
        List<DishFlavor> dishFlavorList = dishFlavorService.listByDishId(vo.getId());
        vo.setFlavors(dishFlavorList);
        return vo;
    }


    //========================================== 私有方法 end ========================================

}
