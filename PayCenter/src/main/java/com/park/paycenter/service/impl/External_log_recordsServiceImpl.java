package com.park.paycenter.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.park.paycenter.dao.External_log_recordsDao;
import com.park.paycenter.domain.External_log_records;
import com.park.paycenter.service.External_log_recordsService;

/**
 * @author wangfanfan
 * 时间：2016年9月20日
 * 功能：支付中心-外部接口调用记录
 * 备注：
 */
@Transactional
@Repository(value = "External_log_recordsServiceImpl")
public class External_log_recordsServiceImpl implements External_log_recordsService {
	
	@Resource(name="External_log_recordsDaoImpl")
	private External_log_recordsDao internal_log_recordsDao;

	@Override
	public Integer creat_external_log_records(String payment_platform,String req_interface, String req_params, String return_result) {
		// TODO Auto-generated method stub
		External_log_records external_log_records = new External_log_records();
		external_log_records.setPayment_platform(payment_platform);
		external_log_records.setReq_interface(req_interface);
		external_log_records.setReq_params(req_params);
		external_log_records.setReturn_result(return_result);
		return internal_log_recordsDao.add(external_log_records);
	}

}
