package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.Employee;

/**
 * <p>
 * 员工信息 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-28
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 根据 UserName 获取员工信息
     *
     * @param username
     * @return
     */
    Employee getByUserName(String username);

    /**
     * 分页功能 - 名称模糊查询
     *
     * @param pageNum   当前页码
     * @param pageSize  分页数
     * @param name      模糊名称
     * @return
     */
    Page<Employee> pageLikeName(int pageNum, int pageSize, String name);
}
