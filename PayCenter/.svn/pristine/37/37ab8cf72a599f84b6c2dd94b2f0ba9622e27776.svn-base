package com.park.paycenter.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

/**
 * @author liuyang
 * 时间：2016年4月6日
 * 功能：支付的业务逻辑
 * 备注：
 */
@Repository(value = "PayService")
public interface PayService {
	
	/**
	 * 统一下单
	 * @return
	 */
	public String unifiedorder(String params,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 查询订单
	 * @param params
	 * @return
	 */
	public String queryorder(String params);
	
	/**
	 * 关闭订单
	 * @param params
	 * @return
	 */
	public String offorder(String params);
	
	/**
	 * 支付完成后通知
	 * @param trade_type 支付类型：WEIXIN、ZHIFUBAO
	 * @param WXparams 微信参数
	 * @param ZFBparams 支付宝参数
	 * @return
	 */
	public String notice_of_payment(String trade_type, String WXparams, Map<String, String> ZFBparams);
	
	/**
	 * 支付完成后返回
	* @param trade_type 支付类型：WEIXIN、ZHIFUBAO
	 * @param WXparams 微信参数
	 * @param ZFBparams 支付宝参数
	 * @return
	 */
	public String return_of_payment(String trade_type, String WXparams, Map<String, String> ZFBparams);
	
}
