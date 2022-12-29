package com.itheima.stock.config.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 金宗文
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "task.pool")
public class TaskThreadPoolInfoConfig {
    /**
     *  核心线程数（获取硬件）：线程池创建时候初始化的线程数
     */
    private Integer corePoolSize;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize;
    /**
     * 空闲线程最大存活时间
     */
    private Integer keepAliveSeconds;
    /**
     * 队列容量
     */
    private Integer queueCapacity;
}
