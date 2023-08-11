package com.sky.controller.admin.common;

import cn.hutool.core.util.StrUtil;
import com.sky.base.BaseController;
import com.sky.result.Result;
import com.sky.utils.template.AliOssTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description 工具管理
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "工具管理")
public class CommonController extends BaseController {

    @Autowired
    private AliOssTemplate aliOssTemplate;

    @ApiOperation("工具管理-文件上传")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        log.info("Common upload:{}", multipartFile.getOriginalFilename());
        String imageUrl = "";
        try {
            String name = multipartFile.getOriginalFilename();
            if (StrUtil.isBlank(name)) {
                name = "sky";
            }
            InputStream inputStream = multipartFile.getInputStream();
            imageUrl = aliOssTemplate.ossUploadImage(name, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(imageUrl);
    }

}
