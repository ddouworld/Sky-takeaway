package com.sky.controller.user;

import com.sky.base.BaseController;
import com.sky.biz.DishBizService;
import com.sky.result.Result;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description C端 - 菜品
 */
@RestController
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端 - 菜品")
public class WeChatDishController extends BaseController {

    @Autowired
    private DishBizService dishBizService;

    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * C端-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    @ApiOperation("C端-根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> listByCategoryId(Integer categoryId) {
        List<DishVO> list = dishBizService.listDTOByCategoryId(categoryId);
        return Result.success(list);
    }



}
