package com.sky.exception.enums;

import com.sky.exception.constant.EmployeeExceptionConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:21:41
 * @Description 员工相关异常枚举
 */
public enum EmployeeExceptionEnum {
    /**
     * 查询相关
     */
    FIND_EMPLOYEE_BY_ID_NULL(EmployeeExceptionConstant.FIND_EMPLOYEE_BY_ID_NULL_CODE, EmployeeExceptionConstant.FIND_EMPLOYEE_BY_ID_NULL_MESSAGE);


    private Integer code;

    private String message;


    EmployeeExceptionEnum() {

    }

    EmployeeExceptionEnum(Integer findEmployeeByIdNullCode, String findEmployeeByIdNullMessage) {

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
        for (EmployeeExceptionEnum enumOp : EmployeeExceptionEnum.values()) {
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
        for (EmployeeExceptionEnum enumOp : EmployeeExceptionEnum.values()) {
            if (code.equals(enumOp.getCode())) {
                message = enumOp.getMessage();
                break;
            }
        }
        return message;
    }
}
