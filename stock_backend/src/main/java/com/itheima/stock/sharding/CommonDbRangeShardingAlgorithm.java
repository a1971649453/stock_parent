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
public class CommonDbRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    /**
     *
     * @param dsNames 数据源名称集合
     * @param shardingValue 范围查询片键值相关信息的封装
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> dsNames, RangeShardingValue<Date> shardingValue) {
        //1.获取范围封装对象
        Range<Date> valueRange = shardingValue.getValueRange();
        //2.判断是否有下限值

        if (valueRange.hasLowerBound()){
            //获取下限日期
            Date lowerDate = valueRange.lowerEndpoint();
            //获取年份
            int year = new DateTime(lowerDate).getYear();
            dsNames = dsNames.stream().filter(dsName -> Integer.valueOf(dsName.substring(dsName.lastIndexOf("-")+1)) >= year)
                    .collect(Collectors.toList());
        }
        //2.2判断是否有上限值
        if (valueRange.hasUpperBound()){
            //获取上限日期
            Date upperDate = valueRange.upperEndpoint();
            //获取年份
            int year = new DateTime(upperDate).getYear();
            dsNames = dsNames.stream().filter(dsName -> Integer.valueOf(dsName.substring(dsName.lastIndexOf("-")+1)) <= year)
                    .collect(Collectors.toList());
        }
        return dsNames;
    }
}
