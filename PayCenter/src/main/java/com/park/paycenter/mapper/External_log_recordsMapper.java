package com.park.paycenter.mapper;


import org.springframework.stereotype.Repository;

import com.park.paycenter.domain.External_log_records;

/**
 * @author wangfanfan 
 * 时间：2016年9月20日 
 * 功能：支付中心-外部接口调用记录Mapper 
 * 备注：
 */
@Repository(value = "External_log_recordsMapper")
public interface External_log_recordsMapper {

	/**
	 * 插入
	 * @param internal_log_records
	 * @return
	 */
	Integer insert(External_log_records external_log_records);

}
