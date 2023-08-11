package com.sky.controller.user;

import com.sky.base.BaseController;
import com.sky.biz.ShoppingCartBizService;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description C端 - 购物车
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "C端 - 购物车")
public class WeChatShoppingCartController extends BaseController {

    @Autowired
    private ShoppingCartBizService shoppingCartBizService;

    /**
     * C端-添加购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @ApiOperation("C端-添加购物车")
    @PostMapping("/add")
    public Result<Boolean> addShopCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartDTO.setUserId(BaseContext.getCurrentId());
        log.info("weChat addShopCart：{}, userID:{}", shoppingCartDTO.toString(), shoppingCartDTO.getUserId());
        return shoppingCartBizService.addShopCart(shoppingCartDTO) ?
                successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * C端-查询购物车
     *
     * @return
     */
    @ApiOperation("C端-查询购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> listByUserId(){
        Long userId = BaseContext.getCurrentId();
        log.info("weChat listByUserId userID:{}", userId);
        return Result.success(shoppingCartBizService.listByUserId(userId));
    }


    /**
     * C端-删除购物车
     *
     * @param shoppingCartDTO
     * @return
     */
    @ApiOperation("C端-删除购物车")
    @PostMapping("/sub")
    public Result<Boolean> subShopCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartDTO.setUserId(BaseContext.getCurrentId());
        log.info("weChat subShopCart：{}, userID:{}", shoppingCartDTO.toString(), shoppingCartDTO.getUserId());
        return shoppingCartBizService.subShopCart(shoppingCartDTO) ?
                successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * C端-清空购物车
     *
     * @return
     */
    @ApiOperation("C端-清空购物车")
    @DeleteMapping("/clean")
    public Result<Boolean> cleanShopCart() {
        log.info("weChat cleanShopCart userID:{}", BaseContext.getCurrentId());
        return shoppingCartBizService.cleanShopCartByUserId(BaseContext.getCurrentId()) ?
                successReturnBoolean() : errorReturnBoolean();
    }

}
