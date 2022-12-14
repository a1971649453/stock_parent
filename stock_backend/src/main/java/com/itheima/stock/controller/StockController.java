package com.itheima.stock.controller;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.common.domain.StockUpdownDomain;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.service.StockService;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 金宗文
 * @version 1.0
 */
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock/business/all")
    public List<StockBusiness> findAllBusinessInfo(){
        return stockService.findAll();
    }

    /**
     * 获取最新的A股大盘信息
     * 如果不在交易日 显示最近最新的交易数据信息
     * @return
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getNewAMarket(){
        return stockService.getNewAMarketInfo();
    }

    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @GetMapping("/sector/all")
    public R<List<StockBlockRtInfo>> sectorAll(){
        return stockService.sectorAllLimit();
    }


    /**
     * 需求说明: 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前10条数据
     * @return
     */
    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> getStockRtInfoLimit(){
        return stockService.getStockRtInfoLimit();
    }

    /**
     * 分页查询股票交易数据 按照日期和涨幅降序排序
     * @param page 当前页
     * @param pageSize 每页显示多少
     * @return
     */
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> getStockRtInfo4Page(Integer page,Integer pageSize){
        return stockService.getStockRtInfo4Page(page,pageSize);
    }

    /**
     * 统计T日(最近最新一次股票交易日) 涨停和跌停每分钟的数量
     * @return
     */
    @GetMapping("/stock/updown/count")
    public R<Map> getStockUpdownCount(){
        return stockService.getStockUpdownCount();
    }

    /**
     * 导出股票信息到Excel
     * @param resp http响应对象 可获取流对象
     * @param page 当前页
     * @param pageSize 每页大小
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse resp,Integer page,Integer pageSize){
        stockService.stockExport(resp,page,pageSize);
    }
}















