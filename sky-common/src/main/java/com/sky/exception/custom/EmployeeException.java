package com.sky.exception.custom;

import com.sky.exception.enums.EmployeeExceptionEnum;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:30:47
 * @Description 员工相关异常
 */
public class EmployeeException  extends RuntimeException {
    private Integer code;

    private String message;

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public EmployeeException(EmployeeExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
