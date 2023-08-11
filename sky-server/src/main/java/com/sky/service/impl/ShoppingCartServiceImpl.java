package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartDao;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    /**
     * 根据 shoppingCartDTO 查询购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @Override
    public ShoppingCart findByShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
        if (ObjectUtil.isNotNull(shoppingCartDTO)) {
            Long userId = shoppingCartDTO.getUserId();
            Long setmealId = shoppingCartDTO.getSetmealId();
            Long dishId = shoppingCartDTO.getDishId();
            String dishFlavor = shoppingCartDTO.getDishFlavor();
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotNull(userId), ShoppingCart::getUserId, userId)
                    .eq(ObjectUtil.isNotNull(setmealId), ShoppingCart::getSetmealId, setmealId)
                    .eq(ObjectUtil.isNotNull(dishId), ShoppingCart::getDishId, dishId)
                    .eq(StrUtil.isNotBlank(dishFlavor), ShoppingCart::getDishFlavor, dishFlavor);
            return getOne(queryWrapper);
        }
        return null;
    }

    /**
     * 根据 UserId 查询购物车列表
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<ShoppingCart> listByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, userId)
                    .orderByDesc(ShoppingCart::getCreateTime, ShoppingCart::getId);
            return list(queryWrapper);
        }
        return null;
    }

    /**
     * 根据 UserId 清空购物车列表
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public Integer deleteByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, userId);
            return shoppingCartDao.delete(queryWrapper);
        }
        return 0;
    }
}
