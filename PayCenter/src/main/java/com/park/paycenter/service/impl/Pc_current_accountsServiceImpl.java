package com.park.paycenter.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.park.paycenter.dao.Pc_current_accountsDao;
import com.park.paycenter.domain.Pc_current_accounts;
import com.park.paycenter.service.Pc_current_accountsService;

/**
 * @author liuyang
 *         时间：2016年4月7日
 *         功能：支付中心-流水账
 *         备注：
 */
@Transactional
@Repository(value = "Pc_current_accountsServiceImpl")
public class Pc_current_accountsServiceImpl implements Pc_current_accountsService {

	@Resource(name = "Pc_current_accountsDaoImpl")
	private Pc_current_accountsDao pc_current_accountsDao;

	@Override
	public Integer creat_current_accounts(String trade_type, BigDecimal income, String trade_no, String terminal_type, 
			String park_id, String out_trade_no, String wx_prepayid, String wx_transaction_id, String status,String return_url,String notify_url, String openid) {
		Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
		if (null != income) {
			pc_current_accounts.setIncome(income);
		}
		pc_current_accounts.setOutTradeNo(out_trade_no);
		pc_current_accounts.setTradeNo(trade_no);
		pc_current_accounts.setParkId(park_id);
		pc_current_accounts.setTradeType(trade_type);
		pc_current_accounts.setStatus(status);
		pc_current_accounts.setTerminalType(terminal_type);
		pc_current_accounts.setWxPrepayid(wx_prepayid);
		pc_current_accounts.setWxTransactionId(wx_transaction_id);
		pc_current_accounts.setReturnUrl(return_url);
		pc_current_accounts.setNotifyUrl(notify_url);
		pc_current_accounts.setOpenid(openid);

		return pc_current_accountsDao.add(pc_current_accounts);
	}

}
