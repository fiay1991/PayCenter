package com.park.paycenter.dao;

import org.springframework.stereotype.Repository;

import com.park.paycenter.domain.Internal_log_records;

/**
 * @author wangfanfan
 * 时间：2016年9月20日
 * 功能：支付中心-内部接口调用记录
 * 备注：
 */
@Repository(value = "Internal_log_recordsDao")
public interface Internal_log_recordsDao {

	/**
	 * 插入
	 * @param internal_log_records
	 * @return
	 */
	Integer add(Internal_log_records internal_log_records);
}
