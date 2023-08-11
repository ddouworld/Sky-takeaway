package com.sky.biz;

import com.sky.vo.UserLoginVO;

/**
 * @author 尹志伟
 * @date 2023/7/8 22:32:50
 * @Description
 */
public interface WeChatUserBizService {
    /**
     * 微信小程序 - 登录
     *
     * @param wxCode 微信授权码 Code
     * @return
     */
    UserLoginVO loginByWxCode(String wxCode);
}
