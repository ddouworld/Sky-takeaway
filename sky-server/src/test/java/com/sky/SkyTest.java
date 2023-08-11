package com.sky;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 尹志伟
 * @date 2023/7/5 18:02:18
 * @Description
 */
@SpringBootTest
public class SkyTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void redisTest() {
       String key = "yzw";
       String value = "13548941063";
       redisTemplate.opsForValue().set(key,value,5, TimeUnit.MINUTES);
    }
}
