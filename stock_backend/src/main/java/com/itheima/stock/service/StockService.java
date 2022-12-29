package com.itheima.stock.service;

import com.itheima.stock.common.domain.*;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 金宗文
 * @version 1.0
 * 股票相关接口
 */
public interface StockService {

    List<StockBusiness> findAll();

    /**
     * 获取最新的A股大盘信息
     * 如果不在交易日 显示最近最新的交易数据信息
     * @return
     */
    R<List<InnerMarketDomain>> getNewAMarketInfo();

    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    R<List<StockBlockRtInfo>> sectorAllLimit();

    /**
     * 需求说明: 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前10条数据
     * @return
     */
    R<List<StockUpdownDomain>> getStockRtInfoLimit();

    /**
     * 分页查询股票交易数据 按照日期和涨幅降序排序
     * @param page 当前页
     * @param pageSize 每页显示多少
     * @return
     */
    R<PageResult<StockUpdownDomain>> getStockRtInfo4Page(Integer page, Integer pageSize);

    /**
     * 统计T日(最近最新一次股票交易日) 涨停和跌停每分钟的数量
     * @return
     */
    R<Map> getStockUpdownCount();
    /**
     * 导出股票信息到Excel
     * @param resp http响应对象 可获取流对象
     * @param page 当前页
     * @param pageSize 每页大小
     */
    void stockExport(HttpServletResponse resp, Integer page, Integer pageSize);

    /**
     * 统计国内大盘A股T日和T-1日成交量对比功能
     * 吐过当前时间点不股票交易日内,则将距离当前最近的一个时间点作为T日时间查询
     * @return
     */
    R<Map> getSockTradeCol4Comparison();

    /**
     * 统计在当前时间下（精确到分钟），股票在各个涨跌区间的数量
     * 如果当前时间点不股票交易日内,则将距离当前最近的一个时间点作为查询时间点时间查询
     * @return
     */
    R<Map> getsStockUpDownRegion();

    /**
     * 查询个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据
     * @param stockCode 股票代码
     * @return
     */
    R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String stockCode);

    /**
     * 个股日K数据查询 ，可以根据时间区间查询数日的K线数据
     * 		默认查询历史20天的数据；
     * @param code 股票代码
     * @return
     */
    R<List<Stock4EvrDayDomain>> stockScreenDkLine(String code);

    /**
     * 个股周K数据查询 ，可以根据时间区间查询数周的K线数据
     * @param code
     * @return
     */
    R<List<Stock4EvrWeekDomain>> stockScreenWeekLine(String code);

    /**
     * 查询国外大盘详情数据,根据时间和大盘点数降序排序取前2
     * @return
     */
    R<List<OutMarketDomain>> getNewOutMarketLimit();

    /**
     * 模糊查询，返回证券代码和证券名称
     * @param searchStr
     * @return
     */
    R<List<Map>> getSuggestStock(String searchStr);

    /**
     * 根据股票代码查询股票描述
     * @param code
     * @return
     */
    R<Stock4DescriptionDomain> getStockDescription(String code);

    /**
     * 获取个股最新分时行情数据，主要包含：
     * 开盘价、前收盘价、最新价、最高价、最低价、成交金额和成交量、交易时间信息
     * @param code
     * @return
     */
    R<Stock4HourDetailsDomain> getStockHourDetails(String code);

    /**
     * 查询个股交易流水 最新10条
     * @return
     */
    R<List<Map>> getStockNewFlow();
}
