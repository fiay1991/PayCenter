package com.park.paycenter.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.paycenter.domain.ScheduleJob;

@Repository(value="scheduleJobMapper")
public interface ScheduleJobMapper {

	int deleteByPrimaryKey(Long jobId);

	int insert(ScheduleJob record);

	int insertSelective(ScheduleJob record);

	ScheduleJob selectByPrimaryKey(Long jobId);

	int updateByPrimaryKeySelective(ScheduleJob record);

	int updateByPrimaryKey(ScheduleJob record);

	List<ScheduleJob> getAll();
	
	List<ScheduleJob> checkUnique(ScheduleJob record);
}
