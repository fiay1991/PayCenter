package com.park.paycenter.dao;

import java.awt.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.park.paycenter.domain.Pc_current_accounts;

/**
 * @author liuyang
 * 时间：2016年4月7日
 * 功能：支付中心-流水账
 * 备注：
 */
@Repository(value = "Pc_current_accountsDao")
public interface Pc_current_accountsDao {

	/**
	 * 插入
	 * @param pc_current_accounts
	 * @return
	 */
	Integer add(Pc_current_accounts pc_current_accounts);
	
	/**
	 * 更新
	 * @param pc_current_accounts
	 * @return
	 */
	Integer update(Pc_current_accounts pc_current_accounts);
	
	/**
	 * 查询单个
	 * @param pc_current_accounts
	 * @return
	 */
	Pc_current_accounts search(Pc_current_accounts pc_current_accounts);
	
	/**
	 * 查询列表
	 * @param pc_current_accounts
	 * @return
	 */
	List searchList(Pc_current_accounts pc_current_accounts);

	/**
	 * 通过ISV订单号查询openid和orderno
	 * @param out_trade_no
	 * @return
	 */
	Map<String, String> getOpenidAndOrdernoByOuttradeno(String out_trade_no);
}
