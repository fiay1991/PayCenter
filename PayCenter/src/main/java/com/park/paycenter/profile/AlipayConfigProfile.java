package com.park.paycenter.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author fangct
 *         时间：2017年6月13日
 *         功能：支付宝的通用配置类
 *         备注：适用于支付宝手机网站支付V2.0
 */
@Component
@ConfigurationProperties(prefix = "AlipayConfigProfile")
public class AlipayConfigProfile {
	private String appid;//商户appid
	private String rsa_private_key;//私钥 pkcs8格式的
	private String notify_url;// 支付结果通用通知
	private String return_url;//返回地址
	private String url;//请求网关地址
	private String charset;// 编码
	private String format;//返回格式
	private String alipay_public_key;//支付宝公钥
	private String signtype;//签名验证方式
	
	private String h5_updateorder_url;//同步返回页面地址
	private String h5_notifyorder_url;//异步通知
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getRsa_private_key() {
		return rsa_private_key;
	}

	public void setRsa_private_key(String rsa_private_key) {
		this.rsa_private_key = rsa_private_key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAlipay_public_key() {
		return alipay_public_key;
	}

	public void setAlipay_public_key(String alipay_public_key) {
		this.alipay_public_key = alipay_public_key;
	}

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getH5_updateorder_url() {
		return h5_updateorder_url;
	}

	public void setH5_updateorder_url(String h5_updateorder_url) {
		this.h5_updateorder_url = h5_updateorder_url;
	}

	public String getH5_notifyorder_url() {
		return h5_notifyorder_url;
	}

	public void setH5_notifyorder_url(String h5_notifyorder_url) {
		this.h5_notifyorder_url = h5_notifyorder_url;
	}

}
