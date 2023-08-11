package com.sky.biz.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.biz.EmployeeBizService;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.exception.custom.EmployeeException;
import com.sky.exception.enums.EmployeeExceptionEnum;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * @author 尹志伟
 * @date 2023/6/29 01:11:41
 * @Description
 */
@Service
@Slf4j
public class EmployeeBizServiceImpl implements EmployeeBizService {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();
        //1、根据用户名查询数据库中的数据
        Employee employee = employeeService.getByUserName(username);
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        String md5pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        //密码比对
        if (!md5pwd.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        //3、返回实体对象
        return employee;
    }


    /**
     * 员工保存
     *
     * @param dto
     * @return
     */
    @Override
    public Boolean save(EmployeeDTO dto) {
        if (ObjectUtil.isNotNull(dto)) {
            Employee employee = new Employee();
            employee.setUsername(dto.getUsername());
            employee.setName(dto.getName());
            employee.setPhone(dto.getPhone());
            employee.setIdNumber(dto.getIdNumber());
            employee.setSex(dto.getSex());
            //设置密码默认 123456
            employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());
            employee.setCreateUser(BaseContext.getCurrentId());
            employee.setUpdateUser(BaseContext.getCurrentId());
            //设置账号的状态，默认正常状态 1表示正常 0表示锁定
            employee.setStatus(StatusConstant.ENABLE);
            return employeeService.save(employee);
        }
        return false;
    }


    /**
     * 员工分页
     *
     * @param queryDTO
     * @return
     */
    @Override
    public PageResult page(EmployeePageQueryDTO queryDTO) {
        PageResult pageResult = new PageResult();
        if (ObjectUtil.isNotNull(queryDTO)) {
            Page<Employee> page = employeeService.pageLikeName(queryDTO.getPage(), queryDTO.getPageSize(), queryDTO.getName());
            if (ObjectUtil.isNotNull(page)) {
                pageResult.setTotal(page.getTotal());
                pageResult.setRecords(page.getRecords());
            }
        }
        return pageResult;
    }


    /**
     * 修改员工状态
     *
     * @param status 状态值
     * @param id     员工ID
     * @return
     */
    @Override
    public Boolean updateStatusByEmployeeId(Integer status, Long id) {
        if (ObjectUtil.isNotNull(id) && ObjectUtil.isNotNull(status)) {
            Employee employee = employeeService.getById(id);
            if (ObjectUtil.isNull(employee)) {
                return false;
            }
            employee.setStatus(status);
            return employeeService.updateById(employee);
        }
        return false;
    }

    /**
     * 根据ID查询员工信息
     *
     * @param id 员工 ID
     * @return
     */
    @Override
    public Employee findById(Long id) {
        if (ObjectUtil.isNotNull(id)) {
            return employeeService.getById(id);
        }
        return null;
    }

    /**
     * 修改员工信息
     *
     * @param dto
     * @return
     */
    @Override
    public Boolean updateEmployee(EmployeeDTO dto) {
        if (ObjectUtil.isNotNull(dto)) {
            Employee employee = employeeService.getById(dto.getId());
            if (ObjectUtil.isNull(employee)) {
                throw new EmployeeException(EmployeeExceptionEnum.FIND_EMPLOYEE_BY_ID_NULL);
            }
            employee.setUsername(dto.getUsername());
            employee.setName(dto.getName());
            employee.setSex(dto.getSex());
            employee.setIdNumber(dto.getIdNumber());
            employee.setPhone(dto.getPhone());
            return employeeService.updateById(employee);
        }
        log.error("updateEmployee error,EmployeeDTO null || dateTime:{}", DateTime.now());
        return false;
    }
}
