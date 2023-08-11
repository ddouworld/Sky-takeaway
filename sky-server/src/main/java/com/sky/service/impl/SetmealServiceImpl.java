package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealDao;
import com.sky.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 套餐 - 分页
     *
     * @param page       当前页码
     * @param pageSize   分页数
     * @param setmealDTO 查询对象
     * @return
     */
    @Override
    public Page<Setmeal> pageBySetmealDTO(int page, int pageSize, SetmealDTO setmealDTO) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(setmealDTO)) {
            String name = setmealDTO.getName();
            Long categoryId = setmealDTO.getCategoryId();
            Integer status = setmealDTO.getStatus();
            queryWrapper.like(StrUtil.isNotBlank(name), Setmeal::getName, name)
                    .eq(ObjectUtil.isNotNull(categoryId), Setmeal::getCategoryId, categoryId)
                    .eq(ObjectUtil.isNotNull(status), Setmeal::getStatus, status)
                    .orderByDesc(Setmeal::getId, Setmeal::getCreateTime);
        }
        return page(setmealPage, queryWrapper);
    }

    /**
     * 套餐管理 - 根据分类ID查询套餐列表
     *
     * @param categoryId 分类ID
     * @return
     */
    @Override
    public List<Setmeal> listByCategoryId(Integer categoryId) {
        if (ObjectUtil.isNotNull(categoryId)) {
            LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Setmeal::getCategoryId, categoryId)
                    .orderByDesc(Setmeal::getId, Setmeal::getCreateTime);
            return list(queryWrapper);
        }
        return null;
    }

    /**
     * 根据状态值查询数量
     *
     * @param status 状态值
     * @return
     */
    @Override
    public Integer countByStatus(Integer status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getStatus, status);
        return count(queryWrapper);
    }
}

