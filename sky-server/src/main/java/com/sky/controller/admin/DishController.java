package com.sky.controller.admin;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.sky.base.BaseController;
import com.sky.biz.DishBizService;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜品 前端控制器
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理")
public class DishController extends BaseController {

    @Autowired
    private DishBizService dishBizService;

    /**
     * 菜品管理 - 分页
     *
     * @param dto
     * @return
     */
    @ApiOperation("菜品管理-分页")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dto) {
        log.info("page Dish:{} || dateTime:{}", dto.toString(), DateTime.now());
        PageResult dishVo = dishBizService.pageByQueryDTO(dto);
        return Result.success(dishVo);
    }

    /**
     * 菜品管理 - 新增
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation("菜品管理-新增")
    @PostMapping
    public Result<Boolean> save(@RequestBody DishDTO dishDTO) {
        log.info("save Dish:{} || dateTime:{}", dishDTO.toString(), DateTime.now());
        return dishBizService.saveDishDTO(dishDTO) ? successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * 菜品管理 - 批量删除
     *
     * @param ids 菜品id，之间用逗号分隔
     * @return
     */
    @ApiOperation("菜品管理-批量删除")
    @DeleteMapping
    public Result<Boolean> deleteByIds(String ids) {
        log.info("delete Dish.ids:{} || dateTime:{}", ids, DateTime.now());
        if (StrUtil.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            List<Long> idList = new ArrayList<Long>(idArr.length);
            for (String id : idArr) {
                idList.add(Long.parseLong(id));
            }
            dishBizService.deleteByIdList(idList);
        }
        return successReturnBoolean();
    }


    /**
     * 菜品管理-菜品起售||停售
     *
     * @param status 状态
     * @param id     菜品ID
     * @return
     */
    @ApiOperation("菜品管理-菜品起售||停售")
    @PostMapping("/status/{status}")
    public Result<Boolean> updateStatus(@PathVariable("status") Integer status, Long id) {
        log.info("updateStatus Dish.id:{},Status:{} || dateTime:{}", id, status, DateTime.now());
        return dishBizService.updateStatus(status, id) ? successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * 菜品管理 - 根据ID查询
     *
     * @param id 菜品ID
     * @return
     */
    @ApiOperation("菜品管理-根据ID查询")
    @GetMapping("/{id}")
    public Result<DishDTO> getById(@PathVariable("id") Long id) {
        log.info("getById Dish.id:{} || dateTime:{}", id, DateTime.now());
        return Result.success(dishBizService.getById(id));
    }


    /**
     * 菜品管理-根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return
     */
    @ApiOperation("菜品管理-根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> listByCategoryId(Integer categoryId) {
        return Result.success(dishBizService.listByCategoryId(categoryId));
    }


    /**
     * 菜品管理 - 修改
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation("菜品管理-修改")
    @PutMapping
    public Result<Boolean> update(@RequestBody DishDTO dishDTO) {
        log.info("update Dish:{} || dateTime:{}", dishDTO.toString(), DateTime.now());
        return dishBizService.update(dishDTO) ? successReturnBoolean() : errorReturnBoolean();
    }


}

