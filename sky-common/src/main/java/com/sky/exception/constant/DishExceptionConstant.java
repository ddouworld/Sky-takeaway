package com.sky.exception.constant;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:25:16
 * @Description 菜品异常信息 - 常量类
 */
public class DishExceptionConstant {
    /**
     * 新增菜品失败
     */
    public static final Integer SAVE_DISH_ERROR = 40001;

    public static final String SAVE_DISH_ERROR_MESSAGE = "新增菜品失败";


    /**
     * 删除菜品失败 - 套餐有关联删除的菜品，请勿删除
     */
    public static final Integer DELETE_DISH_ERROR_SETMEAL_DISH_IS_NOT_NULL = 40002;

    public static final String DELETE_DISH_ERROR_SETMEAL_DISH_IS_NOT_NULL_MESSAGE = " 套餐有关联删除的菜品，请勿删除!";


    /**
     * 根据ID查询员工信息为空
     */
    public static final Integer FIND_DISH_BY_ID_NULL_CODE = 40003;

    public static final String FIND_DISH_BY_ID_NULL_MESSAGE = "根据ID查询菜品信息为空";


    /**
     * 更新菜品失败
     */
    public static final Integer UPDATE_DISH_ERROR = 40004;

    public static final String UPDATE_DISH_ERROR_MESSAGE = "更新菜品失败";


}
