package com.park.paycenter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.park.paycenter.alipay.trade.config.Configs;
import com.park.paycenter.config.PaymentPlatformConfig;
import com.park.paycenter.profile.AlipayConfigProfile;
import com.park.paycenter.profile.AlipayPartnerProfile;
import com.park.paycenter.service.PayService;

/**
 * @author liuyang
 * 时间：2016年5月13日
 */
@Controller
@RequestMapping(value = "AlipayController")
public class AlipayController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "PayServiceImpl")
	private PayService payService;
	
	@Resource(name = "alipayConfigProfile")
	AlipayConfigProfile alipayConfigProfile;
	
	@Resource(name = "alipayPartnerProfile")
	AlipayPartnerProfile alipayPartnerProfile;

	/**
	 * H5支付-支付宝结果通用通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/notice_of_payment", produces = "text/html;charset=UTF-8")
	public String notice_of_payment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("H5支付-通知进来了...");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = getParmasByRequest(request);
		
		//计算得出通知验证结果
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean verify_result = false;
		try {
			verify_result = AlipaySignature.rsaCheckV1(params, alipayConfigProfile.getAlipay_public_key(), alipayConfigProfile.getCharset(),alipayConfigProfile.getSigntype());
		} catch (AlipayApiException e) {
			logger.error("下单的订单号：{} 签名验证出现错误：{}",params.get("out_trade_no"),e);
		}
		/*** 签名验证结束...***/
		if(verify_result){//验证成功
			return payService.notice_of_payment(PaymentPlatformConfig.ZHIFUBAO, null, params);
		}else{
			logger.info("下单的订单号：{} 签名验证失败",params.get("out_trade_no"));
			return "fail";
		}
	}
	
	/**
	 * 自助缴费机付-支付宝结果通用通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/f2f_notice_of_payment", produces = "text/html;charset=UTF-8")
	public String f2f_notice_of_payment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("自助缴费机付-通知进来了...");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = getParmasByRequest(request);
		
		boolean verify_result = false;
		try {
			verify_result = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "utf-8", AlipayConstants.SIGN_TYPE_RSA);
		} catch (Exception e) {
			logger.error("下单的订单号：{} 签名验证出现错误：{}",params.get("out_trade_no"),e);
		}
		/*** 签名验证结束...***/
		if(verify_result){//验证成功
			return payService.notice_of_payment(PaymentPlatformConfig.ZHIFUBAO, null, params);
		}else{
			logger.info("下单的订单号：{} 签名验证失败",params.get("out_trade_no"));
			return "fail";
		}
	}
	
	/**
	 * H5支付-支付宝结果通用通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/return_of_payment", produces = "text/html;charset=UTF-8")
	public String return_of_payment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("支付宝手机网站-返回页面通知进来了...");
		//获取支付宝GET过来反馈信息
		Map<String,String> params = getParmasByRequest(request);
		
		//计算得出通知验证结果
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean verify_result = false;
		try {
			verify_result = AlipaySignature.rsaCheckV1(params, alipayConfigProfile.getAlipay_public_key(), alipayConfigProfile.getCharset(),alipayConfigProfile.getSigntype());
		} catch (AlipayApiException e) {
			logger.error("下单的订单号：{} 签名验证出现错误：{}",params.get("out_trade_no"),e);
		}
		
		if(verify_result){//验证成功
			logger.info("下单的订单号：{} 签名验证成功!", params.get("out_trade_no"));
			params.put("sign_result", "success");
		}else{
			logger.info("下单的订单号：{} 签名验证失败！", params.get("out_trade_no"));
			params.put("sign_result", "fail");
		}
		return payService.return_of_payment(PaymentPlatformConfig.ZHIFUBAO, null, params);
	}
	
	/**
	 * 获取支付宝GET/POST过来反馈参数
	 * @param request
	 * @return
	 */
	public Map<String,String> getParmasByRequest(HttpServletRequest request){
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			/*try {
				// 同步返回通知乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				
				//异步通知乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			params.put(name, valueStr);
		}
		logger.info("支付宝返回结果参数：{}",params.toString());
		return params;
	}
}
