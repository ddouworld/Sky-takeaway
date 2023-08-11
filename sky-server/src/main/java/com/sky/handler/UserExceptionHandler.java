package com.sky.handler;

import cn.hutool.core.date.DateTime;
import com.sky.exception.custom.OrdersException;
import com.sky.exception.custom.UserException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 尹志伟
 * @date 2023/7/8 23:18:17
 * @Description
 */
@RestControllerAdvice
@Slf4j
public class UserExceptionHandler {

    /**
     * 用户相关异常信息
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = UserException.class)
    public Result exceptionHandler(UserException ex) {
        log.error("UserExceptionHandler UserException code:{},message:{},dateTime:{}",
                ex.getCode(), ex.getMessage(), DateTime.now());
        return Result.error(ex.getMessage());
    }


    /**
     * 订单相关异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = OrdersException.class)
    public Result exceptionHandler(OrdersException ex) {
        log.error("UserExceptionHandler OrdersException code:{},message:{},dateTime:{}",
                ex.getCode(), ex.getMessage(), DateTime.now());
        return Result.error(ex.getMessage());
    }
}
