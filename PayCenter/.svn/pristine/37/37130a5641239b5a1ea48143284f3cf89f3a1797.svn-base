package com.park.paycenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.paycenter.domain.FailNotifyRecord;


/**
 * @author fangct
 * 时间：2016年3月25日 
 * 功能： 
 */
//@Component
@Repository(value = "FailNotifyRecordMapper")
public interface FailNotifyRecordMapper {
	/**
	 * 查询全部
	 * 
	 */
	List<FailNotifyRecord> findAll();
	/**
	 * 查询全部需轮询的通知任务
	 * @param status 状态, RED/YELLOW/GREEN
	 * @param next_notify_time 下次执行时间
	 * @return List 
	 */
	List<FailNotifyRecord> findByStatus(@Param("status")String status, 
			@Param("next_notify_time")String next_notify_time);
	/**
	 * 插入通知失败对象
	 * @param failNotifyRecord
	 * @return
	 */
	Integer insert(FailNotifyRecord failNotifyRecord);
	/**
	 * 更新通知失败对象
	 * @param failNotifyRecord
	 * @return
	 */
	Integer update(FailNotifyRecord failNotifyRecord);
	/**
	 * 查询未未发送的记录
	 * @return
	 */
	List<FailNotifyRecord> findNoSendMail();
}
