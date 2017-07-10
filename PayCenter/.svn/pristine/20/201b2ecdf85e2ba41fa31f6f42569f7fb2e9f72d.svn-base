package com.park.paycenter.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.park.paycenter.response.OffOrderResponse;
import com.park.paycenter.response.UnifiedorderResponse;

/**
 * @author liuyang
 * 时间：2016年5月9日
 * 功能：支付宝支付的相关业务逻辑
 * 备注：
 */
@Repository(value = "AlipayService")
public interface AlipayService {
	
	/**
	 * 支付宝的统一下单（使用支付宝的“开放平台平台” 支付宝规定业务类型不同所使用的平台也不同）
	 * @param unifiedorderResponse
	 * @param request
	 * @param response
	 * @return
	 */
	public String alipayunifiedorder(UnifiedorderResponse unifiedorderResponse, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 旧版本V1.0
	 * 支付宝的手机网页支付（使用支付宝的“合作伙伴平台” 支付宝规定业务类型不同所使用的平台也不同）
	 * @param unifiedorderResponse
	 * @param request
	 * @param response
	 * @return
	 */
	public String H5Pay(UnifiedorderResponse unifiedorderResponse, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 新版本V2.0
	 * 支付宝的手机网页支付（使用支付宝的“合作伙伴平台” 支付宝规定业务类型不同所使用的平台也不同）
	 * @param unifiedorderResponse
	 * @param request
	 * @param response
	 * @return
	 */
	public String new_H5Pay(UnifiedorderResponse unifiedorderResponse, HttpServletRequest request, HttpServletResponse response) ;

	/**
	 * 支付宝-支付结果通用通知
	 * @param params
	 * @return
	 */
	public String notice_of_payment(Map<String, String> params);
	
	/**
	 * 支付宝-关闭订单接口
	 * @param params
	 * @return
	 */
	public String offOrder(OffOrderResponse offOrderResponse);
	
	/**
	 * 支付宝-返回页面处理
	 * @param params
	 * @return
	 */
	public String return_of_payment(Map<String,String> params);
}
