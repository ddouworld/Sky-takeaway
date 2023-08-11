package com.sky.exception.custom;

import com.sky.exception.enums.UserExceptionEnum;

/**
 * @author 尹志伟
 * @date 2023/7/8 23:17:14
 * @Description
 */
public class UserException extends RuntimeException {
    private Integer code;

    private String message;

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public UserException(UserExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
