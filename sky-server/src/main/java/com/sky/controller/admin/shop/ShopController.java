package com.sky.controller.admin.shop;

import com.sky.base.BaseController;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author 尹志伟
 * @date 2023/7/5 19:47:45
 * @Description 店铺管理
 */
@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺管理")
public class ShopController extends BaseController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 店铺管理 - 设置营业状态
     *
     * @param status 状态值
     * @return
     */
    @ApiOperation("店铺管理-设置营业状态")
    @PutMapping("/{status}")
    public Result<Boolean> updateShopStatus(@PathVariable("status") Integer status) {
        log.info("updateShopStatus：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }


    /**
     * 店铺管理 - 获取营业状态
     *
     * @return
     */
    @ApiOperation("店铺管理-设置营业状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus() {
        Integer status = redisTemplate.opsForValue().get(KEY);
        log.info("getShopStatus：{}", status == 1 ? " 营业中" : "打烊中");
        return Result.success(status);
    }


}
