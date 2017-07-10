package com.park.paycenter.domain;

/**
 * @author fangct
 * 时间：2017年6月16日
 * 功能：通知失败的实体
 */

public class FailNotifyRecord {
	private int id;
	private String trade_type ;
	private String out_trade_no ;
	private String park_id ;
	private String notify_url ;
	private String params ;
	private String wx_zfb_no ;
	private String cause_mark;
	private String create_time ;
	private String update_time ;
	private String status ;
	private int send_status ;
	private int notify_times;
	private String next_notify_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getPark_id() {
		return park_id;
	}
	public void setPark_id(String park_id) {
		this.park_id = park_id;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getWx_zfb_no() {
		return wx_zfb_no;
	}
	public void setWx_zfb_no(String wx_zfb_no) {
		this.wx_zfb_no = wx_zfb_no;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getCause_mark() {
		return cause_mark;
	}
	public void setCause_mark(String cause_mark) {
		this.cause_mark = cause_mark;
	}
	public int getNotify_times() {
		return notify_times;
	}
	public void setNotify_times(int notify_times) {
		this.notify_times = notify_times;
	}
	public String getNext_notify_time() {
		return next_notify_time;
	}
	public void setNext_notify_time(String next_notify_time) {
		this.next_notify_time = next_notify_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSend_status() {
		return send_status;
	}
	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}
	
}
