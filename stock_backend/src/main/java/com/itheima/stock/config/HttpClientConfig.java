package com.itheima.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 金宗文
 * @version 1.0
 */
@Configuration
public class HttpClientConfig {

    /**
     * 定义RestTemplate模板bean
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
