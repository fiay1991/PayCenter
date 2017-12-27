package com.park.paycenter.response;

/**
 * @author liuyang
 * 时间：2016年4月6日
 * 功能：接收-移动端自助缴费机等对应的实体
 * 备注：
 */

public class UnifiedorderResponse {
	private String body;//商品或支付单简要描述（如：停车费，停车年卡等）
	private String trade_no;//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
	private String total_fee;//支付的金额以”分”为单位
	private String parkid;//停车的ID
	private String trade_type;//业务类型如：微信，支付宝
	private String terminal_type;//支付设备的标示“自助缴费机支付，小票扫码，手机”
	private String openid;// 用户标识此参数必传，用户在商户appid下的唯一标识。下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
	private String userid;// 支付宝的userid
	private String accesstoken; // 支付宝的accesstoken
	private String auth_code;// 刷卡支付时扫描用户手机码获取用户授权的auth_code
	private String create_ip;
	
	private String return_url;//页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问（支付宝的手机网页支付用到）
	private String notify_url;//异步通知回调地址（支付宝的手机网页支付用到）
	
	private String ali_user_id;//支付宝用户id
	private String ali_access_token;//支付宝accessToken
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getParkid() {
		return parkid;
	}
	public void setParkid(String parkid) {
		this.parkid = parkid;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTerminal_type() {
		return terminal_type;
	}
	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getCreate_ip() {
		return create_ip;
	}
	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getAli_user_id() {
		return ali_user_id;
	}
	public void setAli_user_id(String ali_user_id) {
		this.ali_user_id = ali_user_id;
	}
	public String getAli_access_token() {
		return ali_access_token;
	}
	public void setAli_access_token(String ali_access_token) {
		this.ali_access_token = ali_access_token;
	}
	
}
