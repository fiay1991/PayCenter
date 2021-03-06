package com.park.paycenter.config;

/**
 * @author liuyang
 * 时间：2016年4月7日
 * 功能：支付的状态
 * 备注：
 */

public class PayStatusConfig {
	
	/**
	 * 未支付
	 */
	public static final String INTRADING = "INTRADING";
	/**
	 * 支付失败
	 */
	public static final String NPAY = "FAIL";
	
	/**
	 * 支付成功
	 */
	public static final String YPAY = "SUCCESS";
	
	/**
	 * 交易关闭
	 */
	public static final String OFFPAY = "OFF";
}
