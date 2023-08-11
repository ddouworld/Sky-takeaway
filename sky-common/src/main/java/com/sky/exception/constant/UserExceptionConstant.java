package com.sky.exception.constant;

/**
 * @author 尹志伟
 * @date 2023/7/8 23:13:42
 * @Description 小程序 - 用户
 */
public class UserExceptionConstant {

    /**
     * 小程序登录 - wxCode 参数为空
     */
    public static final Integer WECHAT_LOGIN_CODE_IS_NULL = 60001;

    public static final String WECHAT_LOGIN_CODE_IS_NULL_MESSAGE = "小程序登录,wxCode参数为空";

    /**
     * 小程序登录 - 根据wxCode 获取 用户openId失败
     */
    public static final Integer GET_OPENID_BY_CODE_ERROR = 60002;

    public static final String  GET_OPENID_BY_CODE_ERROR_MESSAGE = "获取用户OpenId失败!";

}
