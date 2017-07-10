package com.park.paycenter.service;

import org.springframework.stereotype.Repository;

/**
 * @author fangct 
 * 时间：2017年6月16日 
 * 功能：通知失败记录
 */
@Repository(value = "FailNotifyRecordService")
public interface FailNotifyRecordService {

	/**
	 * 插入通知失败对象
	 * @param trade_type 交易类型
	 * @param out_trade_no 无忧支付订单号
	 * @param park_id 车场ID
	 * @param notify_url 主动通知地址
	 * @param  params 通知参数
	 * @param  wx_zfb_no 微信/支付宝订单号
	 * @param  cause_mark 原因描述
	 * @return
	 */
	Integer insert(String trade_type, String out_trade_no, String park_id, String notify_url, String params,
			String wx_zfb_no, String cause_mark);
}
