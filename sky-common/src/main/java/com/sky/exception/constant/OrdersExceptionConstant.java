package com.sky.exception.constant;

/**
 * @author 尹志伟
 * @date 2023/7/8 23:13:42
 * @Description 小程序 - 订单异常
 */
public class OrdersExceptionConstant {

    /**
     * 提交订单失败，订单地址ID为空
     */
    public static final Integer SUBMIT_ORDER_ERROR_ADDRESSBOOK_ID_IS_NULL = 70001;

    public static final String SUBMIT_ORDER_ERROR_ADDRESSBOOK_ID_IS_NULL_MESSAGE = "提交订单失败，订单地址ID为空";



    /**
     * 提交订单失败，查询不到地址
     */
    public static final Integer SUBMIT_ORDER_ERROR_ADDRESSBOOK_IS_NULL = 70002;

    public static final String SUBMIT_ORDER_ERROR_ADDRESSBOOK_IS_NULL_MESSAGE = "提交订单失败，查询不到地址";



    /**
     * 提交订单失败,数据库插入失败
     */
    public static final Integer SUBMIT_ORDER_ERROR_OF_DB = 70003;

    public static final String SUBMIT_ORDER_ERROR_OF_DB_MESSAGE = "提交订单失败,数据库插入失败";

    /**
     * 提交订单失败,购物车无数据
     */
    public static final Integer SUBMIT_ORDER_ERROR_SHOP_CART_IS_NULL = 70004;

    public static final String SUBMIT_ORDER_ERROR_SHOP_CART_IS_NULL_MESSAGE = "提交订单失败,购物车无数据";


}
