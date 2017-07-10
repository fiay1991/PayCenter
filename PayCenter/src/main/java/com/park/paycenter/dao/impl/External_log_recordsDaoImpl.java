package com.park.paycenter.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.External_log_recordsDao;
import com.park.paycenter.domain.External_log_records;
import com.park.paycenter.mapper.External_log_recordsMapper;

@Repository(value = "External_log_recordsDaoImpl")
public class External_log_recordsDaoImpl implements External_log_recordsDao {

	@Resource(name = "External_log_recordsMapper")
	private External_log_recordsMapper external_log_recordsMapper;

	@Override
	public Integer add(External_log_records external_log_records) {
		// TODO Auto-generated method stub
		return external_log_recordsMapper.insert(external_log_records);
	}

}
