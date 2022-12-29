package com.itheima.stock.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 金宗文
 * @version 1.0
 * 定义公共范围匹配数据的类
 */
public class CommonTbRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    /**
     *
     * @param tbNames 逻辑表名称及合
     * @param shardingValue 范围查询片键值相关信息的封装
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> tbNames, RangeShardingValue<Date> shardingValue) {
        return tbNames;
    }
}
