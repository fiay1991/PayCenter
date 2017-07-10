package com.park.paycenter.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;
import com.park.paycenter.service.PayService;

/**
 * @author liuyang
 *         时间：2016年4月6日
 *         功能：暴露给用户的支付业务
 *         备注：
 */
@Controller
@RequestMapping(value = "PayController")
public class PayController {
	// private final static Logger logger = LoggerFactory.getLogger(PayController.class);
	@Resource(name = "PayServiceImpl")
	private PayService payService;
	
	Logger logger =LoggerFactory.getLogger(getClass());

	/**
	 * 统一下单
	 * 
	 * @return
	 * @throws IOException 
	 * @throws WriterException
	 */
	@ResponseBody
	@RequestMapping(value = "/unifiedorder", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public String unifiedorder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String params = request.getParameter("params");// 对方的json形式参数都叫params
		logger.info("请求信息：{}",request);
		logger.info("统一下单参数：{}",params);
		if (null != params) {
			params = params.trim();
		}
		return payService.unifiedorder(params, request, response);
	}

	/**
	 * 查询订单
	 * 
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryorder", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public String queryorder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String params = request.getParameter("params");// 对方的json形式参数都叫params
		logger.info("查询订单请求参数：{}",params);
		if (null != params) {
			params = params.trim();
		}
		return payService.queryorder(params);
	}

	/**
	 * 关闭订单
	 * 
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/offorder", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public String offorder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String params = request.getParameter("params");// 对方的json形式参数都叫params
		logger.info("关闭订单请求参数：{}",params);
		if (null != params) {
			params = params.trim();
		}
		return payService.offorder(params);
	}
}
