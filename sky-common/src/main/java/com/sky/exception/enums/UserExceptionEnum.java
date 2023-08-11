package com.sky.exception.enums;

import com.sky.exception.constant.UserExceptionConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/6/30 01:21:41
 * @Description 菜品相关异常枚举
 */
public enum UserExceptionEnum {
    /**
     * 小程序登录 - wxCode 参数为空
     */
    WECHAT_LOGIN_CODE_IS_NULL(UserExceptionConstant.WECHAT_LOGIN_CODE_IS_NULL, UserExceptionConstant.WECHAT_LOGIN_CODE_IS_NULL_MESSAGE),

    /**
     * 小程序登录 - 获取用户OpenId失败
     */
    GET_OPENID_BY_CODE_ERROR(UserExceptionConstant.GET_OPENID_BY_CODE_ERROR,UserExceptionConstant.GET_OPENID_BY_CODE_ERROR_MESSAGE),
    ;


    private Integer code;

    private String message;


    UserExceptionEnum() {

    }

    UserExceptionEnum(Integer findEmployeeByIdNullCode, String findEmployeeByIdNullMessage) {

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
        for (UserExceptionEnum enumOp : UserExceptionEnum.values()) {
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
        for (UserExceptionEnum enumOp : UserExceptionEnum.values()) {
            if (code.equals(enumOp.getCode())) {
                message = enumOp.getMessage();
                break;
            }
        }
        return message;
    }
}
