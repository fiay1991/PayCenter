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
	public Integer creat_current_accounts(String platform, BigDecimal income, BigDecimal pay, String out_trade_no, String terminal_type, String park_id, String ordernum, String wx_prepayid, String wx_transaction_id, String stauts,String return_url) {
		// TODO Auto-generated method stub
		Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
		if (null != income) {
			pc_current_accounts.setIncome(income);
		}
		if (null != pay) {
			pc_current_accounts.setPay(pay);
		}
		pc_current_accounts.setOrdernum(ordernum);
		pc_current_accounts.setOut_trade_no(out_trade_no);
		pc_current_accounts.setPark_id(park_id);
		pc_current_accounts.setPlatform(platform);
		pc_current_accounts.setStauts(stauts);
		pc_current_accounts.setTerminal_type(terminal_type);
		pc_current_accounts.setWx_prepayid(wx_prepayid);
		pc_current_accounts.setWx_transaction_id(wx_transaction_id);
		pc_current_accounts.setReturn_url(return_url);

		return pc_current_accountsDao.add(pc_current_accounts);
	}

}
