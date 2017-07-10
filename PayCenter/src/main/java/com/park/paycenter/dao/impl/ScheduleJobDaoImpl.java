package com.park.paycenter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.ScheduleJobDao;
import com.park.paycenter.domain.ScheduleJob;
import com.park.paycenter.mapper.ScheduleJobMapper;

@Repository(value="scheduleJobDaoImpl")
public class ScheduleJobDaoImpl implements ScheduleJobDao {

	@Resource(name="scheduleJobMapper")
	ScheduleJobMapper scheduleJobMapper;
	
	public int deleteByPrimaryKey(Long jobId) {
		return scheduleJobMapper.deleteByPrimaryKey(jobId);
	}

	@Override
	public int insert(ScheduleJob record) {
		return scheduleJobMapper.insert(record);
	}

	@Override
	public int insertSelective(ScheduleJob record) {
		return scheduleJobMapper.insertSelective(record);
	}

	@Override
	public ScheduleJob selectByPrimaryKey(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public int updateByPrimaryKeySelective(ScheduleJob record) {
		return scheduleJobMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ScheduleJob record) {
		return scheduleJobMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ScheduleJob> getAll() {
		return scheduleJobMapper.getAll();
	}

	@Override
	public List<ScheduleJob> checkUnique(ScheduleJob record) {
		return scheduleJobMapper.checkUnique(record);
	}

}
