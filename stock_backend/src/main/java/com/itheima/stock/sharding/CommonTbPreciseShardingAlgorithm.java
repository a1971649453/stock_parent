package com.itheima.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;

/**
 * @author 金宗文
 * @version 1.0
 * 定义公共精准匹配表的类
 */
public class CommonTbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    /**
     *
     * @param tbNames 物理表名称集合
     * @param shardingValue 分片键相关信息的封装
     * @return
     */
    @Override
    public String doSharding(Collection<String> tbNames, PreciseShardingValue<Date> shardingValue) {

        //板块和大盘流水表 仅仅做了分库处理 没有做分表处理 逻辑表与物理表一致
        String logicTableName = shardingValue.getLogicTableName();
        return logicTableName;
    }
}
