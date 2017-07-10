package com.park.paycenter.requset;

/**
 * @author liuyang
 *         时间：2016年4月11日
 *         功能：向微信服务器关闭订单的请求实体
 *         备注：
 * 
 *         字段名 变量名 必填 类型 示例值 描述
 *         公众账号ID appid 是 String(32) wx8888888888888888 微信分配的公众账号ID（企业号corpid即为此appId）
 *         商户号 mch_id 是 String(32) 1900000109 微信支付分配的商户号
 *         商户订单号 out_trade_no 是 String(32) 1217752501201407033233368018 商户系统内部的订单号
 *         随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见安全规范
 *         签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法
 */

public class WeiXinOffOrderRequest {
	private String appid;
	private String mch_id;
	private String out_trade_no;
	private String nonce_str;
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
