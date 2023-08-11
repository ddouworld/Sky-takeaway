package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.utils.template.AliOssTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliOssConfig {
    @Bean
    public AliOssTemplate aliOssTemplate(AliOssProperties aliOssProperties) {
        return new AliOssTemplate(aliOssProperties);
    }
}