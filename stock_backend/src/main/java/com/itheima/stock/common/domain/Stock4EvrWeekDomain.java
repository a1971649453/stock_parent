package com.itheima.stock.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author by itheima
 * @Date 2022/2/28
 * @Description 个股日K数据封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock4EvrWeekDomain {
    /**
     * 日期，eg:202201280809
     */
   private String mxTime;

    /**
     * 股票编码
     */
   private String stock_code;
    /**
     * 最低价
     */
   private BigDecimal minPrice;
    /**
     * 最高价
     */
    private BigDecimal maxPrice;

    /**
     * 开盘价
     */
    private BigDecimal openPrice;
    /**
     * 当前收盘价格指收盘时的价格，如果当天未收盘，则显示最新cur_price）
     */
    private BigDecimal closePrice;
    /**
     * 平均价格
     */
   private BigDecimal avgPrice;

}
