package com.sky.controller.admin;


import cn.hutool.core.date.DateTime;
import com.sky.base.BaseController;
import com.sky.biz.CategoryBizService;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryBizService categoryBizService;

    /**
     * 分类管理 - 分页
     *
     * @param dto
     * @return
     */
    @ApiOperation("分类管理-分页")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO dto) {
        log.info("page Category:{} || dateTime:{}", dto.toString(), DateTime.now());
        return Result.success(categoryBizService.page(dto));
    }


    /**
     * 分类管理-新增
     *
     * @param categoryDTO 新增对象
     * @return
     */
    @ApiOperation("分类管理-新增")
    @PostMapping
    public Result<Boolean> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("save Category:{} || dateTime:{}", categoryDTO.toString(), DateTime.now());
        return categoryBizService.save(categoryDTO) ? successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * 分类管理 - 根据ID修改状态
     *
     * @param status 状态值
     * @param id     分类ID
     * @return
     */
    @ApiOperation("分类管理-启用/禁用")
    @PostMapping("/status/{status}")
    public Result<Boolean> updateStatusById(@PathVariable("status") Integer status, Integer id) {
        log.info("update status:{}  Category.id:{} || dateTime:{}", status, id, DateTime.now());
        return categoryBizService.updateStatusById(status, id) ? successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * 分类管理 - 根据ID删除
     *
     * @param id
     * @return
     */
    @ApiOperation("分类管理-根据ID删除")
    @DeleteMapping
    public Result<Boolean> deleteById(Integer id) {
        log.info("deleteById Category.id:{} || dateTime:{}", id, DateTime.now());
        return categoryBizService.deleteById(id) ? successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * 分类管理-修改
     *
     * @return
     */
    @ApiOperation("分类管理-修改")
    @PutMapping
    public Result<Boolean> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("update Category:{} || dateTime:{}", categoryDTO.toString(), DateTime.now());
        return categoryBizService.updateById(categoryDTO) ? successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * 分类管理-根据类型查询分类
     *
     * @param type
     * @return
     */
    @ApiOperation("分类管理-根据类型查询分类")
    @GetMapping("/list")
    public Result<List<CategoryDTO>> listByType(@RequestParam(value = "type",required = true) Integer type) {
        return Result.success(categoryBizService.listByCategoryPageQueryDTO(
                CategoryPageQueryDTO.builder().type(type).build()));
    }
}

