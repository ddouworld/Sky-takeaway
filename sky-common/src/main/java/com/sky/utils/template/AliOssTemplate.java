package com.sky.utils.template;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.sky.properties.AliOssProperties;
import org.springframework.http.HttpStatus;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author 尹志伟
 * @date 2023/3/19 00:58:06
 * @Description
 */
public class AliOssTemplate {

    private AliOssProperties aliOssProperties;

    public AliOssTemplate(AliOssProperties aliOssProperties) {
        this.aliOssProperties = aliOssProperties;
    }

    /**
     * 图片上传至阿里云OSS
     *
     * @param imageName       图片名称
     * @param fileInputStream 图片输入流
     */
    public String ossUploadImage(String imageName, InputStream fileInputStream) {
        String imagePrefix = aliOssProperties.getAppUploadImagePrefix();
        //文件上传名称：imagePrefix/2023/03/20/UUID.png
        String ossName = imagePrefix+ DateTime.of(new Date()).toString("yyyy/MM/dd")+"-"+ UUID.randomUUID().toString()+
                imageName.substring(imageName.lastIndexOf("."));
        //创建OSS实例
        OSS ossClient = new OSSClientBuilder()
                .build(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getAccessKeySecret());
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliOssProperties.getBucketName(), ossName, fileInputStream);

            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            if(result.getResponse().getStatusCode() == HttpStatus.OK.value()){
                String successName = aliOssProperties.getBucketVisitUrlPrefix() + ossName;
                System.out.println("文件上传成功，路径为："+successName);
                return successName;
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
        } catch (ClientException ce) {
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "";
    }
}
