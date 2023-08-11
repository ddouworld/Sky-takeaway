package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sky.alioss")
public class AliOssProperties {
    /**
     * 阿里云OSS - Endpoint（地域节点）
     */
    private String endpoint;

    /**
     * 阿里云账号 accessKeyId
     */
    private String accessKeyId;

    /**
     * 阿里云账号 accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 阿里云 对应的 Bucket 名称
     */
    private String bucketName;

    /**
     * 阿里云OSS 前端上传图片标识前缀
     */
    private String appUploadImagePrefix;

    /**
     * 阿里云OSS 文件上传成功之后访问路径 URL 前缀
     *
     * 需要和 Endpoint 对应上
     */
    private String bucketVisitUrlPrefix;
}
