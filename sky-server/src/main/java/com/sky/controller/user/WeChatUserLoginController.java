package com.sky.controller.user;

import cn.hutool.core.util.ObjectUtil;
import com.sky.base.BaseController;
import com.sky.biz.WeChatUserBizService;
import com.sky.context.BaseContext;
import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description 微信用户管理
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端 - 微信用户管理")
public class WeChatUserLoginController extends BaseController {
    @Autowired
    private WeChatUserBizService weChatUserBizService;


    /**
     * 小程序 - 用户登录接口
     *
     * @param userLoginDTO 小程序登录参数，包含code授权码
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "C端-用户登录接口")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("WeChatUserLogin login params:{}", userLoginDTO.toString());
        if (ObjectUtil.isNull(userLoginDTO)) {
            log.error("WeChatUserLogin login error, params is null!");
            return Result.error();
        }
        //微信登录授权码 code
        UserLoginVO vo = weChatUserBizService.loginByWxCode(userLoginDTO.getCode());
        if (ObjectUtil.isNull(vo)) {
            log.error("WeChatUserLogin login  loginByWxCode error, return user is null !");
            return Result.error();
        }
        return Result.success(vo);
    }


    /**
     *
     * 小程序 - 用户退出接口
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "C端-用户退出接口")
    public Result<Boolean> logout() {
        //后端清除ThreadLocal用户标识，前端清除请求头标识参数，
        BaseContext.removeCurrentId();
        return successReturnBoolean();
    }


}
