package com.sky.controller.user;

import com.sky.base.BaseController;
import com.sky.biz.CategoryBizService;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description C端 - 分类
 */
@RestController
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "C端 - 分类")
public class WeChatCategoryController extends BaseController {

    @Autowired
    private CategoryBizService categoryBizService;


    /**
     * 分类管理-根据类型查询分类
     *
     * @param type
     * @return
     */
    @ApiOperation("C端-查询分类")
    @GetMapping("/list")
    public Result<List<CategoryDTO>> listByType(@RequestParam(value = "type", required = false) Integer type) {
        return Result.success(categoryBizService.listByCategoryPageQueryDTO(
                CategoryPageQueryDTO.builder().type(type).build()));
    }


}
