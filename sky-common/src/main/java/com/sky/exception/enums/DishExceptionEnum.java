package com.sky.exception.enums;

import com.sky.exception.constant.DishExceptionConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:21:41
 * @Description 菜品相关异常枚举
 */
public enum DishExceptionEnum {
    /**
     * 保存失败
     */
    SAVE_DISH_ERROR(DishExceptionConstant.SAVE_DISH_ERROR, DishExceptionConstant.SAVE_DISH_ERROR_MESSAGE),

    /**
     * 更新失败
     */
    UPDATE_DISH_ERROR(DishExceptionConstant.UPDATE_DISH_ERROR,DishExceptionConstant.UPDATE_DISH_ERROR_MESSAGE),

    /**
     * 根据ID查询菜品失败
     */
    FIND_DISH_BY_ID_NULL(DishExceptionConstant.FIND_DISH_BY_ID_NULL_CODE, DishExceptionConstant.FIND_DISH_BY_ID_NULL_MESSAGE),

    /**
     * 删除失败 - 套餐下有删除的菜品
     */
    DELETE_DISH_ERROR_SETMEAL_DISH_IS_NOT_NULL(DishExceptionConstant.DELETE_DISH_ERROR_SETMEAL_DISH_IS_NOT_NULL, DishExceptionConstant.DELETE_DISH_ERROR_SETMEAL_DISH_IS_NOT_NULL_MESSAGE);




    private Integer code;

    private String message;


    DishExceptionEnum() {

    }

    DishExceptionEnum(Integer findEmployeeByIdNullCode, String findEmployeeByIdNullMessage) {

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
        for (DishExceptionEnum enumOp : DishExceptionEnum.values()) {
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
        for (DishExceptionEnum enumOp : DishExceptionEnum.values()) {
            if (code.equals(enumOp.getCode())) {
                message = enumOp.getMessage();
                break;
            }
        }
        return message;
    }
}
