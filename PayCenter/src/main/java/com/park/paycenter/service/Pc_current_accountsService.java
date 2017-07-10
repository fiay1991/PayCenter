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
	 * @param platform
	 * @param income
	 * @param pay
	 * @param out_trade_no
	 * @param terminal_type
	 * @param park_id
	 * @param ordernum
	 * @param wx_prepayid
	 * @param wx_transaction_id
	 * @param timestamp
	 * @param stauts
	 * @return
	 */
	Integer creat_current_accounts(String platform, BigDecimal income, BigDecimal pay, String out_trade_no, String terminal_type, String park_id, String ordernum, String wx_prepayid, String wx_transaction_id, String stauts,String return_url);
}
