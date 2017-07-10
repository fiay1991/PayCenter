package com.park.paycenter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * @author liuyang
 * 时间：2016年4月25日
 * 功能：动态数据源
 * 备注：
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() { 
        return DynamicDataSourceContextHolder.getDataSourceType();
    }

}
