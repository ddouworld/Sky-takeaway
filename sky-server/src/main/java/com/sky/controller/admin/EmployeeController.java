package com.sky.controller.admin;

import cn.hutool.core.date.DateTime;
import com.sky.base.BaseController;
import com.sky.biz.EmployeeBizService;
import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeBizService employeeBizService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录接口")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("admin login Employee:{} || dateTime:{}", employeeLoginDTO.getUsername(), DateTime.now());
        Employee employee = employeeBizService.login(employeeLoginDTO);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        //登录成功后，生成jwt令牌，用户的 ID 作为JWT内容
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();
        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出接口")
    public Result<Boolean> logout() {
        BaseContext.removeCurrentId();
        return successReturnBoolean();
    }


    /**
     * 新增员工
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "新增员工")
    @PostMapping
    public Result<Boolean> save(@RequestBody EmployeeDTO dto) {
        log.info("save Employee:{} || dateTime:{}", dto.toString(), DateTime.now());
        return employeeBizService.save(dto) ? successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * 员工分页
     *
     * @param queryDTO
     * @return
     */
    @ApiOperation(value = "员工分页")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO queryDTO) {
        log.info("page Employee:{} || dateTime:{}", queryDTO.toString(), DateTime.now());
        return Result.success(employeeBizService.page(queryDTO));
    }

    /**
     * 修改员工状态
     *
     * @param status 状态值
     * @param id     员工ID
     * @return
     */
    @ApiOperation(value = "修改员工状态")
    @PostMapping("/status/{status}")
    public Result<Boolean> updateStatusByEmployeeId(@PathVariable("status") Integer status, Long id) {
        log.info("update status Employee:{} || dateTime:{}", "员工ID：" + id + ",修改状态:" + status, DateTime.now());
        return employeeBizService.updateStatusByEmployeeId(status, id) ?
                successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * 根据ID查询员工信息
     *
     * @param id 员工 ID
     * @return
     */
    @ApiOperation(value = "根据ID查询员工")
    @GetMapping("/{id}")
    public Result<Employee> findById(@PathVariable("id") Long id) {
        return Result.success(employeeBizService.findById(id));
    }


    /**
     * 修改员工信息
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "修改员工信息")
    @PutMapping
    public Result<Boolean> updateEmployee(@RequestBody EmployeeDTO dto) {
        log.info("update Employee:{} || dateTime:{}", dto.toString(), DateTime.now());
        return employeeBizService.updateEmployee(dto) ?  successReturnBoolean() : errorReturnBoolean();
    }
}
