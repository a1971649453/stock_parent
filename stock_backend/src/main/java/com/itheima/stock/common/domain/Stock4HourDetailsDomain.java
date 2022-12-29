package com.itheima.stock.common.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 金宗文
 * @version 1.0
 */
@Data
public class Stock4HourDetailsDomain {

    /**
     * 日期，eg:202201280809
     */
    private String curDate;
    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 最低价
     */
    private BigDecimal lowPrice;

    /**
     * 最高价
     */
    private BigDecimal highPrice;
    /**
     * 开盘价
     */
    private BigDecimal openPrice;
    /**
     * 当前交易总金额
     */
    private BigDecimal tradeVol;
    /**
     * 当前价格
     */
    private BigDecimal tradePrice;
    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;
}
