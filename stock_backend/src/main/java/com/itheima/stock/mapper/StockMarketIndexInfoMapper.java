package com.itheima.stock.mapper;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.pojo.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 寂笙
* @description 针对表【stock_market_index_info(股票大盘数据详情表)】的数据库操作Mapper
* @createDate 2022-12-10 21:46:55
* @Entity com.itheima.stock.pojo.StockMarketIndexInfo
*/
@Mapper
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据大盘id和时间查询大盘信息
     * @param marketIds 大盘ID
     * @param timePoint 当前时间点(默认精确到分钟)
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("marketIds") List<String> marketIds,@Param("timePoint") Date timePoint);

    /**
     * 根据时间范围和大盘id查询每分钟的交易量
     * @param marketIds
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> getStockTradeVol(@Param("marketIds") List<String> marketIds, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
