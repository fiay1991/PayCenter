package com.park.paycenter.log;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.park.base.common.DataChangeTools;
import com.park.base.common.XMLTool;
import com.park.paycenter.config.PaymentPlatformConfig;
import com.park.paycenter.service.Internal_log_recordsService;

/**
 * @author wangfanfan、fangct
 * 时间：created at 2016年9月20日 ，updated at 20170620
 * 功能：内部接口调用日志记录AOP实现 
 * 备注：
 */
@Aspect
@Component
public class Internal_log_recordsAspect {
	
	// 本地异常日志记录对象
	private static final Logger logger=LoggerFactory.getLogger(Internal_log_recordsAspect.class);
	
	// 注入Service用于把日志保存数据库
	@Resource(name = "Internal_log_recordsServiceImpl")
	private Internal_log_recordsService internal_log_recordsService;

	/**
	 * 支付相关业务逻辑方法切入点，包含：下单、查询和关单
	 */
	@Pointcut("execution(* com.park.paycenter.service.impl.PayServiceImpl.offorder(..)) || execution(* com.park.paycenter.service.impl.PayServiceImpl.queryorder(..)) || execution(* com.park.paycenter.service.impl.PayServiceImpl.unifiedorder(..))")
	public void inside_log() {}
	
	/**
	 * 支付完成后的通知，包含：异步通知和返回
	 */
	@Pointcut("execution(* com.park.paycenter.service.impl.PayServiceImpl.notice_of_payment(..)) || execution(* com.park.paycenter.service.impl.PayServiceImpl.return_of_payment(..))")
	public void without_log() {}
	
	@AfterReturning(value="inside_log()",returning="returnValue")
	public void insideLog(JoinPoint joinPoint,Object returnValue) throws Throwable {
		try {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			// 判断参数
			Object objectArgs = joinPoint.getArgs();
			if (objectArgs == null) {
				return;
			}
			// 请求接口
			String req_interface = joinPoint.getSignature().getName();
			// 请求URL
			String url = request.getRequestURL().toString();
			//获取请求方式
			String method = request.getMethod();
			String params = "";
			if ("POST".equals(method)) {
				params = (String) request.getAttribute("params");
				if(params != null) params = params.trim();
				logger.info("AOP日志记录功能，req_params:"+params);
				String req_params = url + "?params=" + params;
				// 将字符串转为JsonObject对象
				JsonObject jsonObject = new JsonParser().parse(params).getAsJsonObject();
				// 获取某个键的值
				String user_environment = jsonObject.get("trade_type").getAsString();
				String visitor = "";
				if ("" != visitor || null != visitor && "unifiedorder".equals(req_interface)) {
					visitor = jsonObject.get("terminal_type").getAsString();
				}
				internal_log_recordsService.creat_internal_log_records(visitor,user_environment, req_params,req_interface, returnValue.toString());

			}else if("GET".equals(method)){
				// params = request.getParameter("params");
				// params = request.getQueryString();只适用于GET,比如客户端发送http://xxxxx/test?a=b&c=d,通过request.getQueryString()得到的是a=b&c=d.
				System.out.println("GET " + request.getRequestURL() + "?"+ request.getQueryString()); 
				
				// 获取参数
				Map<String, String[]> map = request.getParameterMap();
				String queryString = "";
				String value = "";
				JsonObject jsonObject = null;
				for (String key : map.keySet()) {
					String[] values = map.get(key);
					for (int i = 0; i < values.length; i++) {
						value = values[i];
						queryString += key + "=" + value + "&";
					}
					if ("params".equals(key)) {
						jsonObject = new JsonParser().parse(value).getAsJsonObject();
					}
				}
				// queryString = queryString.substring(0, queryString.length() - 1);
				String req_params = url + "?" + queryString;

				// 将字符串转为JsonObject对象
				// JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
				// 获取某个键的值
				String user_environment = jsonObject.get("trade_type").getAsString();
				String visitor = "";
				if ("" != visitor || null != visitor && "unifiedorder".equals(req_interface)) {
					visitor = jsonObject.get("terminal_type").getAsString();
				}
				internal_log_recordsService.creat_internal_log_records(visitor,user_environment, req_params,req_interface, returnValue.toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//记录本地异常日志    
            logger.error("=======内部接口调用记录Log异常========"); 
            logger.info("AOP访问异常：{}",e);
            logger.error("异常信息:{}", e.getMessage()); 
            logger.error("异常方法:{}异常代码:{}异常信息:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
		}
	}
	
	@AfterReturning(value="without_log()",returning="returnValue")
	public void withoutLog(JoinPoint joinPoint,Object returnValue) throws Throwable {
		try {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			// 判断参数
			Object[] objectArgs = joinPoint.getArgs();
			if (objectArgs == null) {
				return;
			}
			// 请求接口
			String req_interface = joinPoint.getSignature().getName();
			// 请求URL
			String url = request.getRequestURL().toString();

			String trade_type = (String)objectArgs[0];
			String wxParams = (String)objectArgs[1];
			@SuppressWarnings("unchecked")
			Map<String, String> zfbParams = (Map<String, String>)objectArgs[2];
			
			String req_params = "";
			if(PaymentPlatformConfig.WEIXIN.equals(trade_type) && wxParams != null){
				Map<String, Object> map = XMLTool.xmlToMap(wxParams);
				// 按照ascii字典进行从大到小的排序
				Collection<String> keyset = map.keySet();
				List<String> list = new ArrayList<String>(keyset);
				java.util.Collections.sort(list);
				StringBuffer stringBuffer = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					String key = list.get(i);
					String value = map.get(key).toString();
					stringBuffer.append("&" + key + "=" + value);
				}
				req_params = url + "?" + stringBuffer.substring(stringBuffer.indexOf("&") + 1);
			}else if(PaymentPlatformConfig.ZHIFUBAO.equals(trade_type) && zfbParams != null){
				req_params = url + "?" + DataChangeTools.createLinkString(zfbParams);
			}
			internal_log_recordsService.creat_internal_log_records("",trade_type, req_params,req_interface, returnValue.toString());
		}catch(Exception e){
			//记录本地异常日志    
            logger.error("=======内部接口调用记录Log异常========"); 
            logger.info("AOP访问异常：{}",e);
            logger.error("异常信息:{}", e.getMessage()); 
            logger.error("异常方法:{}异常代码:{}异常信息:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
		}
	}
}