package com.itheima.stock.mapper;

import com.itheima.stock.pojo.StockBusiness;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 寂笙
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2022-12-10 21:46:55
* @Entity com.itheima.stock.pojo.StockBusiness
*/
@Mapper
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    List<StockBusiness> findAll();
}
