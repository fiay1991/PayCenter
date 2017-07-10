package com.park.paycenter.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author liuyang
 * 时间：2016年4月7日
 * 功能：支付中心-流水账对应数据库的实体
 * 备注：所有数据库对应实体的字段含义请对应数据库字典
 */

public class Pc_current_accounts {
	private int id;
	private String platform;
	private BigDecimal income;
	private BigDecimal pay;
	private String out_trade_no;
	private String terminal_type;
	private String park_id;
	private String ordernum;
	private String zfb_trade_no;
	private String wx_prepayid;
	private String wx_transaction_id;
	private Timestamp timestamp;
	private String stauts;
	private String return_url;//返回页面
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getPay() {
		return pay;
	}
	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTerminal_type() {
		return terminal_type;
	}
	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	public String getPark_id() {
		return park_id;
	}
	public void setPark_id(String park_id) {
		this.park_id = park_id;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getZfb_trade_no() {
		return zfb_trade_no;
	}
	public void setZfb_trade_no(String zfb_trade_no) {
		this.zfb_trade_no = zfb_trade_no;
	}
	public String getWx_prepayid() {
		return wx_prepayid;
	}
	public void setWx_prepayid(String wx_prepayid) {
		this.wx_prepayid = wx_prepayid;
	}
	public String getWx_transaction_id() {
		return wx_transaction_id;
	}
	public void setWx_transaction_id(String wx_transaction_id) {
		this.wx_transaction_id = wx_transaction_id;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getStauts() {
		return stauts;
	}
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
}
