package com.itheima.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author 金宗文
 * @version 1.0
 * 定义公共精准匹配数据的类
 */
public class StockRtInfoPreciseShardingAlgorithm4Table implements PreciseShardingAlgorithm<Date> {
    /**
     *
     * @param tbNames 数据源集合
     * @param shardingValue 分片键相关信息的封装
     * @return
     */
    @Override
    public String doSharding(Collection<String> tbNames, PreciseShardingValue<Date> shardingValue) {
        //获取日期对象
        Date date = shardingValue.getValue();
        //获取年月组装的字符串 比如202203
        String sufixDate = new DateTime(date).toString(DateTimeFormat.forPattern("yyyyMM"));
        //从tableNames获取以sufixDate结尾的数据
        String tableName = tbNames.stream().filter(tbName -> tbName.endsWith(sufixDate)).findFirst().get();
        return tableName;
    }
}
