package com.park.paycenter.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.Internal_log_recordsDao;
import com.park.paycenter.domain.Internal_log_records;
import com.park.paycenter.mapper.Internal_log_recordsMapper;

@Repository(value = "Internal_log_recordsDaoImpl")
public class Internal_log_recordsDaoImpl implements Internal_log_recordsDao {

	@Resource(name = "Internal_log_recordsMapper")
	private Internal_log_recordsMapper internal_log_recordsMapper;

	@Override
	public Integer add(Internal_log_records internal_log_records) {
		// TODO Auto-generated method stub
		return internal_log_recordsMapper.insert(internal_log_records);
	}

}
