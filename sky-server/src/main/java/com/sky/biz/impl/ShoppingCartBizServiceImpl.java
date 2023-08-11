package com.sky.biz.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sky.biz.ShoppingCartBizService;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.exception.custom.DishException;
import com.sky.exception.custom.SetmealException;
import com.sky.exception.enums.DishExceptionEnum;
import com.sky.exception.enums.SetmealExceptionEnum;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/11 23:27:26
 * @Description
 */
@Service
@Slf4j
public class ShoppingCartBizServiceImpl implements ShoppingCartBizService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;

    @Override
    public Boolean addShopCart(ShoppingCartDTO shoppingCartDTO) {
        Boolean shopCartResult = false;
        if (ObjectUtil.isNotNull(shoppingCartDTO)) {
            //查询出是否有对应的购物车，
            ShoppingCart shoppingCart = shoppingCartService.findByShoppingCartDTO(shoppingCartDTO);
            if (ObjectUtil.isNull(shoppingCart)) {
                //新增购物车
                shoppingCart = new ShoppingCart();
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCart.setNumber(1);
                shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
                shoppingCart.setUserId(shoppingCartDTO.getUserId());
                shoppingCart.setDishId(shoppingCartDTO.getDishId());
                shoppingCart.setSetmealId(shoppingCartDTO.getSetmealId());
                if (ObjectUtil.isNotNull(shoppingCart.getSetmealId())) {
                    //套餐
                    Setmeal setmeal = setmealService.getById(shoppingCart.getSetmealId());
                    if (ObjectUtil.isNull(setmeal)) {
                        throw new SetmealException(SetmealExceptionEnum.FIND_SETMEAL_BY_ID_NULL);
                    }
                    shoppingCart.setName(setmeal.getName());
                    shoppingCart.setImage(setmeal.getImage());
                    shoppingCart.setAmount(setmeal.getPrice());
                } else {
                    //菜品
                    Dish dish = dishService.getById(shoppingCart.getDishId());
                    if (ObjectUtil.isNull(dish)) {
                        throw new DishException(DishExceptionEnum.FIND_DISH_BY_ID_NULL);
                    }
                    shoppingCart.setName(dish.getName());
                    shoppingCart.setImage(dish.getImage());
                    shoppingCart.setAmount(dish.getPrice());
                }
                shopCartResult = shoppingCartService.save(shoppingCart);
            } else {
                //修改购物车
                shoppingCart.setNumber(shoppingCart.getNumber() + 1);
                shopCartResult = shoppingCartService.updateById(shoppingCart);
            }
            log.info("addShopCart:{},result:{}", shoppingCart.toString(), shopCartResult);
        }
        return shopCartResult;
    }


    /**
     * C端-查询购物车
     *
     * @return
     */
    @Override
    public List<ShoppingCart> listByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            return shoppingCartService.listByUserId(userId);
        }
        return null;
    }

    /**
     * C端-删除购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @Override
    public Boolean subShopCart(ShoppingCartDTO shoppingCartDTO) {
        Boolean shopCartResult = false;
        if (ObjectUtil.isNotNull(shoppingCartDTO)) {
            //查询出是否有对应的购物车，
            ShoppingCart shoppingCart = shoppingCartService.findByShoppingCartDTO(shoppingCartDTO);
            if (ObjectUtil.isNotNull(shoppingCart)) {
                Integer number = shoppingCart.getNumber();
                if (number > 1) {
                    shoppingCart.setNumber(number - 1);
                    shopCartResult = shoppingCartService.updateById(shoppingCart);
                } else {
                    shopCartResult = shoppingCartService.removeById(shoppingCart.getId());
                }
            }
        }
        return shopCartResult;
    }

    /**
     * C端-清空购物车
     *
     * @return
     */
    @Override
    public Boolean cleanShopCartByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            Integer deleteCount = shoppingCartService.deleteByUserId(userId);
            log.info("cleanShopCartByUserId count:{}", deleteCount);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
