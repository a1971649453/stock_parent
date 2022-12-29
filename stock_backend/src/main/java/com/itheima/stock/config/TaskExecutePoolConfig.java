package com.itheima.stock.config;

import com.itheima.stock.config.vo.TaskThreadPoolInfoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * @author 金宗文
 * @version 1.0
 */
@Configuration
public class TaskExecutePoolConfig {

    @Resource
    private TaskThreadPoolInfoConfig taskThreadPoolInfoConfig;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        //1.构建线程池对象
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //2.设置参数
        //2.1设置核心线程数
        executor.setCorePoolSize(taskThreadPoolInfoConfig.getCorePoolSize());
        //2.2设置最大线程数
        executor.setMaxPoolSize(taskThreadPoolInfoConfig.getMaxPoolSize());
        //2.3设置线程最大存活时间
        executor.setKeepAliveSeconds(taskThreadPoolInfoConfig.getKeepAliveSeconds());
        //2.4设设置任务队列
        executor.setQueueCapacity(taskThreadPoolInfoConfig.getQueueCapacity());
        //2.5设置线程池的任务拒绝策略
//        executor.setRejectedExecutionHandler(null);
        //2.6设置核心线程超时回收
        executor.setAllowCoreThreadTimeOut(true);
        //3.参数初始化
        executor.initialize();
        //4.返回
        return executor;
    }

}
