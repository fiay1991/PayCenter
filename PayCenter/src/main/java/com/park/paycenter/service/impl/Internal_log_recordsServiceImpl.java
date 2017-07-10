package com.park.paycenter.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.park.paycenter.dao.Internal_log_recordsDao;
import com.park.paycenter.domain.Internal_log_records;
import com.park.paycenter.service.Internal_log_recordsService;

/**
 * @author wangfanfan
 * 时间：2016年9月20日
 * 功能：支付中心-内部接口调用记录
 * 备注：
 */
@Transactional
@Repository(value = "Internal_log_recordsServiceImpl")
public class Internal_log_recordsServiceImpl implements Internal_log_recordsService {
	
	@Resource(name="Internal_log_recordsDaoImpl")
	private Internal_log_recordsDao internal_log_recordsDao;

	@Override
	public Integer creat_internal_log_records(String visitor,String user_environment, String req_params, String req_interface,String return_result) {
		// TODO Auto-generated method stub
		Internal_log_records internal_log_records = new Internal_log_records();
		internal_log_records.setVisitor(visitor);
		internal_log_records.setUser_environment(user_environment);
		internal_log_records.setReq_params(req_params);
		internal_log_records.setReq_interface(req_interface);
		internal_log_records.setReturn_result(return_result);
		return internal_log_recordsDao.add(internal_log_records);
	}

}
