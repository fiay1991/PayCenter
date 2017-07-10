package com.park.paycenter.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.park.paycenter.constants.NotifyFrequencyConstants;
import com.park.paycenter.constants.NotifyStatusConstants;
import com.park.paycenter.dao.FailNotifyRecordDao;
import com.park.paycenter.domain.FailNotifyRecord;
import com.park.paycenter.service.FailNotifyRecordService;
import com.park.paycenter.tools.DateTools;

/**
 * @author fangct
 * 时间：2017年6月16日
 * 功能：通知失败记录保存
 */
@Transactional
@Repository(value = "FailNotifyRecordServiceImpl")
public class FailNotifyRecordServiceImpl implements FailNotifyRecordService {

	@Resource(name = "FailNotifyRecordDaoImpl")
	private FailNotifyRecordDao failNotifyRecordDao;

	public Integer insert(String trade_type, String out_trade_no, String park_id, String notify_url, String params,
			String wx_zfb_no, String cause_mark) {
		FailNotifyRecord failNotifyRecord = new FailNotifyRecord();
		failNotifyRecord.setTrade_type(trade_type);
		failNotifyRecord.setOut_trade_no(out_trade_no);
		failNotifyRecord.setPark_id(park_id);
		failNotifyRecord.setNotify_url(notify_url);
		failNotifyRecord.setParams(params);
		failNotifyRecord.setWx_zfb_no(wx_zfb_no);
		failNotifyRecord.setCause_mark(cause_mark);
		//初始状态：未轮询
		failNotifyRecord.setStatus(NotifyStatusConstants.RED);
		//下次通知时间：当前时间+第一次与第二次通知的间接分钟数
		failNotifyRecord.setNext_notify_time(DateTools.addTime(new Date(), 
				NotifyFrequencyConstants.timesMap.get("1")));
		
		return failNotifyRecordDao.insert(failNotifyRecord);
	}

}
