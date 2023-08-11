package com.sky.biz;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

/**
 * @author 尹志伟
 * @date 2023/6/29 01:11:29
 * @Description
 */
public interface EmployeeBizService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工保存
     *
     * @param dto
     * @return
     */
    Boolean save(EmployeeDTO dto);


    /**
     * 员工分页
     *
     * @param queryDTO
     * @return
     */
    PageResult page(EmployeePageQueryDTO queryDTO);

    /**
     * 修改员工状态
     *
     * @param status  状态值
     * @param id      员工ID
     * @return
     */
    Boolean updateStatusByEmployeeId(Integer status, Long id);

    /**
     * 根据ID查询员工信息
     *
     * @param id 员工 ID
     * @return
     */
    Employee findById(Long id);

    /**
     * 修改员工信息
     *
     * @param dto
     * @return
     */
    Boolean updateEmployee(EmployeeDTO dto);
}
