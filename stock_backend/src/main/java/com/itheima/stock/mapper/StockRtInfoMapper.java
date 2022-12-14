package com.itheima.stock.mapper;

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
}
