package com.park.paycenter.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝-合作伙伴平台配置
 * @author liuyang
 *
 */
@Component
@ConfigurationProperties(prefix = "AlipayPartnerProfile")
public class AlipayPartnerProfile {
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	private String partner;

	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	private String private_key;

	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	private String alipay_public_key;

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	private String return_url;

	// 签名方式
	private String sign_type;

	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	private String log_path;

	// 字符编码格式 目前支持utf-8
	private String input_charset;

	// 支付类型 ，无需修改
	private String payment_type;

	// 调用的接口名，无需修改
	private String service;

	// 支付宝提供给商户的服务接入网关URL(新)
	private String alipay_gateway_new;
	
	// 支付宝表单提交方式
	private String submit_method;
	
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPrivate_key() {
		return private_key;
	}

	public void setPrivate_key(String private_key) {
		this.private_key = private_key;
	}

	public String getAlipay_public_key() {
		return alipay_public_key;
	}

	public void setAlipay_public_key(String alipay_public_key) {
		this.alipay_public_key = alipay_public_key;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getLog_path() {
		return log_path;
	}

	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAlipay_gateway_new() {
		return alipay_gateway_new;
	}

	public void setAlipay_gateway_new(String alipay_gateway_new) {
		this.alipay_gateway_new = alipay_gateway_new;
	}

	public String getSubmit_method() {
		return submit_method;
	}

	public void setSubmit_method(String submit_method) {
		this.submit_method = submit_method;
	}
	
}
