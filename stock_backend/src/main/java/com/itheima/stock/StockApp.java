package com.itheima.stock;

import com.itheima.stock.config.vo.TaskThreadPoolInfoConfig;
import com.itheima.stock.config.vo.StockInfoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StockInfoConfig.class, TaskThreadPoolInfoConfig.class})//开启配置初始化,加入Ioc容器
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }
}
