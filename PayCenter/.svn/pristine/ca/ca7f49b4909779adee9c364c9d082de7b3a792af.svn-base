package com.park.paycenter.domain;

import java.sql.Timestamp;
/**
 * @author wangfanfan 
 * 时间：2016年9月20日 
 * 功能： 支付中心-外部接口调用记录对应数据库的实体
 * 备注：所有数据库对应实体的字段含义请对应数据库字典
 */
public class External_log_records {

	private int id;
	private Timestamp timestamp; // 时间戳
	private String payment_platform;// 支付平台（如：微信）
	private String req_params; // 请求参数（拼接的字符串链接）
	private String req_interface; // 请求内容（如：下单接口）
	private String return_result; // 返回结果
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getPayment_platform() {
		return payment_platform;
	}
	public void setPayment_platform(String payment_platform) {
		this.payment_platform = payment_platform;
	}
	
	public String getReq_params() {
		return req_params;
	}
	public void setReq_params(String req_params) {
		this.req_params = req_params;
	}
	
	public String getReq_interface() {
		return req_interface;
	}
	public void setReq_interface(String req_interface) {
		this.req_interface = req_interface;
	}
	
	public String getReturn_result() {
		return return_result;
	}
	public void setReturn_result(String return_result) {
		this.return_result = return_result;
	}

}
