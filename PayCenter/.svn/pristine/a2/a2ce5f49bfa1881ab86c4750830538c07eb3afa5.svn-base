package com.park.paycenter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.FailNotifyRecordDao;
import com.park.paycenter.domain.FailNotifyRecord;
import com.park.paycenter.mapper.FailNotifyRecordMapper;

/**
 * @author fangct
 * 时间：2017年6月16日
 * 功能：异步通知失败DAO
 * 备注：
 */
//@Component
@Repository(value = "FailNotifyRecordDaoImpl")
public class FailNotifyRecordDaoImpl implements FailNotifyRecordDao {
	
	@Resource(name = "FailNotifyRecordMapper")
	private FailNotifyRecordMapper failNotifyRecordMapper;
	
	public List<FailNotifyRecord> findAll() {
		return failNotifyRecordMapper.findAll();
	}

	public List<FailNotifyRecord> findByStatus(String status, String next_notify_time) {
		return failNotifyRecordMapper.findByStatus(status, next_notify_time);
	}

	public Integer insert(FailNotifyRecord failNotifyRecord) {
		return failNotifyRecordMapper.insert(failNotifyRecord);
	}

	public Integer update(FailNotifyRecord failNotifyRecord) {
		return failNotifyRecordMapper.update(failNotifyRecord);
	}

	public List<FailNotifyRecord> findNoSendMail() {
		return failNotifyRecordMapper.findNoSendMail();
	}
}
