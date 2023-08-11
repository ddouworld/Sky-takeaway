package com.sky.controller.admin;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.sky.base.BaseController;
import com.sky.biz.SetmealBizService;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@RestController
@Slf4j
@Api(tags = "套餐管理")
@RequestMapping("/admin/setmeal")
public class SetmealController extends BaseController {

    @Autowired
    private SetmealBizService setmealBizService;

    /**
     * 套餐管理 - 分页
     *
     * @param dto 套餐查询对象
     * @return
     */
    @ApiOperation("套餐管理-分页")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO dto) {
        log.info("page Setmeal:{} || dateTime:{}", dto.toString(), DateTime.now());
        PageResult setmealVO = setmealBizService.pageByQueryDTO(dto);
        return Result.success(setmealVO);
    }

    /**
     * 套餐管理-起售、停售
     *
     * @param status 状态值
     * @param id     ID值
     * @return
     */
    @ApiOperation("套餐管理-起售、停售")
    @PostMapping("/status/{status}")
    public Result<Boolean> updateStatus(@PathVariable("status") Integer status, Long id) {
        log.info("page updateStatus.id:{}, status:{} || dateTime:{}", id, status, DateTime.now());
        return setmealBizService.updateStatus(status, id) ? successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * 菜品管理 - 批量删除
     *
     * @param ids 菜品id，之间用逗号分隔
     * @return
     */
    @ApiOperation("套餐管理-批量删除")
    @DeleteMapping
    public Result<Boolean> deleteByIds(String ids) {
        log.info("delete Setmeal.ids:{} || dateTime:{}", ids, DateTime.now());
        if (StrUtil.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            List<Long> idList = new ArrayList<Long>(idArr.length);
            for (String id : idArr) {
                idList.add(Long.parseLong(id));
            }
            setmealBizService.deleteByIdList(idList);
        }
        return successReturnBoolean();
    }

    /**
     * 套餐管理 - 新增
     *
     * @param setmealDTO
     * @return
     */
    @ApiOperation("套餐管理-新增")
    @PostMapping
    public Result<Boolean> save(@RequestBody SetmealDTO setmealDTO) {
        log.info("save Setmeal:{} || dateTime:{}", setmealDTO.toString(), DateTime.now());
        return setmealBizService.save(setmealDTO) ? successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * 套餐管理 - 根据 ID 查询
     *
     * @param id 主键 ID
     * @return
     */
    @ApiOperation("套餐管理-根据ID查询")
    @GetMapping("/{id}")
    public Result<SetmealVO> findById(@PathVariable("id") Integer id) {
        log.info("findById id:{} || dateTime:{}", id, DateTime.now());
        return Result.success(setmealBizService.findById(id));
    }
}

