package com.park.paycenter.controller;

import java.io.DataInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.paycenter.config.PaymentPlatformConfig;
import com.park.paycenter.service.PayService;

/**
 * @author liuyang
 *         时间：2016年4月7日
 *         功能：
 *         备注：
 */
@Controller
@RequestMapping(value = "WeiXinController")
public class WeiXinController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "PayServiceImpl")
	private PayService payService;

	/**
	 * 微信-支付结果通用通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/notice_of_payment", produces = "text/html;charset=UTF-8")
	public String notice_of_payment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("微信通知进来了...");
		DataInputStream in;
		in = new DataInputStream(request.getInputStream());
		byte[] dataOrigin = new byte[request.getContentLength()];
		in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中
		in.close(); // 关闭数据流
		String wxNotifyXml = new String(dataOrigin); // 从字节数组中得到表示实体的字符串
		logger.info("微信通知的参数：{}", wxNotifyXml);
		return payService.notice_of_payment(PaymentPlatformConfig.WEIXIN, wxNotifyXml, null);
	}
	
}
