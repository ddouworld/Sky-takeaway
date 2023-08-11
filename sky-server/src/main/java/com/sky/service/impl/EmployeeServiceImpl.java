package com.sky.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeDao;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-28
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 根据 UserName 获取员工信息
     *
     * @param username
     * @return
     */
    @Override
    public Employee getByUserName(String username) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<Employee>();
        wrapper.eq(Employee::getUsername, username);
        return employeeDao.selectOne(wrapper);
    }


    /**
     * 分页功能 - 名称模糊查询
     *
     * @param pageNum  当前页码
     * @param pageSize 分页数
     * @param name     模糊名称
     * @return
     */
    @Override
    public Page<Employee> pageLikeName(int pageNum, int pageSize, String name) {
        Page<Employee> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(name), Employee::getName,  name)
                .orderByDesc(Employee::getId);
        return page(page, queryWrapper);
    }
}
