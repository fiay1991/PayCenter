package com.park.paycenter.service;

import org.springframework.stereotype.Repository;

/**
 * @author wangfanfan 时间：2016年9月20日 功能： 备注：
 */
@Repository(value = "External_log_recordsService")
public interface External_log_recordsService {

	/**
	 * 生成外部接口调用记录日志
	 * @param timestamp
	 * @param payment_platform
	 * @param req_interface
	 * @param req_params
	 * @param return_result
	 * @return
	 */
	Integer creat_external_log_records(String payment_platform, String req_interface, String req_params, String return_result);
}
