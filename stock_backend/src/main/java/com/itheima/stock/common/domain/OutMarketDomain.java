package com.itheima.stock.common.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author by itheima
 * @Date 2022/1/9
 * @Description 定义封装多内大盘数据的实体类
 */
@Data
public class OutMarketDomain {


    /**
     * 当前点数
     */
    private BigDecimal curPoint;
    /**
     * 当前时间
     */
    private String curTime;
    /**
     * 当前名字
     */
    private String name;
    /**
     * 当前涨幅
     */
    private BigDecimal upDownRate;
    /**
     * 当前交易额
     */
    private BigDecimal tradePrice;
}
