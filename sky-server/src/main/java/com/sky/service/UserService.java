package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.User;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface UserService extends IService<User> {

    /**
     * 根据 openId 获取用户信息
     *
     * @param openId 微信OpenId
     * @return
     */
    User getByOpenId(String openId);

    /**
     * 根据日期 获取 该日期段的 新增用户人数
     *
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @return
     */
    Integer countByDate(String beginDate, String endDate);
}
