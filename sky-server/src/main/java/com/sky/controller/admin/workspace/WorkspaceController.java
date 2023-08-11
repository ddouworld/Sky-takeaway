package com.sky.controller.admin.workspace;

import com.sky.base.BaseController;
import com.sky.biz.WorkspaceBizService;
import com.sky.result.Result;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 尹志伟
 * @date 2023/7/16 17:02:54
 * @Description 工作台
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "工作台")
public class WorkspaceController extends BaseController {

    @Autowired
    private WorkspaceBizService workspaceBizService;

    /**
     * 查询今日运营数据
     *
     * @return
     */
    @ApiOperation("工作台 - 查询今日运营数据")
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData() {
        log.info("businessData dateTime:{}", LocalDateTime.now());
        BusinessDataVO vo = workspaceBizService.businessData();
        return Result.success(vo);
    }

    /**
     * 查询订单管理数据
     *
     * @return
     */
    @ApiOperation("工作台 - 查询订单管理数据")
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> overviewOrders() {
        log.info("overviewOrders dateTime:{}", LocalDateTime.now());
        OrderOverViewVO vo = workspaceBizService.overviewOrders();
        return Result.success(vo);
    }


    /**
     * 查询菜品总览
     *
     * @return
     */
    @ApiOperation("工作台 - 查询菜品总览")
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overviewDishes() {
        log.info("overviewDishes dateTime:{}", LocalDateTime.now());
        DishOverViewVO vo = workspaceBizService.overviewDishes();
        return Result.success(vo);
    }


    /**
     * 查询套餐总览
     *
     * @return
     */
    @ApiOperation("工作台 - 查询套餐总览")
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> overviewSetmeals() {
        log.info("overviewDishes dateTime:{}", LocalDateTime.now());
        SetmealOverViewVO vo = workspaceBizService.overviewSetmeals();
        return Result.success(vo);
    }
}
