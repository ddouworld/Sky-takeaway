package com.sky.exception.enums;

import com.sky.exception.constant.OrdersExceptionConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:21:41
 * @Description 订单异常相关枚举
 */
public enum OrdersExceptionEnum {
    /**
     * 提交订单失败，订单地址ID为空
     */
    SUBMIT_ORDER_ERROR_ADDRESSBOOK_ID_IS_NULL(OrdersExceptionConstant.SUBMIT_ORDER_ERROR_ADDRESSBOOK_ID_IS_NULL, OrdersExceptionConstant.SUBMIT_ORDER_ERROR_ADDRESSBOOK_ID_IS_NULL_MESSAGE),

    /**
     * 提交订单失败，查询不到地址
     */
    SUBMIT_ORDER_ERROR_ADDRESSBOOK_IS_NULL(OrdersExceptionConstant.SUBMIT_ORDER_ERROR_ADDRESSBOOK_IS_NULL, OrdersExceptionConstant.SUBMIT_ORDER_ERROR_ADDRESSBOOK_IS_NULL_MESSAGE),

    /**
     * 提交订单失败,数据库插入失败
     */
    SUBMIT_ORDER_ERROR_OF_DB(OrdersExceptionConstant.SUBMIT_ORDER_ERROR_OF_DB, OrdersExceptionConstant.SUBMIT_ORDER_ERROR_OF_DB_MESSAGE),

    /**
     * 提交订单失败,购物车无数据
     */
    SUBMIT_ORDER_ERROR_SHOP_CART_IS_NULL(OrdersExceptionConstant.SUBMIT_ORDER_ERROR_SHOP_CART_IS_NULL, OrdersExceptionConstant.SUBMIT_ORDER_ERROR_SHOP_CART_IS_NULL_MESSAGE);


    private Integer code;

    private String message;


    OrdersExceptionEnum() {

    }

    OrdersExceptionEnum(Integer findEmployeeByIdNullCode, String findEmployeeByIdNullMessage) {

    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 获取枚举类全部属性，转换成 Map 返回
     *
     * @return
     */
    public static Map<String, Object> getMaps() {
        HashMap<String, Object> enumMap = new HashMap<String, Object>();
        for (OrdersExceptionEnum enumOp : OrdersExceptionEnum.values()) {
            enumMap.put(enumOp.getCode().toString(), enumOp.getMessage());
        }
        return enumMap;
    }

    /**
     * 根据枚举类 code 返回 对应的message
     *
     * @param code 枚举 code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        String message = "";
        for (OrdersExceptionEnum enumOp : OrdersExceptionEnum.values()) {
            if (code.equals(enumOp.getCode())) {
                message = enumOp.getMessage();
                break;
            }
        }
        return message;
    }
}
