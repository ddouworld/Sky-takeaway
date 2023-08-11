package com.sky.base;

import com.sky.constant.ResultDataConstant;
import com.sky.result.Result;

/**
 * @author 尹志伟
 * @date 2023/7/2 00:37:02
 * @Description
 */
public class BaseController {
    public Result<Boolean> successReturnBoolean() {
        return Result.success(ResultDataConstant.SUCCESS);
    }

    public Result<Boolean> errorReturnBoolean() {
        return Result.success(ResultDataConstant.ERROR);
    }
}
