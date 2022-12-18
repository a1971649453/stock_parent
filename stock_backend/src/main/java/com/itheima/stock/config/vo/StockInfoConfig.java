package com.itheima.stock.config.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 金宗文
 * @version 1.0
 * @Description: 定义a股大盘和外盘编码集合
 */
@Data
@ConfigurationProperties(prefix = "stock")
public class StockInfoConfig {
    /**
     * A股大盘code集合
     */
    private List<String> inner;

    /**
     * 外国大盘code集合
     */
    private List<String> outer;

    /**
     * 定义股票涨幅区间顺序
     */
    private List<String> upDownRange;

    /**
     * 大盘参数Url地址
     */
    private String marketUrl;

    /**
     * 板块Url地址
     */
    private String blockUrl;
}
