package com.itheima.stock.mapper;

import com.itheima.stock.common.domain.Stock4EvrDayDomain;
import com.itheima.stock.common.domain.Stock4MinuteDomain;
import com.itheima.stock.common.domain.StockUpdownDomain;
import com.itheima.stock.pojo.StockRtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 寂笙
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2022-12-10 21:46:55
* @Entity com.itheima.stock.pojo.StockRtInfo
*/
@Mapper
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    /**
     * 查询指定时间点下数据,然后根据涨幅顺序,取前10
     * @param timePoint 时间点 精确到分钟
     * @return
     */
    List<StockUpdownDomain> getStockRtInfoLimit(@Param("timePoint") Date timePoint);

    /**
     * 查询股票交易数据 按照日期和涨幅降序排序
     */
    List<StockUpdownDomain> getStockRtInfo4All();

    /**
     * 根据指定日期范围查询内每分钟的涨停或者跌停的数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param flag 标识 1:涨停 0:跌停
     * @return
     */
    List<Map> getStockUpDownCount(@Param("startTime") Date startTime,
                                  @Param("endTime") Date endTime,
                                  @Param("flag") int flag);

    /**
     *  统计在当前时间下（精确到分钟），股票在各个涨跌区间的数量
     *  如果当前时间点不股票交易日内,则将距离当前最近的一个时间点作为查询时间点时间查询
     * @param timePoint 股票交易时间点
     * @return
     */
    List<Map> getsStockUpDownRegion(Date timePoint);


    /**
     * 根据时间范围和code查询指定股票的交易流水
     * @param stockCode 股票代码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<Stock4MinuteDomain> getStockInfoByCodeAndDate(@Param("stockCode") String stockCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询指定日期范围内指定股票每天的交易数据
     * @param stockCode 股票代码
     * @param startTime 开始时间
     * @param endTime 终止时间
     * @return
     */
    List<Stock4EvrDayDomain> getStockInfo4EvrDay(@Param("stockCode") String stockCode, @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    /**
     * 获取指定日期范围内的收盘价格
     * @param code 股票编码
     * @param beginDate 起始时间
     * @param endDate 结束时间
     * @return
     */
    List<Date> getCloseDates(@Param("code") String code, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);


    /**
     * 获取指定股票在指定日期下的数据
     * @param code 股票编码
     * @param dates 指定日期集合
     * @return
     */
    List<Stock4EvrDayDomain> getStockCreenDkLineData(@Param("code") String code, @Param("dates") List<Date> dates);

    /**
     * 批量插入股票实时详情信息
     * @param stockRtInfo 股票详情集合
     */
    void insertBatch(@Param("stockRtInfos") List<StockRtInfo> stockRtInfo);
}
