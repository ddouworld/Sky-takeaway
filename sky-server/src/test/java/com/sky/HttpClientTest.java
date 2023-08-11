package com.sky;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author 尹志伟
 * @date 2023/7/6 00:35:51
 * @Description
 */
@SpringBootTest
@Slf4j
public class HttpClientTest {

    @Test
    public void getUrl() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:8081/admin/dish/list");
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            System.out.println(execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void postUrl() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8081/admin/dish/list");
        httpPost.setHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg2MjE0MzMsImVtcElkIjoxfQ.Cbn1_F-jTEso-dQcYEi01M-9PQ9t68SXgf4n67hZrIQ");
        //封装参数
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("categoryId", 11);
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentEncoding("utf-8");
            //数据格式
            /*entity.setContentType("application/json");*/
            httpPost.setEntity(entity);
            //调用
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (200 == code) {
                HttpEntity entityResult = response.getEntity();
                String body = EntityUtils.toString(entityResult);
                System.out.println("响应数据为：" + body);
            }
            //关闭资源
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
