package com.itheima.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.itheima.stock.common.domain.*;
import com.itheima.stock.config.vo.StockInfoConfig;
import com.itheima.stock.mapper.StockBlockRtInfoMapper;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.service.StockService;
import com.itheima.stock.utils.DateTimeUtil;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import com.itheima.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 金宗文
 * @version 1.0
 */
@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {

    @Resource
    private StockBusinessMapper stockBusinessMapper;

    @Resource
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Resource
    private StockInfoConfig stockInfoConfig;

    @Resource
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Resource
    private StockRtInfoMapper stockRtInfoMapper;


    @Override
    public List<StockBusiness> findAll() {
        return stockBusinessMapper.findAll();
    }

    @Override
    public R<List<InnerMarketDomain>> getNewAMarketInfo() {

        //1.获取国内A股大盘id的集合
        List<String> inners = stockInfoConfig.getInner();
        //2.获取最近股票交易日期
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //3.将最近日期转化为Java中Date
        Date lastDate = lastDateTime.toDate();
        //TODO mock测试 后期通过第三方接口动态获取实时数据
        lastDate = DateTime.parse("2022-01-03 11:15:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //4.将获取的Date传入接口
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getMarketInfo(inners,lastDate);
        //5.返回查询结果
        return R.ok(list);

    }

    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @Override
    public R<List<StockBlockRtInfo>> sectorAllLimit() {
        //1.调用mapper接口获取数据 TODO 优化 避免全表查询 根据时间范围查询，提高查询效率
        List<StockBlockRtInfo> infos=stockBlockRtInfoMapper.sectorAllLimit();
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    /**
     * 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前10条数据
     * @return
     */
    @Override
    public R<List<StockUpdownDomain>> getStockRtInfoLimit() {
        //1.获取最近的有效交易时间点(精确到分钟)
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据
        lastDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.调用mapper查询
        List<StockUpdownDomain> list = stockRtInfoMapper.getStockRtInfoLimit(lastDate);
        //3.判断集合
        if (CollectionUtils.isEmpty(list)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }

        return R.ok(list);
    }

    /**
     * 分页查询股票交易数据 按照日期和涨幅降序排序
     * @param page 当前页
     * @param pageSize 每页显示多少
     * @return
     */
    @Override
    public R<PageResult<StockUpdownDomain>> getStockRtInfo4Page(Integer page, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(page,pageSize);
        //2.查询
        List<StockUpdownDomain> pages = stockRtInfoMapper.getStockRtInfo4All();
        if (CollectionUtils.isEmpty(pages)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        //3.组装PageInfo对象
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(pages);
        //4.转换成自定义的PageResult对象
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);

    }

    /**
     * 统计T日(最近最新一次股票交易日) 涨停和跌停每分钟的数量
     * @return
     */
    @Override
    public R<Map> getStockUpdownCount() {
        //1.获取最近的有效交易时间的 开盘和收盘时间
        //获取有效时间点
        DateTime avableTimePoint = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //2.获取最近的有效交易时间的 开盘和收盘时间
        Date openTime = DateTimeUtil.getOpenDate(avableTimePoint).toDate();
        Date closeTime = DateTimeUtil.getCloseDate(avableTimePoint).toDate();
        //TODO mock数据
        openTime = DateTime.parse("2021-12-19 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        closeTime = DateTime.parse("2021-12-19 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.查询长停的统计数据
        List<Map> upList = stockRtInfoMapper.getStockUpDownCount(openTime,closeTime,1);
        //3.查询跌停的统计数据
        List<Map> downList = stockRtInfoMapper.getStockUpDownCount(openTime,closeTime,0);
        //4.组装map
        Map<String,List<Map>> map = new HashMap<>();
        map.put("upList",upList);
        map.put("downList",downList);
        //5.返回结果
        return R.ok(map);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.设置响应数据的类型:excel
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //4.分页查询股票数据
            PageHelper.startPage(page, pageSize);
            List<StockUpdownDomain> pages = this.stockRtInfoMapper.getStockRtInfo4All();
            if (CollectionUtils.isEmpty(pages)) {
                R<String> error = R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
                String jsonData = new Gson().toJson(error);
                response.getWriter().write(jsonData);
                //终止当前程序
                return;
            }
            Gson gson = new Gson();
            //将List<StockUpdownDomain>转换成List<StockExcelDomain>
            List<StockExcelDomain> excelDomains = pages.stream().map(info -> {
                StockExcelDomain domain = new StockExcelDomain();
                BeanUtils.copyProperties(info, domain);
                return domain;
            }).collect(Collectors.toList());
            //5.导出
            EasyExcel.write(response.getOutputStream(), StockExcelDomain.class).sheet("stockInfo").doWrite(excelDomains);
        } catch (IOException e) {
            log.info("股票excel数据导出异常，当前页：{}，每页大小：{}，异常信息：{}", page, pageSize, e.getMessage());
        }
    }

    /**
     * 统计国内大盘A股T日和T-1日成交量对比功能
     * 吐过当前时间点不股票交易日内,则将距离当前最近的一个时间点作为T日时间查询
     * @return
     */
    @Override
    public R<Map> getSockTradeCol4Comparison() {
        //1.获取T日和T-1日的开始时间和结束时间
        //1.1获取最近股票交易日的时间点
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateTime);
        //1.2转化为Java中的Date类型
        Date startTime4T = openDateTime.toDate();
        Date endTime4T = lastDateTime.toDate();
        //TODO mock数据
        startTime4T = DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endTime4T = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //1.3获取T-1日的开始时间和结束时间
        //获取lastDateTime的上一个有效股票交易日
        DateTime preLastDateTime = DateTimeUtil.getPreviousTradingDay(lastDateTime);
        DateTime ptrOpenDateTime = DateTimeUtil.getOpenDate(preLastDateTime);
        //转为Java中的Date类型
        Date startTime4PreT = ptrOpenDateTime.toDate();
        Date endTime4PreT = preLastDateTime.toDate();
        //TODO mock数据
        startTime4PreT = DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endTime4PreT = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.获取上证和深证的配置的大盘id
        List<String> markIds = stockInfoConfig.getInner();
        //3.分别查询T日和T-1日交易量数据
        List<Map> data4T = stockMarketIndexInfoMapper.getStockTradeVol(markIds,startTime4T,endTime4T);
        if (CollectionUtils.isEmpty(data4T)) {
            data4T = new ArrayList<>();
        }
        //3.1T-1日交易量数据
        List<Map> data4PreT = stockMarketIndexInfoMapper.getStockTradeVol(markIds,startTime4PreT,endTime4PreT);
        if (CollectionUtils.isEmpty(data4PreT)) {
            data4T = new ArrayList<>();
        }
        //4.组装数据
        HashMap<String, List> info = new HashMap<>();
        info.put("volList",data4T);
        info.put("yesVolList",data4PreT);
        //4.返回数据
        return R.ok(info);
    }

    @Override
    public R<Map> getsStockUpDownRegion() {
        //1.获取最近股票交易时间点
        DateTime lastDate4Stock = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date lastDate = lastDate4Stock.toDate();
        //TODO mock数据
        lastDate = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.从mapper中获取统计数据
        List<Map> infos = stockRtInfoMapper.getsStockUpDownRegion(lastDate);
        if (CollectionUtils.isEmpty(infos)){
            infos = new ArrayList<Map>();
        }
        //保证涨幅区间按照从小到大排序,且对于没有数据的涨幅区间默认0
        //获取涨幅区间顺序集合

        List<String> upDownRangeList = stockInfoConfig.getUpDownRange();
        List<Map> finalInfos = infos;
        List<Map> newMaps = upDownRangeList.stream().map(item -> {
            Optional<Map> optional = finalInfos.stream().filter(map -> map.get("title").equals(item)).findFirst();
            Map tmp = null;
            //判断结果是否有map
            if (optional.isPresent()) {
                tmp = optional.get();
            }else {
                tmp = new HashMap();
                tmp.put("title",item);
                tmp.put("count",0);
            }
            return tmp;
        }).collect(Collectors.toList());
//        List<Map> newMaps = new ArrayList<>();
//        for (String item : upDownRangeList) {
//            //循环查询info集合找到item对应的map
//            Map tmp = null;
//            for (Map info : infos) {
//                if (info.get("title").equals(item)) {
//                    tmp = info;
//                }
//            }
//            if (tmp==null){
//                tmp = new HashMap<>();
//                tmp.put("title",item);
//                tmp.put("count",0);
//            }
//            newMaps.add(tmp);
//        }
        //3.组装数据
        HashMap<String, Object> data = new HashMap<>();
        //获取日期格式
        String stringDateTime = lastDate4Stock.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        data.put("time",stringDateTime);
        data.put("infos",newMaps);
        //4.返回数据
        return R.ok(data);
    }

    @Override
    public R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String stockCode) {
        //1.1获取最近股票有效交易时间点
        DateTime lastDate4Stock = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = lastDate4Stock.toDate();
        //TODO mock数据
        endTime = DateTime.parse("2021-12-30 14:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2.获取最近股票有效开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(lastDate4Stock);
        Date startTime = openDate.toDate();
        //TODO mock数据
        startTime = DateTime.parse("2021-12-30 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.根据股票code和时间范围查询数据
        List<Stock4MinuteDomain> list = stockRtInfoMapper.getStockInfoByCodeAndDate(stockCode,startTime,endTime);
        if (CollectionUtils.isEmpty(list)){
            list = new ArrayList<>();
        }
        //3.返回数据
        return R.ok(list);
    }

    @Override
    public R<List<Stock4EvrDayDomain>> stockScreenDkLine(String code) {
        //1.获取查询的日期范围
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = endDateTime.toDate();
        //TODO mock数据
        endTime = DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        DateTime startDateTime = endDateTime.minusDays(10);
        Date startTime = startDateTime.toDate();
        //TODO mock数据
        startTime = DateTime.parse("2022-01-01 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.调用mapper查询数据
//        List<Stock4EvrDayDomain> data = stockRtInfoMapper.getStockInfo4EvrDay(code,startTime,endTime);
        //获取指定范围的收盘日期集合
        List<Date> closeDates=stockRtInfoMapper.getCloseDates(code,startTime,endTime);
        //根据收盘日期精准查询，如果不在收盘日期，则查询最新数据
        List<Stock4EvrDayDomain> data= stockRtInfoMapper.getStockCreenDkLineData(code,closeDates);
        if (CollectionUtils.isEmpty(data)){
            data = new ArrayList<>();
        }
        //3.相应数据
        return R.ok(data);
    }

    @Override
    public R<List<Stock4EvrWeekDomain>> stockScreenWeekLine(String code) {
        //1.获取查询的日期范围
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = endDateTime.toDate();
        //TODO mock数据
        endTime = DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //获取这周的周一开盘时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        // N：0-表示本周，1-表示下周，-1-表示上周
        cal.add(Calendar.DATE, 0 * 7);
        // Calendar.MONDAY 表示获取周一的日期; Calendar.WEDNESDAY:表示周三的日期

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateTime startDate = new DateTime(cal.getTime());
        DateTime startDateTime = DateTimeUtil.getOpenDate(startDate);
        Date startTime = startDateTime.toDate();
        //TODO mock数据
        startTime = DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.调用mapper查询数据
//        List<Stock4EvrDayDomain> data = stockRtInfoMapper.getStockInfo4EvrDay(code,startTime,endTime);
        //获取指定范围的收盘日期集合
        List<Date> closeDates=stockRtInfoMapper.getCloseDates(code,startTime,endTime);
        //根据收盘日期精准查询，如果不在收盘日期，则查询最新数据
        List<Stock4EvrWeekDomain> data= stockRtInfoMapper.getStockCreenWLineData(code,closeDates);
        if (CollectionUtils.isEmpty(data)){
            data = new ArrayList<>();
        }
        //3.相应数据
        return R.ok(data);
    }

    @Override
    public R<List<OutMarketDomain>> getNewOutMarketLimit() {
        //1.获取国外大盘id集合
        List<String> outers = stockInfoConfig.getOuter();
        //2.获取最近交易日期
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //3.将最近日期转化为Java中Date
        //3.将最近日期转化为Java中Date
        Date lastDate = lastDateTime.toDate();
        //TODO mock测试 后期通过第三方接口动态获取实时数据
        lastDate = DateTime.parse("2021-12-29 11:15:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //4.将获取的Date传入接口
        List<OutMarketDomain> list = stockMarketIndexInfoMapper.getNewOutMarketLimit(outers,lastDate);
        if (CollectionUtils.isEmpty(list)){
            list = new ArrayList<>();
        }
        //5.返回查询结果
        return R.ok(list);
    }

    @Override
    public R<List<Map>> getSuggestStock(String searchStr) {

        //1.调用mapper查询数据
        List<Map> infos = stockRtInfoMapper.getSuggestStock(searchStr);
        //2.组装数据
        List<Map> finalInfos = infos;

        return R.ok(finalInfos);
    }

    @Override
    public R<Stock4DescriptionDomain> getStockDescription(String code) {
        //1.调用mapper查询数据
        Stock4DescriptionDomain stock4DescriptionDomain = stockBusinessMapper.getStockDescription(code);
        if (stock4DescriptionDomain == null){
            stock4DescriptionDomain = new Stock4DescriptionDomain();
        }
        //3.返回数据
        return R.ok(stock4DescriptionDomain);
    }

    @Override
    public R<Stock4HourDetailsDomain> getStockHourDetails(String code) {
        //获取最新有效交易时间点
        DateTime lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //转为Java中Date对象
        Date lastDateTime = lastDate.toDate();
        //TODO mock测试 后期通过第三方接口动态获取实时数据
        lastDateTime = DateTime.parse("2021-12-30 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //调用mapper
        Stock4HourDetailsDomain stock4HourDetailsDomain = stockRtInfoMapper.getStockHourDetails(code,lastDateTime);
        return R.ok(stock4HourDetailsDomain);
    }

    @Override
    public R<List<Map>> getStockNewFlow() {
        //1.1获取最近股票有效交易时间点
        DateTime lastDate4Stock = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = lastDate4Stock.toDate();
        //TODO mock数据
        endTime = DateTime.parse("2021-12-30 14:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2.获取最近股票有效开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(lastDate4Stock);
        Date startTime = openDate.toDate();
        //TODO mock数据
        startTime = DateTime.parse("2021-12-30 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.调用mapper查询数据
        List<Map> data = stockRtInfoMapper.getStockNewFlow(startTime,endTime);
        if (CollectionUtils.isEmpty(data)){
            data = new ArrayList<>();
        }
        return R.ok(data);
    }
}
