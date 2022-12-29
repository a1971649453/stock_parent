package com.itheima.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;

/**
 * @author 金宗文
 * @version 1.0
 * 定义公共精准匹配数据的类
 */
public class CommonDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    /**
     *
     * @param dbNames 数据源集合
     * @param shardingValue 分片键相关信息的封装
     * @return
     */
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Date> shardingValue) {
        //获取分片的字段
        String columnName = shardingValue.getColumnName();
        // 获取分片的表名
        String logicTableName = shardingValue.getLogicTableName();
        // 获取分片键的值
        Date date = shardingValue.getValue();
        //获取年份
        int year = new DateTime(date).getYear();
        //从数据源集合中查找以指定年结尾的数据源
        String dsName = dbNames.stream().filter(dbName -> dbName.endsWith(year + "")).findFirst().get();
        return dsName;


    }
}
