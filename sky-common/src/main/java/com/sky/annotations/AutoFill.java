package com.sky.annotations;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 尹志伟
 * @date 2023/7/2 00:37:02
 * @Description 自定义注解 -
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    /**
     * type 属性 - 新增/修改
     *
     * @return
     */
    OperationType type();
}
