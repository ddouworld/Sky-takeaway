package com.sky.exception.enums;

import com.sky.exception.constant.SetmealExceptionConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:21:41
 * @Description 菜品相关异常枚举
 */
public enum SetmealExceptionEnum {
    /**
     * 根据ID查询套餐信息为空
     */
    FIND_SETMEAL_BY_ID_NULL(SetmealExceptionConstant.FIND_SETMEAL_BY_ID_NULL_CODE, SetmealExceptionConstant.FIND_SETMEAL_BY_ID_NULL_MESSAGE),

    /**
     * 新增套餐失败
     */
    SAVE_SETMEAL_ERROR(SetmealExceptionConstant.SAVE_SETMEAL_ERROR, SetmealExceptionConstant.SAVE_SETMEAL_ERROR_MESSAGE),


    ;


    private Integer code;

    private String message;


    SetmealExceptionEnum() {

    }

    SetmealExceptionEnum(Integer findEmployeeByIdNullCode, String findEmployeeByIdNullMessage) {

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
        for (SetmealExceptionEnum enumOp : SetmealExceptionEnum.values()) {
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
        for (SetmealExceptionEnum enumOp : SetmealExceptionEnum.values()) {
            if (code.equals(enumOp.getCode())) {
                message = enumOp.getMessage();
                break;
            }
        }
        return message;
    }
}
