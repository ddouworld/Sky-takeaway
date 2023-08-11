package com.sky.biz;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/11 23:24:17
 * @Description
 */
public interface ShoppingCartBizService {

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    Boolean addShopCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * C端-查询购物车
     *
     * @return
     */
    List<ShoppingCart> listByUserId(Long userId);

    /**
     * C端-删除购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    Boolean subShopCart(ShoppingCartDTO shoppingCartDTO);


    /**
     * C端-清空购物车
     *
     * @return
     */
    Boolean cleanShopCartByUserId(Long currentId);
}
