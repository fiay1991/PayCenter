package com.park.paycenter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.paycenter.domain.FailNotifyRecord;

/**
 * @author fangct
 * 时间：2017年6月16日
 * 功能：异步通知失败对象的DAO
 * 备注：
 */
@Repository(value = "FailNotifyRecordDao")
public interface  FailNotifyRecordDao {
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
	List<FailNotifyRecord> findByStatus(String status, String next_notify_time);
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
	 * 查询全部待邮件提醒的订单
	 * @return List 
	 */
	List<FailNotifyRecord> findNoSendMail();
}
