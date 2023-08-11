package com.sky.exception.custom;

import com.sky.exception.enums.OrdersExceptionEnum;

/**
 * @author 尹志伟
 * @date 2023/7/8 23:17:14
 * @Description 订单相关异常
 */
public class OrdersException extends RuntimeException {
    private Integer code;

    private String message;

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public OrdersException(OrdersExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
