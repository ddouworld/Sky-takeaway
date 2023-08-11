package com.sky.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.User;
import com.sky.mapper.UserDao;
import com.sky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据 openId 获取用户信息
     *
     * @param openId 微信OpenId
     * @return
     */
    @Override
    public User getByOpenId(String openId) {
        if (StrUtil.isBlank(openId)) {
            return null;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenid, openId)
                .orderByDesc(User::getId);
        return getOne(queryWrapper);
    }

    /**
     * 根据日期 获取 该日期段的 新增用户人数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @Override
    public Integer countByDate(String beginDate, String endDate) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(User::getCreateTime, beginDate, endDate);
        return count(queryWrapper);
    }
}
