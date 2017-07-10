package com.park.paycenter.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.park.paycenter.response.OffOrderResponse;
import com.park.paycenter.response.UnifiedorderResponse;

/**
 * @author liuyang
 * 时间：2016年4月6日
 * 功能：微信支付的相关业务逻辑
 * 备注：
 */
@Repository(value = "WeiXinPayService")
public interface WeiXinPayService {
	
	/**
	 * 微信-统一下单
	 * @return
	 */
	public String weixinunifiedorder(UnifiedorderResponse unifiedorderResponse, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 微信-支付结果通用通知
	 * @param params
	 * @return
	 */
	public String notice_of_payment(String params);
	
	/**
	 * 微信-关闭订单
	 * @param offOrderResponse
	 * @return
	 */
	public String offOrder(OffOrderResponse offOrderResponse);
}
