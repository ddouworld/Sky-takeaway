package com.sky.controller.user;

import com.sky.base.BaseController;
import com.sky.biz.SetmealBizService;
import com.sky.result.Result;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description C端 - 套餐
 */
@RestController
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "C端 - 套餐")
public class WeChatSetmealController extends BaseController {

    @Autowired
    private SetmealBizService setmealBizService;


    /**
     * C端-根据分类id查询套餐
     *
     * @param categoryId 分类id
     * @return
     */
    @ApiOperation("C端-根据分类id查询套餐")
    @GetMapping("/list")
    public Result<List<SetmealVO>> listByCategoryId(Integer categoryId) {
        return Result.success(setmealBizService.listByCategoryId(categoryId));
    }


    /**
     * C端-根据套餐ID查询包含的菜品信息
     *
     * @param id 套餐ID
     * @return
     */
    @ApiOperation("C端-根据套餐ID查询包含的菜品信息")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> listDishById(@PathVariable("id") Long id) {
        return Result.success(setmealBizService.listDishById(id));
    }
}
