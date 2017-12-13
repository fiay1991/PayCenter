package com.park.paycenter.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.park.paycenter.config.AlgorithmTypeConfig;
import com.park.paycenter.config.PayEquipmentConfig;
import com.park.paycenter.config.PayStatusConfig;
import com.park.paycenter.config.PaymentPlatformConfig;
import com.park.paycenter.config.ResultConfig;
import com.park.paycenter.dao.Pc_current_accountsDao;
import com.park.paycenter.domain.Pc_current_accounts;
import com.park.paycenter.enums.ErrorCode;
import com.park.paycenter.profile.WeiXinProfile;
import com.park.paycenter.requset.OffOrderRequest;
import com.park.paycenter.requset.UnifiedorderRequest;
import com.park.paycenter.requset.WeiXinH5APIRequest;
import com.park.paycenter.requset.WeiXinMicroRequest;
import com.park.paycenter.requset.WeiXinOffOrderRequest;
import com.park.paycenter.requset.WeiXinUnifiedorderRequest;
import com.park.paycenter.response.OffOrderResponse;
import com.park.paycenter.response.QueryOrderResponse;
import com.park.paycenter.response.UnifiedorderResponse;
import com.park.paycenter.response.WeiXinNoticeOfPaymentResponse;
import com.park.paycenter.service.External_log_recordsService;
import com.park.paycenter.service.FailNotifyRecordService;
import com.park.paycenter.service.Pc_current_accountsService;
import com.park.paycenter.service.WeiXinPayService;
import com.park.paycenter.tools.BackResultTools;
import com.park.paycenter.tools.DataChangeTools;
import com.park.paycenter.tools.HttpJsonTools;
import com.park.paycenter.tools.MD5encryptTool;
import com.park.paycenter.tools.MoneyTool;
import com.park.paycenter.tools.XMLTool;

/**
 * @author liuyang 时间：2016年4月6日 功能：微信支付-业务逻辑 备注：
 * update by fangct at 20160616 代码逻辑优化
 * @author WangYuefei 时间：2017/11/17 功能：1、刷卡支付 2、从微信查询订单
 */
@Repository(value = "WeiXinPayServiceImpl")
public class WeiXinPayServiceImpl implements WeiXinPayService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "Pc_current_accountsServiceImpl")
	private Pc_current_accountsService pc_current_accountsService;

	@Resource(name = "Pc_current_accountsDaoImpl")
	private Pc_current_accountsDao pc_current_accountsDao;

	@Resource(name = "External_log_recordsServiceImpl")
	private External_log_recordsService external_log_recordsService;
	
	@Autowired
	WeiXinProfile weiXinProfile;
	
	@Resource(name = "FailNotifyRecordServiceImpl")
	private FailNotifyRecordService failNotifyRecordService;

	/**
	 * 统一下单
	 */
	@Override
	public String weixinunifiedorder(UnifiedorderResponse unifiedorderResponse, HttpServletRequest request, HttpServletResponse response) {

		UnifiedorderRequest unifiedorderRequest = new UnifiedorderRequest();// 发送个用户端的实体

		// 必填项目
		WeiXinUnifiedorderRequest weiXin_unifiedorderRequest = new WeiXinUnifiedorderRequest();// 发送给微信服务器的实体
		weiXin_unifiedorderRequest.setAppid(weiXinProfile.getAppid());// 公众账号ID
		weiXin_unifiedorderRequest.setMch_id(weiXinProfile.getMch_id());// 商户号
		weiXin_unifiedorderRequest.setNonce_str(String.valueOf((int) ((Math.random() * 10 + 1) * 10000000)));// 随机字符串
		weiXin_unifiedorderRequest.setBody(unifiedorderResponse.getBody());// 商品或支付单简要描述（如：停车费，停车年卡等）
		String create_ip = unifiedorderResponse.getCreate_ip();
		if(null == create_ip || "".equals(create_ip)) {
			weiXin_unifiedorderRequest.setSpbill_create_ip(request.getRemoteAddr());// 请求方的IP地址
		}else {
			weiXin_unifiedorderRequest.setSpbill_create_ip(create_ip);// 请求方的IP地址
		}
		weiXin_unifiedorderRequest.setTotal_fee(unifiedorderResponse.getTotal_fee());// 订单总金额，单位为分
		if(!unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.CardPayMent)) {
			weiXin_unifiedorderRequest.setNotify_url(weiXinProfile.getNotify_url());// 通知地址
		}
		weiXin_unifiedorderRequest.setOut_trade_no(unifiedorderResponse.getParkid() + "_" + unifiedorderResponse.getOut_trade_no());// 订单号
		// 根据不同业务形式进行动态赋值的必填选项
		if (unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.AutomaticPayMent)) {
			logger.info("微信自助缴费机支付...");
			weiXin_unifiedorderRequest.setBody("自助缴费机扫码-停车费");
			weiXin_unifiedorderRequest.setTrade_type(weiXinProfile.getTrade_type_native());
		} else if (unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.H5PayMent)) {
			logger.info("微信H5扫码支付...");
			weiXin_unifiedorderRequest.setBody("H5扫码-停车费");
			weiXin_unifiedorderRequest.setTrade_type(weiXinProfile.getTrade_type_jsapi());
			weiXin_unifiedorderRequest.setOpenid(unifiedorderResponse.getOpenid());
		} else if (unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.CardPayMent)) {
			logger.info("微信刷卡支付...");
			weiXin_unifiedorderRequest.setBody("扫码枪扫码-停车费");
			weiXin_unifiedorderRequest.setAuth_code(unifiedorderResponse.getAuth_code());
		}
		String info = ErrorCode.成功.getContent();
		int code = ErrorCode.成功.getCode();
		try {

			/******* 生成链接字符串并进行MD5加密 ********/
			String stringSignTemp = piece_together_WeiXin_unifiedorder(weiXin_unifiedorderRequest);
			weiXin_unifiedorderRequest.setSign(MD5encryptTool.getMD5(stringSignTemp));
			logger.info("** 微信支付 - 预备参数：" + DataChangeTools.bean2gson(weiXin_unifiedorderRequest));
			/************* 生成xml ************/
			String sendXML = creatWeiXin_unifiedorderXML(weiXin_unifiedorderRequest);
			logger.info("生成请求微信的XML参数成功");
			
			/******* 向微信服务器发送 ********/
			String weixinOrderUrl = "";
			if(unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.CardPayMent)) {
				weixinOrderUrl = weiXinProfile.getCardpay_url();
			}else {
				weixinOrderUrl = weiXinProfile.getUnifiedorder_url();
			}
			logger.info("** 微信支付 - URL：" + weixinOrderUrl);
			logger.info("** 微信支付 - XML参数：" + sendXML);
			String weixinresponse = sendWeiXinService(weixinOrderUrl, new String(sendXML.toString().getBytes(), "ISO8859-1"));
			logger.info("向微信发送下单请求成功");
			logger.info("微信下单返回结果：{}",weixinresponse);
			
			external_log_recordsService.creat_external_log_records(PaymentPlatformConfig.WEIXIN, weiXinProfile.getUnifiedorder_url(), stringSignTemp, weixinresponse);
			logger.info("记录下单请求的记录成功");
			
			/******* 向APP返回数据 ********/
			if (null != weixinresponse) {
				/******* 校验返回的数据是否合法 ********/
				Map<String, Object> map = XMLTool.xmlToMap(weixinresponse);
				logger.info("将微信返回的xml格式字符串转换为map成功");
				
				/*和微信的通信失败*/
				if (map.get("return_code").toString().equals("FAIL")) {
					logger.info("微信返回通信失败，提示信息为：{}",map.get("return_msg"));
					info = map.get("return_msg").toString();
					code = ErrorCode.服务器参数错误.getCode();
					return BackResultTools.response(code, info, unifiedorderResponse, "");
				} 
				/*校验签名通过*/
				if (checkMD5(weixinresponse)) {
					/*下单的业务逻辑处理失败*/
					if (map.get("result_code").toString().equals("FAIL")) {
						String wxErrCode = map.get("err_code").toString();
						info = map.get("err_code_des").toString();
						logger.info("微信下单返回状态为FAIL : 状态码："+ wxErrCode +"，返回信息："+ info);
						if("USERPAYING".equals(wxErrCode) || "SYSTEMERROR".equals(wxErrCode) || "BANKERROR".equals(wxErrCode)) {
							logger.info(" 微信支付 - 支付中 - 生成流水账。。。");
							// 生成流水账
							Integer id = pc_current_accountsService.creat_current_accounts(PaymentPlatformConfig.WEIXIN, new BigDecimal(MoneyTool.fromFenToYuan(unifiedorderResponse.getTotal_fee())), null, unifiedorderResponse.getOut_trade_no(), unifiedorderResponse.getTerminal_type(), unifiedorderResponse.getParkid(), unifiedorderResponse.getParkid() + "_" + unifiedorderResponse.getOut_trade_no(), null, null, PayStatusConfig.INTRADING,null);
							if (null == id) {
								logger.info(" 微信支付 - 支付中 - 生成流水账失败！");
								return BackResultTools.response(ErrorCode.订单创建失败.getCode(), ErrorCode.订单创建失败.getContent(), unifiedorderResponse, "");
							}
							code = ErrorCode.刷卡支付中.getCode();
							return BackResultTools.response(ErrorCode.刷卡支付中.getCode(), info, unifiedorderResponse, "");
						}
						code = ErrorCode.服务器参数错误.getCode();
						return BackResultTools.response(code, info, unifiedorderResponse, "");
					/*下单的业务逻辑处理成功*/
					} else {
						// 生成流水账
						Integer id = 0;
						if(unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.CardPayMent)) {
							logger.info(" 微信支付 - 刷卡支付 - 生成流水账。。。");
							id = pc_current_accountsService.creat_current_accounts(PaymentPlatformConfig.WEIXIN, new BigDecimal(MoneyTool.fromFenToYuan(unifiedorderResponse.getTotal_fee())), null, unifiedorderResponse.getOut_trade_no(), unifiedorderResponse.getTerminal_type(), unifiedorderResponse.getParkid(), unifiedorderResponse.getParkid() + "_" + unifiedorderResponse.getOut_trade_no(), null, null, PayStatusConfig.YPAY,null);
						}else {
							logger.info(" 微信支付 - 统一下单 - 生成流水账。。。");
							id = pc_current_accountsService.creat_current_accounts(PaymentPlatformConfig.WEIXIN, new BigDecimal(MoneyTool.fromFenToYuan(unifiedorderResponse.getTotal_fee())), null, unifiedorderResponse.getOut_trade_no(), unifiedorderResponse.getTerminal_type(), unifiedorderResponse.getParkid(), unifiedorderResponse.getParkid() + "_" + unifiedorderResponse.getOut_trade_no(), map.get("prepay_id").toString(), null, PayStatusConfig.INTRADING,null);
						}
						if (null == id) {
							logger.info(" 微信支付 - 生成流水账失败！");
							return BackResultTools.response(ErrorCode.订单创建失败.getCode(), ErrorCode.订单创建失败.getContent(), unifiedorderResponse, "");
						}
						// 根据不同业务形式返回相应的json数据
						if (unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.AutomaticPayMent)) {// 自助缴费机(维系扫一扫直接付款)
							unifiedorderRequest.setPrepayid(map.get("prepay_id").toString());
							unifiedorderRequest.setCode_url(map.get("code_url").toString());
							logger.info("返回自助缴费机支付的结果对象封装完成");
							return BackResultTools.response(code, info, unifiedorderRequest, "");
						} else if (unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.H5PayMent)) {//// H5页面支付(微信内部的H5网页支付)
							WeiXinH5APIRequest weiXinH5APIRequest = new WeiXinH5APIRequest();
							weiXinH5APIRequest.setAppId(weiXinProfile.getAppid());
							weiXinH5APIRequest.setNonceStr(String.valueOf((int) ((Math.random() * 10 + 1) * 10000000)));
							weiXinH5APIRequest.setPrepay_id(map.get("prepay_id").toString());
							weiXinH5APIRequest.setSignType(AlgorithmTypeConfig.MD5);
							weiXinH5APIRequest.setTimeStamp(Long.toString(System.currentTimeMillis()));
							/******* 生成链接字符串并进行MD5加密 ********/
							String stringTemp = piece_together_WeiXin_H5API(weiXinH5APIRequest);
							weiXinH5APIRequest.setPaySign(MD5encryptTool.getMD5(stringTemp));
							logger.info("返回H5页面的结果对象封装完成");
							
							System.out.println("拼接的字符串：" + stringTemp);
							System.out.println("MD5加密的值：" + weiXinH5APIRequest.getPaySign());
	
							return BackResultTools.response(code, info, weiXinH5APIRequest, "");
						} else if(unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.CardPayMent)) {
							WeiXinMicroRequest wxMicroRequest = new WeiXinMicroRequest();
							wxMicroRequest.setAppid(weiXinProfile.getAppid());
							wxMicroRequest.setOpenid(map.get("openid").toString());
							wxMicroRequest.setOut_trade_no(map.get("out_trade_no").toString());
							wxMicroRequest.setTime_end(map.get("time_end").toString());
							wxMicroRequest.setTotal_fee(map.get("total_fee").toString());
							wxMicroRequest.setTrade_type(map.get("trade_type").toString());
							wxMicroRequest.setBank_type(map.get("bank_type").toString());
							wxMicroRequest.setTransaction_id(map.get("transaction_id").toString());
							logger.info("微信刷卡支付成功，返回数据：" + wxMicroRequest.toString());
							return BackResultTools.response(code, info, wxMicroRequest, "");
						}
					}
				/*校验签名失败*/
				} else {
					info = ErrorCode.微信发送的数据校验不通过.getContent();
					code = ErrorCode.微信发送的数据校验不通过.getCode();
					return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), info, unifiedorderResponse, "");
				}
			}else{
				logger.info("微信返回对象为空!");
				return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), "微信返回对象为空" ,unifiedorderRequest, "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent() + e.getMessage(), unifiedorderRequest, "");
		}
		return BackResultTools.response(code, info, unifiedorderRequest, "");
	}

	/**
	 * 微信-支付结果通用通知
	 */
	@Override
	public String notice_of_payment(String params) {
		WeiXinNoticeOfPaymentResponse returnResponse = new WeiXinNoticeOfPaymentResponse();
		try {
			if (null != params) {
				// 如果校验通过开始进行业务处理
				Map<String, Object> map = XMLTool.xmlToMap(params);
				logger.info("将XML格式的结果转换成Map成功！");
			
				/*和微信的通信失败*/
				if (map.get("return_code").toString().equals("FAIL")) {
					logger.info("微信返回通信失败，提示信息为：{}",map.get("return_msg"));
					returnResponse.setReturn_code("FAIL");
					returnResponse.setReturn_msg((String)map.get("return_msg"));
				/* 通信成功，验证签名*/
				} else if (checkMD5(params)) {
					// 如果业务处理成功
					if (map.get("result_code").equals("SUCCESS")) {
						logger.info("微信通知: 支付成功！");
						// 改变订单的状况
						Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
						pc_current_accounts.setOrdernum((String)map.get("out_trade_no"));
						pc_current_accounts = pc_current_accountsDao.search(pc_current_accounts);
						
						/*更新微信订单号*/
						if(pc_current_accounts != null)
							//微信支付订单号
							pc_current_accounts.setWx_transaction_id((String)map.get("transaction_id"));
						
						/*未找到订单*/
						if (null == pc_current_accounts) {
							logger.info("下单的订单号：{},没有找到此订单的相关信息！",map.get("out_trade_no"));
							returnResponse.setReturn_code("FAIL");
							returnResponse.setReturn_msg("没有找到此订单的相关信息");
						/*订单状态为已支付时，重复通知了*/
						} else if(pc_current_accounts.getStatus().equals(PayStatusConfig.YPAY)){
							logger.info("订单号：{},微信重复通知！", pc_current_accounts.getOut_trade_no());
							returnResponse.setReturn_code("SUCCESS");
							returnResponse.setReturn_msg("OK");
						/*订单状态为不为已支付的情况*/
						}else{
							logger.info("订单号：{},订单的支付状态为：{}", pc_current_accounts.getOut_trade_no(), pc_current_accounts.getStatus());
							pc_current_accounts.setStatus(PayStatusConfig.YPAY);
							pc_current_accountsDao.update(pc_current_accounts);
							logger.info("订单号：{},支付成功，修改支付状态为“已支付”成功！",pc_current_accounts.getOut_trade_no());
							returnResponse.setReturn_code("SUCCESS");
							returnResponse.setReturn_msg("OK");
							
							//支付渠道不是自助缴费机
							if(!PayEquipmentConfig.AutomaticPayMent.equals(pc_current_accounts.getTerminal_type())){
								//向H5通知支付的结果
								notifyH5(pc_current_accounts);
							}
						}
						
					}else {
						logger.info("下单的订单号：{}，微信返回状态为FAIL : 返回信息：{}", map.get("out_trade_no"),map.get("return_msg"));
						returnResponse.setReturn_code("FAIL");
						returnResponse.setReturn_msg((String)map.get("return_msg"));
					}
				}else{
					logger.info("下单的订单号：{}，微信通知签名错误，参数为：{}",map.get("out_trade_no"),params);
					returnResponse.setReturn_code("FAIL");
					returnResponse.setReturn_msg("数据校验不通过");
				}
			}else{
				returnResponse.setReturn_code("FAIL");
				returnResponse.setReturn_msg("参数为空");
			}
			return creatWeiXin_AppPay_NoticeOfPaymentResponseXML(returnResponse);
		} catch (Exception e) {
			logger.error("通知时出现异常，原因：{}",e);
			returnResponse.setReturn_code("FAIL");
			returnResponse.setReturn_msg("程序异常");
			return creatWeiXin_AppPay_NoticeOfPaymentResponseXML(returnResponse);
		}
	}

	/**
	 * 关闭订单
	 */
	@Override
	public String offOrder(OffOrderResponse offOrderResponse) {
		WeiXinOffOrderRequest weiXinOffOrderRequest = new WeiXinOffOrderRequest();// 发送给微信服务器的实体
		OffOrderRequest offOrderRequest = new OffOrderRequest();// 关闭订单给用户展示的实体
		// 必填项
		weiXinOffOrderRequest.setAppid(weiXinProfile.getAppid());
		weiXinOffOrderRequest.setMch_id(weiXinProfile.getMch_id());
		weiXinOffOrderRequest.setNonce_str(String.valueOf((int) ((Math.random() * 10 + 1) * 10000000)));// 随机字符串
		weiXinOffOrderRequest.setOut_trade_no(offOrderResponse.getParkid() + "_" + offOrderResponse.getOut_trade_no());
		/******* 生成链接字符串并进行MD5加密 ********/
		String info = ErrorCode.成功.getContent();
		int code = ErrorCode.成功.getCode();
		offOrderRequest.setPay_result(ResultConfig.SUCCESS);
		try {
			String stringSignTemp = piece_together_WeiXin_offorder(weiXinOffOrderRequest);
			weiXinOffOrderRequest.setSign(MD5encryptTool.getMD5(stringSignTemp));
			
			/************* 生成xml ************/
			String sendXML = creatWeiXin_offorderXML(weiXinOffOrderRequest);
			logger.info("订单号：{}，生成请求微信的XML参数成功！",offOrderResponse.getOut_trade_no());
			
			/******* 向微信服务器发送 ********/
			String weixinresponse = sendWeiXinService(weiXinProfile.getOfforder_url(), new String(sendXML.toString().getBytes(), "ISO8859-1"));
			logger.info("订单号：{}，向微信发送关闭订单请求成功！",offOrderResponse.getOut_trade_no());
			
			external_log_recordsService.creat_external_log_records(PaymentPlatformConfig.WEIXIN, weiXinProfile.getOfforder_url(), stringSignTemp, weixinresponse);
			logger.info("订单号：{}，记录关闭订单请求的记录成功！",offOrderResponse.getOut_trade_no());
			
			if (null != weixinresponse) {
				/******* 校验返回的数据是否合法 ********/
				Map<String, Object> map = XMLTool.xmlToMap(weixinresponse);
				/*服务器参数错误*/
				if (map.get("return_code").toString().equals("FAIL")) {
					info = map.get("return_msg").toString();
					code = ErrorCode.服务器参数错误.getCode();
					logger.info("订单号：{}，微信返回通信息失败，原因：{}",offOrderResponse.getOut_trade_no(),info);
					offOrderRequest.setPay_result(ResultConfig.FAIL);
					return BackResultTools.response(code, info, offOrderRequest, "");
				}
				/*校验签名*/
				if(checkMD5(weixinresponse)){
					/*业务结果失败*/
					if (map.get("result_code").toString().equals("FAIL")) {
						/*已经支付成功的情况*/
						if(map.get("err_code").toString().equals("ORDERPAID")){
							// 改变订单的状态
							Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
							pc_current_accounts.setStatus(PayStatusConfig.YPAY);
							pc_current_accounts.setOrdernum(offOrderResponse.getParkid() + "_" + offOrderResponse.getOut_trade_no());
							pc_current_accountsDao.update(pc_current_accounts);
							logger.info("订单：{}，已支付成功，不能关闭！",offOrderResponse.getOut_trade_no());
							info = (String)map.get("result_msg");
						/*已经关闭的情况*/
						} else if (map.get("err_code").toString().equals("ORDERCLOSED")) {
							// 改变订单的状态
							Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
							pc_current_accounts.setStatus(PayStatusConfig.OFFPAY);
							pc_current_accounts.setOrdernum(offOrderResponse.getParkid() + "_" + offOrderResponse.getOut_trade_no());
							pc_current_accountsDao.update(pc_current_accounts);
							logger.info("订单：{}，已经关闭，不能重复关闭！",offOrderResponse.getOut_trade_no());
							info = (String)map.get("result_msg");
						/*其他失败的情况*/
						} else {
							offOrderRequest.setPay_result(ResultConfig.FAIL);
							info = map.get("result_msg").toString();
							code = ErrorCode.服务器参数错误.getCode();
							logger.info("订单号：{}，支付通知失败，原因：{}",offOrderResponse.getOut_trade_no(),info);
						}
					/*业务结果成功*/
					} else {
						// 改变订单的状态
						Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
						pc_current_accounts.setStatus(PayStatusConfig.OFFPAY);
						pc_current_accounts.setOrdernum(offOrderResponse.getParkid() + "_" + offOrderResponse.getOut_trade_no());
						pc_current_accountsDao.update(pc_current_accounts);
						logger.info("订单号：{}，取消订单成功！",offOrderResponse.getOut_trade_no());
					}
				/*签名未通过*/
				} else {
					info = ErrorCode.微信发送的数据校验不通过.getContent();
					code = ErrorCode.微信发送的数据校验不通过.getCode();
					offOrderRequest.setPay_result(ResultConfig.FAIL);
				}
			}else{
				info = "微信返回为空";
				code = ErrorCode.服务器参数错误.getCode();
				offOrderRequest.setPay_result(ResultConfig.FAIL);
			}
			return BackResultTools.response(code, info, offOrderRequest, "");
		} catch (Exception e) {
			logger.error("订单号：{}，关闭订单时出现异常，原因：{}",offOrderResponse.getOut_trade_no(),e);
			info = "关闭订单时出现异常";
			code = ErrorCode.服务器参数错误.getCode();
			return BackResultTools.response(code, info + e.getMessage(), offOrderRequest, "");
		}
	}

	/**
	 * 向微信服务器发送数据
	 * 
	 * @param URL
	 * @param params
	 * @return
	 */
	public String sendWeiXinService(String URL, String params) {
		try {
			// 创建连接
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(params);
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// 断开连接
			connection.disconnect();

			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 校验微信服务器发过来的MD5值
	 * 
	 * @param xml
	 * @return
	 */
	public boolean checkMD5(String xml) {
		if (null == xml) {
			return false;
		}
		try {
			Map<String, Object> map = XMLTool.xmlToMap(xml);
			// 按照ascii字典进行从大到小的排序
			Collection<String> keyset = map.keySet();
			List<String> list = new ArrayList<String>(keyset);
			java.util.Collections.sort(list);
			String sign = null;
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				String key = list.get(i);
				String value = map.get(key).toString();
				if (key.equals("sign")) {
					sign = value;
				} else {
					stringBuffer.append("&" + key + "=" + value);
				}
			}
			// 截取字符串并进行MD5的校验
			String md5 = null;
			try {
				String string = stringBuffer.substring(stringBuffer.indexOf("&") + 1) + "&key=" + weiXinProfile.getAPIKey();
				md5 = MD5encryptTool.getMD5(string);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (md5.equals(sign)) {
				return true;
			}

		} catch (Exception e) {
			logger.error("校验微信服务器发过来的MD5值时，出现异常：{}！",e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("校验微信服务器发过来的MD5值不通过！");
		return false;
	}

	/**
	 * 拼接(统一下单)字符串链接
	 * 
	 * @param weiXin_unifiedorderRequest
	 * @return
	 */
	public String piece_together_WeiXin_unifiedorder(WeiXinUnifiedorderRequest weiXin_unifiedorderRequest) {

		String character = "";
		character += "appid=" + weiXin_unifiedorderRequest.getAppid();
		if(null != weiXin_unifiedorderRequest.getAuth_code()) {
			character += "&auth_code=" + weiXin_unifiedorderRequest.getAuth_code();
		}
		character += "&body=" + weiXin_unifiedorderRequest.getBody();
		character += "&mch_id=" + weiXin_unifiedorderRequest.getMch_id();
		character += "&nonce_str=" + weiXin_unifiedorderRequest.getNonce_str();
		if(null != weiXin_unifiedorderRequest.getNotify_url()) {
			character += "&notify_url=" + weiXin_unifiedorderRequest.getNotify_url();
		}
		if (null != weiXin_unifiedorderRequest.getOpenid()) {
			character += "&openid=" + weiXin_unifiedorderRequest.getOpenid();
		}
		character += "&out_trade_no=" + weiXin_unifiedorderRequest.getOut_trade_no();
		character += "&spbill_create_ip=" + weiXin_unifiedorderRequest.getSpbill_create_ip();
		character += "&total_fee=" + weiXin_unifiedorderRequest.getTotal_fee();
		if(null != weiXin_unifiedorderRequest.getTrade_type()) {
			character += "&trade_type=" + weiXin_unifiedorderRequest.getTrade_type();
		}

		// 选填
		if (null != weiXin_unifiedorderRequest.getDevice_info()) {
			character += "&device_info=" + weiXin_unifiedorderRequest.getDevice_info();
		}
		if (null != weiXin_unifiedorderRequest.getDetail()) {
			character += "&detail=" + weiXin_unifiedorderRequest.getDetail();
		}
		if (null != weiXin_unifiedorderRequest.getAttach()) {
			character += "&attach=" + weiXin_unifiedorderRequest.getAttach();
		}
		if (null != weiXin_unifiedorderRequest.getFee_type()) {
			character += "&fee_type=" + weiXin_unifiedorderRequest.getFee_type();
		}
		if (null != weiXin_unifiedorderRequest.getTime_start()) {
			character += "&time_start=" + weiXin_unifiedorderRequest.getTime_start();
		}
		if (null != weiXin_unifiedorderRequest.getTime_expire()) {
			character += "&time_expire=" + weiXin_unifiedorderRequest.getTime_expire();
		}
		if (null != weiXin_unifiedorderRequest.getGoods_tag()) {
			character += "&goods_tag=" + weiXin_unifiedorderRequest.getGoods_tag();
		}
		if (null != weiXin_unifiedorderRequest.getProduct_id()) {
			character += "&product_id=" + weiXin_unifiedorderRequest.getProduct_id();
		}
		if (null != weiXin_unifiedorderRequest.getLimit_pay()) {
			character += "&limit_pay=" + weiXin_unifiedorderRequest.getLimit_pay();
		}

		character += "&key=" + weiXinProfile.getAPIKey();
		return character;
	}
	
	/**
	 * 拼接(查询订单)字符串链接
	 * 
	 * @param weiXin_unifiedorderRequest
	 * @return
	 */
	public String piece_together_WeiXin_queryorder(WeiXinUnifiedorderRequest weiXin_unifiedorderRequest) {

		String character = "";
		character += "appid=" + weiXin_unifiedorderRequest.getAppid();
		character += "&mch_id=" + weiXin_unifiedorderRequest.getMch_id();
		character += "&nonce_str=" + weiXin_unifiedorderRequest.getNonce_str();
		character += "&out_trade_no=" + weiXin_unifiedorderRequest.getOut_trade_no();

		character += "&key=" + weiXinProfile.getAPIKey();
		return character;
	}

	/**
	 * 拼接(关闭订单)字符串链接
	 * 
	 * @param weiXinOffOrderRequest
	 * @return
	 */
	public String piece_together_WeiXin_offorder(WeiXinOffOrderRequest weiXinOffOrderRequest) {
		String character = "";
		character += "appid=" + weiXinOffOrderRequest.getAppid();
		character += "&mch_id=" + weiXinOffOrderRequest.getMch_id();
		character += "&nonce_str=" + weiXinOffOrderRequest.getNonce_str();
		character += "&out_trade_no=" + weiXinOffOrderRequest.getOut_trade_no();
		character += "&key=" + weiXinProfile.getAPIKey();
		return character;
	}

	/**
	 * 拼接(H5)字符串链接
	 * 
	 * @param weiXinOffOrderRequest
	 * @return
	 */
	public String piece_together_WeiXin_H5API(WeiXinH5APIRequest weiXinH5APIRequest) {
		String character = "";
		character += "appId=" + weiXinH5APIRequest.getAppId();
		character += "&nonceStr=" + weiXinH5APIRequest.getNonceStr();
		character += "&package=prepay_id=" + weiXinH5APIRequest.getPrepay_id();
		character += "&signType=" + weiXinH5APIRequest.getSignType();
		character += "&timeStamp=" + weiXinH5APIRequest.getTimeStamp();
		character += "&key=" + weiXinProfile.getAPIKey();
		return character;
	}

	/**
	 * 生成WeiXin_AppPay_NoticeOfPaymentResponse(向支付结果通知接口返回结果)的XML
	 * 
	 * @param weiXin_NoticeOfPaymentResponse
	 * @return
	 */
	public String creatWeiXin_AppPay_NoticeOfPaymentResponseXML(WeiXinNoticeOfPaymentResponse weiXin_NoticeOfPaymentResponse) {
		/*Document document = DocumentHelper.createDocument();
		Element xml = document.addElement("xml");

		// 必填
		Element return_code = xml.addElement("return_code");// 公众账号ID
		return_code.setText(weiXin_NoticeOfPaymentResponse.getReturn_code());

		// 必填
		Element OK = xml.addElement("OK");// 公众账号ID
		OK.setText(weiXin_NoticeOfPaymentResponse.getReturn_msg());

		logger.info("微信支付回调通知的返回结果：{}",document.asXML());
		return document.asXML();*/
		
		StringBuffer xmlStringBuffer = new StringBuffer();
		xmlStringBuffer.append("<xml>");
		xmlStringBuffer.append("<return_code>");
		xmlStringBuffer.append("<![CDATA["+weiXin_NoticeOfPaymentResponse.getReturn_code()+"]]>");
		xmlStringBuffer.append("</return_code>");
		xmlStringBuffer.append("<return_msg>");
		xmlStringBuffer.append("<![CDATA["+weiXin_NoticeOfPaymentResponse.getReturn_msg()+"]]>");
		xmlStringBuffer.append("</return_msg>");
		xmlStringBuffer.append("</xml>");

		logger.info("微信支付回调通知的返回结果：{}", xmlStringBuffer.toString());
		return xmlStringBuffer.toString();
	}

	/**
	 * 生成WeiXin_AppPay_unifiedorder(统一下单)的XML
	 * 
	 * @param weiXin_AppPay_unifiedorder
	 */
	public String creatWeiXin_unifiedorderXML(WeiXinUnifiedorderRequest weiXin_AppPay_unifiedorder) {
		Document document = DocumentHelper.createDocument();
		Element xml = document.addElement("xml");
		// 必填
		Element appid = xml.addElement("appid");// 公众账号ID
		appid.setText(weiXin_AppPay_unifiedorder.getAppid());

		Element body = xml.addElement("body");// 商品或支付单简要描述
		body.setText(weiXin_AppPay_unifiedorder.getBody());

		Element mch_id = xml.addElement("mch_id");// 微信支付分配的商户号
		mch_id.setText(weiXin_AppPay_unifiedorder.getMch_id());

		Element nonce_str = xml.addElement("nonce_str");// 随机字符串，不长于32位。推荐随机数生成算法
		nonce_str.setText(weiXin_AppPay_unifiedorder.getNonce_str());

		Element out_trade_no = xml.addElement("out_trade_no");// 商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
		out_trade_no.setText(weiXin_AppPay_unifiedorder.getOut_trade_no());

		Element spbill_create_ip = xml.addElement("spbill_create_ip");
		spbill_create_ip.setText(weiXin_AppPay_unifiedorder.getSpbill_create_ip());// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。

		Element total_fee = xml.addElement("total_fee");// 订单总金额，只能为整数，详见支付金额
		total_fee.setText(weiXin_AppPay_unifiedorder.getTotal_fee());

		Element sign = xml.addElement("sign");// 签名，详见签名生成算法
		sign.setText(weiXin_AppPay_unifiedorder.getSign());

		// 选填
		if(null != weiXin_AppPay_unifiedorder.getTrade_type()) {
			Element trade_type = xml.addElement("trade_type");// 取值如下：JSAPI，NATIVE，APP，WAP,详细说明见参数规定
			trade_type.setText(weiXin_AppPay_unifiedorder.getTrade_type());
		}
		if(null != weiXin_AppPay_unifiedorder.getNotify_url()) {
			Element notify_url = xml.addElement("notify_url");// 接收微信支付异步通知回调地址
			notify_url.setText(weiXin_AppPay_unifiedorder.getNotify_url());
		}
		if (null != weiXin_AppPay_unifiedorder.getDetail()) {
			Element detail = xml.addElement("detail");// 商品名称明细列表
			detail.setText(weiXin_AppPay_unifiedorder.getDetail());
		}
		if (null != weiXin_AppPay_unifiedorder.getAttach()) {
			Element attach = xml.addElement("attach");// 说明
														// 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
			attach.setText(weiXin_AppPay_unifiedorder.getAttach());
		}
		if (null != weiXin_AppPay_unifiedorder.getFee_type()) {
			Element fee_type = xml.addElement("fee_type");// 符合ISO
															// 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
			fee_type.setText(weiXin_AppPay_unifiedorder.getFee_type());
		}
		if (null != weiXin_AppPay_unifiedorder.getTime_start()) {
			Element time_start = xml.addElement("time_start");// 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
			time_start.setText(weiXin_AppPay_unifiedorder.getTime_start());
		}
		if (null != weiXin_AppPay_unifiedorder.getTime_expire()) {
			Element time_expire = xml.addElement("time_expire");// 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
			time_expire.setText(weiXin_AppPay_unifiedorder.getTime_expire());
		}
		if (null != weiXin_AppPay_unifiedorder.getGoods_tag()) {
			Element goods_tag = xml.addElement("goods_tag");// 商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
			goods_tag.setText(weiXin_AppPay_unifiedorder.getGoods_tag());
		}
		if (null != weiXin_AppPay_unifiedorder.getProduct_id()) {
			Element product_id = xml.addElement("product_id");// 商品IDtrade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
			product_id.setText(weiXin_AppPay_unifiedorder.getProduct_id());
		}
		if (null != weiXin_AppPay_unifiedorder.getLimit_pay()) {
			Element limit_pay = xml.addElement("limit_pay");// 指定支付方式
															// no_credit--指定不能使用信用卡支付
			limit_pay.setText(weiXin_AppPay_unifiedorder.getLimit_pay());
		}
		if (null != weiXin_AppPay_unifiedorder.getOpenid()) {
			Element openid = xml.addElement("openid");// 用户标识
														// 此参数必传，用户在商户appid下的唯一标识。下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
			openid.setText(weiXin_AppPay_unifiedorder.getOpenid());
		}
		if (null != weiXin_AppPay_unifiedorder.getAuth_code()) {
			Element openid = xml.addElement("auth_code");// 用户授权码
			openid.setText(weiXin_AppPay_unifiedorder.getAuth_code());
		}
		if (null != weiXin_AppPay_unifiedorder.getDevice_info()) {
			Element device_info = xml.addElement("device_info");// 设备号
			device_info.setText(weiXin_AppPay_unifiedorder.getDevice_info());
		}
		return document.asXML();
	}
	
	/**
	 * 生成queryOrder(查询订单)的XML
	 * 
	 * @param weiXin_AppPay_unifiedorder
	 */
	public String createQueryOrderXML(WeiXinUnifiedorderRequest weiXin_AppPay_unifiedorder) {
		Document document = DocumentHelper.createDocument();
		Element xml = document.addElement("xml");
		// 必填
		Element appid = xml.addElement("appid");// 公众账号ID
		appid.setText(weiXin_AppPay_unifiedorder.getAppid());

		Element mch_id = xml.addElement("mch_id");// 微信支付分配的商户号
		mch_id.setText(weiXin_AppPay_unifiedorder.getMch_id());

		Element nonce_str = xml.addElement("nonce_str");// 随机字符串，不长于32位。推荐随机数生成算法
		nonce_str.setText(weiXin_AppPay_unifiedorder.getNonce_str());

		Element out_trade_no = xml.addElement("out_trade_no");// 商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
		out_trade_no.setText(weiXin_AppPay_unifiedorder.getOut_trade_no());

		Element sign = xml.addElement("sign");// 签名，详见签名生成算法
		sign.setText(weiXin_AppPay_unifiedorder.getSign());

		return document.asXML();
	}

	/**
	 * 生成(关闭订单)的XML
	 * 
	 * @param weiXinOffOrderRequest
	 * @return
	 */
	public String creatWeiXin_offorderXML(WeiXinOffOrderRequest weiXinOffOrderRequest) {
		Document document = DocumentHelper.createDocument();
		Element xml = document.addElement("xml");
		// 必填
		Element appid = xml.addElement("appid");// 公众账号ID
		appid.setText(weiXinOffOrderRequest.getAppid());

		Element mch_id = xml.addElement("mch_id");// 微信支付分配的商户号
		mch_id.setText(weiXinOffOrderRequest.getMch_id());

		Element nonce_str = xml.addElement("nonce_str");// 随机字符串，不长于32位。推荐随机数生成算法
		nonce_str.setText(weiXinOffOrderRequest.getNonce_str());

		Element out_trade_no = xml.addElement("out_trade_no");// 商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
		out_trade_no.setText(weiXinOffOrderRequest.getOut_trade_no());

		Element sign = xml.addElement("sign");// 签名，详见签名生成算法
		sign.setText(weiXinOffOrderRequest.getSign());
		return document.asXML();
	}

	/**
	 * 生成(H5订单)的XML
	 * 
	 * @param weiXinOffOrderRequest
	 * @return
	 */
	public String creatWeiXin_H5APIXML(WeiXinH5APIRequest weiXinH5APIRequest) {
		Document document = DocumentHelper.createDocument();
		Element xml = document.addElement("xml");
		// 必填
		Element appid = xml.addElement("appId");// 公众账号ID
		appid.setText(weiXinH5APIRequest.getAppId());

		Element mch_id = xml.addElement("nonceStr");// 微信支付分配的商户号
		mch_id.setText(weiXinH5APIRequest.getNonceStr());

		Element prepay_id = xml.addElement("package");// 随机字符串，不长于32位。推荐随机数生成算法
		prepay_id.setText("prepay_id=" + weiXinH5APIRequest.getPrepay_id());

		Element signType = xml.addElement("signType");// 商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
		signType.setText(weiXinH5APIRequest.getSignType());

		Element timeStamp = xml.addElement("timeStamp");// 商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
		timeStamp.setText(weiXinH5APIRequest.getTimeStamp());

		Element sign = xml.addElement("paySign");// 签名，详见签名生成算法
		sign.setText(weiXinH5APIRequest.getPaySign());
		return document.asXML();

	}
	/**
	 * 通知H5支付结果
	 * @param pc_current_accounts 流水账实体
	 */
	public void notifyH5(Pc_current_accounts pc_current_accounts){
		logger.info("订单号：{}，开始回调H5Business的订单修改方法...",pc_current_accounts.getOut_trade_no());
		
		//回调H5Business的订单修改方法
		String requestURL = weiXinProfile.getH5_notifyorder_url();
		String paramsStr = "order_num=" + pc_current_accounts.getOut_trade_no();
		paramsStr += "&price=" + pc_current_accounts.getIncome();
		paramsStr += "&prepayid=" + pc_current_accounts.getWx_prepayid();
		
		logger.info("订单号：{}，回调H5的URL：{}",pc_current_accounts.getOut_trade_no(),requestURL);
		
		try {
			String result =HttpJsonTools.HttpClientGet(requestURL, paramsStr,new HashMap<String,String>());
			if("h5_success".equals(result)){
				logger.info("订单号：{}，回调H5Business的订单修改方法地址成功！",pc_current_accounts.getOut_trade_no());
			}else{
				logger.info("订单号：{}，回调H5Business返回未成功,结果：{}！",pc_current_accounts.getOut_trade_no(), result);
				//记录通知H5的失败记录，进行轮询通知
				logFailNotifyRecord(pc_current_accounts, requestURL, paramsStr, "http请求成功，返回失败" + result);
			}
			logger.info("订单号：{}，回调H5Business结束！",pc_current_accounts.getOut_trade_no());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单号：{}，通知H5支付结果时异常",pc_current_accounts.getOut_trade_no());
			//记录通知H5的失败记录，进行轮询通知
			logFailNotifyRecord(pc_current_accounts, requestURL, paramsStr, "http请求异常");
		} 
	}
	/**
	 * 对异步通知失败的情况进行保存
	 * @param pc_current_accounts
	 * @param requestURL
	 * @param paramsStr
	 */
	public void logFailNotifyRecord(Pc_current_accounts pc_current_accounts, String requestURL, String paramsStr, String cause_mark){
		int n =failNotifyRecordService.insert(PaymentPlatformConfig.WEIXIN, pc_current_accounts.getOut_trade_no(), pc_current_accounts.getPark_id(), 
				requestURL, paramsStr, pc_current_accounts.getWx_transaction_id(), cause_mark);
		if(n > 0) 
			logger.info("订单号：{}，异步通知失败保存成功！",pc_current_accounts.getOut_trade_no());
		else
			logger.info("订单号：{}，异步通知失败保存失败！",pc_current_accounts.getOut_trade_no());
	}

	@Override
	public String queryOrder(QueryOrderResponse queryOrderResp) {
		// 必填项目
		WeiXinUnifiedorderRequest weiXin_unifiedorderRequest = new WeiXinUnifiedorderRequest();// 发送给微信服务器的实体
		weiXin_unifiedorderRequest.setAppid(weiXinProfile.getAppid());// 公众账号ID
		weiXin_unifiedorderRequest.setMch_id(weiXinProfile.getMch_id());// 商户号
		weiXin_unifiedorderRequest.setNonce_str(String.valueOf((int) ((Math.random() * 10 + 1) * 10000000)));// 随机字符串
		weiXin_unifiedorderRequest.setNotify_url(weiXinProfile.getNotify_url());// 通知地址
		weiXin_unifiedorderRequest.setOut_trade_no(queryOrderResp.getParkid() + "_" + queryOrderResp.getOut_trade_no());// 订单号

		String info = ErrorCode.成功.getContent();
		int code = ErrorCode.成功.getCode();
		try {

			/******* 生成链接字符串并进行MD5加密 ********/
			String stringSignTemp = piece_together_WeiXin_queryorder(weiXin_unifiedorderRequest);
			weiXin_unifiedorderRequest.setSign(MD5encryptTool.getMD5(stringSignTemp));
			
			/************* 生成xml ************/
			String sendXML = createQueryOrderXML(weiXin_unifiedorderRequest);
			logger.info("生成请求微信的XML参数成功");
			
			/******* 向微信服务器发送 ********/
			String weixinOrderUrl = weiXinProfile.getOrderquery_url();
			String weixinresponse = sendWeiXinService(weixinOrderUrl, new String(sendXML.toString().getBytes(), "ISO8859-1"));
			logger.info("向微信发送查询订单请求成功");
			logger.info("微信查询订单返回结果：{}",weixinresponse);
			
			external_log_recordsService.creat_external_log_records(PaymentPlatformConfig.WEIXIN, weiXinProfile.getUnifiedorder_url(), stringSignTemp, weixinresponse);
			logger.info("记录查询订单请求的记录成功");
			
			/******* 向APP返回数据 ********/
			if (null != weixinresponse) {
				/******* 校验返回的数据是否合法 ********/
				Map<String, Object> map = XMLTool.xmlToMap(weixinresponse);
				logger.info("将微信返回的xml格式字符串转换为map成功");
				
				/*和微信的通信失败*/
				if (map.get("return_code").toString().equals("FAIL")) {
					logger.info("** 从微信查询订单 - 微信返回通信失败，提示信息为：{}",map.get("return_msg"));
					info = map.get("return_msg").toString();
					code = ErrorCode.服务器参数错误.getCode();
					return BackResultTools.response(code, info, "", "");
				} 
				/*校验签名通过*/
				if (checkMD5(weixinresponse)) {
					/*下单的业务逻辑处理失败*/
					if (map.get("result_code").toString().equals("FAIL")) {
						String wxErrCode = map.get("err_code").toString();
						info = map.get("err_code_des").toString();
						logger.info("** 从微信查询订单结果为FAIL - 状态码："+ wxErrCode +"返回信息："+ info);
						code = ErrorCode.服务器参数错误.getCode();
						return BackResultTools.response(code, info, "", "");
					/*下单的业务逻辑处理成功*/
					} else {
						String trade_state = map.get("trade_state").toString();
						if("SUCCESS".equals(trade_state)) {
							// 更新流水账表支付状态
							Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
							pc_current_accounts.setOrdernum(weiXin_unifiedorderRequest.getOut_trade_no());
							pc_current_accounts.setStatus(PayStatusConfig.YPAY);
							pc_current_accountsDao.update(pc_current_accounts);
							// 返回json数据
							WeiXinMicroRequest wxMicroRequest = new WeiXinMicroRequest();
							wxMicroRequest.setAppid(weiXinProfile.getAppid());
							wxMicroRequest.setOut_trade_no(String.valueOf(map.get("out_trade_no")));
							wxMicroRequest.setOpenid(String.valueOf(map.get("openid")));
							wxMicroRequest.setTotal_fee(String.valueOf(map.get("total_fee")));
							wxMicroRequest.setTime_end(String.valueOf(map.get("time_end")));
							wxMicroRequest.setTransaction_id(String.valueOf(map.get("transaction_id")));
							logger.info("** 从微信查询订单 - 返回数据：" + DataChangeTools.bean2gson(wxMicroRequest));
							return BackResultTools.response(code, info, wxMicroRequest, "");
						}else if("NOTPAY".equals(trade_state) || "USERPAYING".equals(trade_state)) {
							WeiXinMicroRequest wxMicroRequest = new WeiXinMicroRequest();
							wxMicroRequest.setAppid(weiXinProfile.getAppid());
							wxMicroRequest.setOut_trade_no(String.valueOf(map.get("out_trade_no")));
							logger.info("** 从微信查询订单 - 返回数据：" + DataChangeTools.bean2gson(wxMicroRequest));
							return BackResultTools.response(ErrorCode.刷卡支付中.getCode(), ErrorCode.刷卡支付中.getContent(), wxMicroRequest, "");
						}
						// 返回json数据
						WeiXinMicroRequest wxMicroRequest = new WeiXinMicroRequest();
						wxMicroRequest.setAppid(weiXinProfile.getAppid());
						wxMicroRequest.setOut_trade_no(String.valueOf(map.get("out_trade_no")));
						logger.info("** 从微信查询订单 - 返回数据：" + DataChangeTools.bean2gson(wxMicroRequest));
						return BackResultTools.response(ErrorCode.刷卡支付失败.getCode(), ErrorCode.刷卡支付失败.getContent(), wxMicroRequest, "");
					}
				/*校验签名失败*/
				} else {
					info = ErrorCode.微信发送的数据校验不通过.getContent();
					code = ErrorCode.微信发送的数据校验不通过.getCode();
					return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), info, weiXin_unifiedorderRequest, "");
				}
			}else{
				logger.info("微信返回对象为空!");
				return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), "微信返回对象为空" ,weiXin_unifiedorderRequest, "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent() + e.getMessage(), weiXin_unifiedorderRequest, "");
		}
	}
}