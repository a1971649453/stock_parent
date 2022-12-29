package com.itheima.stock.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 金宗文
 * @version 1.0
 * 定义股票流水范围查询的算法类
 */
public class StockRtInfoRangeShardingAlgorithm4Table implements RangeShardingAlgorithm<Date> {

    /**
     *
     * @param tbNames 分表名称集合
     * @param shardingValue 范围查询片键值相关信息的封装
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> tbNames, RangeShardingValue<Date> shardingValue) {
        //1.获取范围封装对象
        Range<Date> valueRange = shardingValue.getValueRange();
        //2.判断是否有下限值

        if (valueRange.hasLowerBound()){
            //获取下限日期
            Date lowerDate = valueRange.lowerEndpoint();
            //获取年月组合的字符串
            String dateStr = new DateTime(lowerDate).toString(DateTimeFormat.forPattern("yyyyMM"));
            Integer intDate = Integer.valueOf(dateStr);
            //从tbNames 获取大于等于intDate的名称集合
            tbNames = tbNames.stream().filter(tbName -> Integer.valueOf(tbName.lastIndexOf("-")+1)>= intDate)
                    .collect(Collectors.toList());
        }
        //2.2判断是否有上限值
        if (valueRange.hasUpperBound()){
            //获取下限日期
            Date highDate = valueRange.upperEndpoint();
            //获取年月组合的字符串
            String dateStr = new DateTime(highDate).toString(DateTimeFormat.forPattern("yyyyMM"));
            Integer intDate = Integer.valueOf(dateStr);
            //从tbNames 获取大于等于intDate的名称集合
            tbNames = tbNames.stream().filter(tbName -> Integer.valueOf(tbName.lastIndexOf("-")+1)<= intDate)
                    .collect(Collectors.toList());
        }
        return tbNames;
    }
}
