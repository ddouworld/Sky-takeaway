package com.sky.biz.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.sky.biz.WeChatUserBizService;
import com.sky.constant.JwtClaimsConstant;
import com.sky.entity.User;
import com.sky.exception.custom.UserException;
import com.sky.exception.enums.UserExceptionEnum;
import com.sky.properties.JwtProperties;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.utils.WeChatUtil;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author 尹志伟
 * @date 2023/7/8 22:32:56
 * @Description
 */
@Slf4j
@Service
public class WeChatUserBizServiceImpl implements WeChatUserBizService {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private WeChatUtil weChatUtil;

    /**
     * 微信小程序 - 登录
     *
     * @param wxCode 微信授权码 Code
     * @return
     */
    @Override
    public UserLoginVO loginByWxCode(String wxCode) {
        if (StrUtil.isBlank(wxCode)) {
            throw new UserException(UserExceptionEnum.WECHAT_LOGIN_CODE_IS_NULL);
        }
        //根据WxCode,请求微信服务器获取参数
        String openId = weChatUtil.getWxOpenId(wxCode);
        if (StrUtil.isBlank(openId)) {
            throw new UserException(UserExceptionEnum.GET_OPENID_BY_CODE_ERROR);
        }
        //查询是否有openId记录用户
        User user = userService.getByOpenId(openId);
        if (ObjectUtil.isNull(user)) {
            //新用户进行登录
            user = new User();
            user.setOpenid(openId);
            user.setCreateTime(LocalDateTime.now());
            userService.save(user);
        }
        //生成JWT，并且封装VO进行返回
        HashMap<String, Object> claims = new HashMap<>(1);
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String jwt = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        UserLoginVO vo = UserLoginVO
                .builder()
                .openid(openId)
                .id(user.getId())
                .token(jwt).build();
        return vo;
    }
}
