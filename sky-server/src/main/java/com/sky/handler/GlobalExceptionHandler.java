package com.sky.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.exception.custom.CategoryException;
import com.sky.exception.custom.DishException;
import com.sky.exception.custom.EmployeeException;
import com.sky.exception.custom.SetmealException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    /**
     * 解决 Mybatis 唯一约束报异常问题
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String sqlMessage = ex.getMessage();
        log.error("Mybatis SQLIntegrityConstraintViolationException:{}", sqlMessage);
        if (StrUtil.isNotBlank(sqlMessage) && sqlMessage.contains("Duplicate entry")) {
            String[] split = sqlMessage.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 员工相关异常全局处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = EmployeeException.class)
    public Result exceptionHandler(EmployeeException ex) {
        //全局异常处理，记录日志，统一返回前端异常信息即可 !
        log.error("GlobalExceptionHandler EmployeeException code:{},message:{},dateTime:{}",
                ex.getCode(), ex.getMessage(), DateTime.now());
        return Result.error(ex.getMessage());
    }


    /**
     * 菜品及套餐分类异常全局处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CategoryException.class)
    public Result exceptionHandler(CategoryException ex) {
        //全局异常处理，记录日志，统一返回前端异常信息即可 !
        log.error("GlobalExceptionHandler CategoryExceptionEnum code:{},message:{},dateTime:{}",
                ex.getCode(), ex.getMessage(), DateTime.now());
        return Result.error(ex.getMessage());
    }


    /**
     * 菜品异常全局处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = DishException.class)
    public Result exceptionHandler(DishException ex) {
        //全局异常处理，记录日志，统一返回前端异常信息即可 !
        log.error("GlobalExceptionHandler DishExceptionEnum code:{},message:{},dateTime:{}",
                ex.getCode(), ex.getMessage(), DateTime.now());
        return Result.error(ex.getMessage());
    }


    /**
     * 套餐异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = SetmealException.class)
    public Result exceptionHandler(SetmealException ex) {
        //全局异常处理，记录日志，统一返回前端异常信息即可 !
        log.error("GlobalExceptionHandler SetmealException code:{},message:{},dateTime:{}",
                ex.getCode(), ex.getMessage(), DateTime.now());
        return Result.error(ex.getMessage());
    }

}


