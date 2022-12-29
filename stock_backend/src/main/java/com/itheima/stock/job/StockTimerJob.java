package com.itheima.stock.job;

import com.itheima.stock.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 金宗文
 * @version 1.0
 * 定义配置xxljob 执行任务的bean对象
 */
@Component
public class StockTimerJob {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;
    @XxlJob("testXxlJob")
    public void testXxlJob(){
        System.out.println("testXxlJob on...");

    }

    @XxlJob("getInnerMarketInfo")
    public void getInnerMarketInfo(){
        stockTimerTaskService.collectInnerMarketInfo();
    }

    @XxlJob("getAshareInfos")
    public void getAshareInfos(){
        stockTimerTaskService.collectAShareInfo();
    }

    @XxlJob("getStockBlockInfoTask")
    public void getStockBlockInfoTask(){
        stockTimerTaskService.getStockSectorRtIndex();
    }
}
