package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 根据 shoppingCartDTO 查询购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    ShoppingCart findByShoppingCartDTO(ShoppingCartDTO shoppingCartDTO);

    /**
     * 根据 UserId 查询购物车列表
     *
     * @param userId 用户ID
     * @return
     */
    List<ShoppingCart> listByUserId(Long userId);

    /**
     * 根据 UserId 清空购物车列表
     *
     * @param userId 用户ID
     * @return
     */
    Integer deleteByUserId(Long userId);
}
