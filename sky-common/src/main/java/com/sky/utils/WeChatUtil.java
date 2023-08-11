package com.sky.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.properties.WeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 尹志伟
 * @date 2023/7/9 01:40:50
 * @Description
 */
@Slf4j
@Component
public class WeChatUtil {

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 小程序 - 登录地址
     */
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * @param wxCode
     * @return
     */
    public String getWxOpenId(String wxCode) {
        if (StrUtil.isBlank(wxCode)) {
            return null;
        }
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", wxCode);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        log.info("WxCode申请OpenId返回：{}",jsonObject.toString());
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
