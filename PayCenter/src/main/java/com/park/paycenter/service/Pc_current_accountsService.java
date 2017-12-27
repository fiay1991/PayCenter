package com.park.paycenter.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

/**
 * @author liuyang
 * 时间：2016年4月7日
 * 功能：
 * 备注：
 */
@Repository(value = "Pc_current_accountsService")
public interface Pc_current_accountsService {
	
	/**
	 * 生成流水账
	 * @param trade_type
	 * @param income
	 * @param trade_no
	 * @param terminal_type
	 * @param park_id
	 * @param out_trade_no
	 * @param wx_prepayid
	 * @param wx_transaction_id
	 * @param status
	 * @param return_url
	 * @param notify_url
	 * @param openid
	 * @return
	 */
	Integer creat_current_accounts(String trade_type, BigDecimal income, String trade_no, String terminal_type, 
			String park_id, String out_trade_no, String wx_prepayid, String wx_transaction_id, String status,String return_url,String notify_url, String openid);
	
}
