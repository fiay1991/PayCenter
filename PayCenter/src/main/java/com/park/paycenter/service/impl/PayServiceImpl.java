package com.park.paycenter.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.park.paycenter.config.PayEquipmentConfig;
import com.park.paycenter.config.PayStatusConfig;
import com.park.paycenter.config.PaymentPlatformConfig;
import com.park.paycenter.dao.Pc_current_accountsDao;
import com.park.paycenter.domain.Pc_current_accounts;
import com.park.paycenter.enums.ErrorCode;
import com.park.paycenter.requset.QueryOrderRequest;
import com.park.paycenter.response.OffOrderResponse;
import com.park.paycenter.response.QueryOrderResponse;
import com.park.paycenter.response.UnifiedorderResponse;
import com.park.paycenter.service.AlipayService;
import com.park.paycenter.service.PayService;
import com.park.paycenter.service.Pc_current_accountsService;
import com.park.paycenter.service.WeiXinPayService;
import com.park.paycenter.tools.BackResultTools;

/**
 * @author liuyang 时间：2016年4月6日 功能：支付相关业务逻辑 备注：
 */
@Repository(value = "PayServiceImpl")
public class PayServiceImpl implements PayService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "WeiXinPayServiceImpl")
	private WeiXinPayService weiXinPayService;

	@Resource(name = "AlipayServiceImpl")
	private AlipayService alipayService;
	
	@Resource(name = "Pc_current_accountsServiceImpl")
	private Pc_current_accountsService pc_current_accountsService;
	
	@Resource(name = "Pc_current_accountsDaoImpl")
	private Pc_current_accountsDao pc_current_accountsDao;
	
	

	/**
	 * 统一下单
	 */
	@Override
	public String unifiedorder(String params, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		// 验证参数
		if (null == params || params == "") {
			return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
		}
		// 非空字段验证
		UnifiedorderResponse unifiedorderResponse = new UnifiedorderResponse();
		try {
			unifiedorderResponse = gson.fromJson(params, UnifiedorderResponse.class);

			if (null == unifiedorderResponse.getBody() || null == unifiedorderResponse.getOut_trade_no() || null == unifiedorderResponse.getParkid() || null == unifiedorderResponse.getTerminal_type() || null == unifiedorderResponse.getTotal_fee() || null == unifiedorderResponse.getTrade_type()) {
				logger.error("订单号：{},传入参数错误！",unifiedorderResponse.getOut_trade_no());
				return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单号：{},出现服务器参数错误：{}！",unifiedorderResponse.getOut_trade_no(),e);
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent() + e.getMessage(), null, "");
		}
		//下单之前先去库里查一次看看有没有重复下单
		Pc_current_accounts search_pc_current_accounts = new Pc_current_accounts();
		search_pc_current_accounts.setPark_id(unifiedorderResponse.getParkid());
		search_pc_current_accounts.setOut_trade_no(unifiedorderResponse.getOut_trade_no());
		search_pc_current_accounts = pc_current_accountsDao.search(search_pc_current_accounts);
		if (null != search_pc_current_accounts) {
			logger.error("订单号：{},订单号已经存在请勿重复下单！",unifiedorderResponse.getOut_trade_no());
			return BackResultTools.response(ErrorCode.订单号已经存在请勿重复下单.getCode(), ErrorCode.订单号已经存在请勿重复下单.getContent(), null, "");
		}
		
		//如果订单金额为0不需要向任何支付平台发起支付直接入库流水账就可以
		if(Integer.valueOf(unifiedorderResponse.getTotal_fee()) <= 0){
			 pc_current_accountsService.creat_current_accounts(PaymentPlatformConfig.WEIXIN,
						new BigDecimal(unifiedorderResponse.getTotal_fee()), null,
						unifiedorderResponse.getOut_trade_no(), unifiedorderResponse.getTerminal_type(),
						unifiedorderResponse.getParkid(),
						unifiedorderResponse.getParkid() + "_" + unifiedorderResponse.getOut_trade_no(),
						null, null, PayStatusConfig.YPAY,null);
			 logger.info("订单号：{},订单金额为0不需要向任何支付平台发起支付",unifiedorderResponse.getOut_trade_no());
			 return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), unifiedorderResponse, "");
			 
		}
		// 区分支付平台
		if (unifiedorderResponse.getTrade_type().equals(PaymentPlatformConfig.WEIXIN)) {
			logger.info("订单号{},进入微信支付...",unifiedorderResponse.getOut_trade_no());
			return weiXinPayService.weixinunifiedorder(unifiedorderResponse, request, response);
		} else if (unifiedorderResponse.getTrade_type().equals(PaymentPlatformConfig.ZHIFUBAO)&&!unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.H5PayMent)) {
			logger.info("订单号{},进入支付宝非H5支付...",unifiedorderResponse.getOut_trade_no());
			return alipayService.alipayunifiedorder(unifiedorderResponse, request, response);
		} else if (unifiedorderResponse.getTrade_type().equals(PaymentPlatformConfig.ZHIFUBAO)&&unifiedorderResponse.getTerminal_type().equals(PayEquipmentConfig.H5PayMent)) {
			logger.info("订单号{},进入支付宝H5支付...",unifiedorderResponse.getOut_trade_no());
			return alipayService.new_H5Pay(unifiedorderResponse, request, response);
		} else {
			return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
		}
	}

	/**
	 * 查询订单
	 */
	@Override
	public String queryorder(String params) {
		// TODO Auto-generated method stub
		// 验证参数
		if (null == params || params == "") {
			return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
		}
		Gson gson = new Gson();
		// 非空字段验证
		QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
		try {
			queryOrderResponse = gson.fromJson(params, QueryOrderResponse.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent() + e.getMessage(), null, "");
		}
		// 查询
		Pc_current_accounts pc_current_accounts = new Pc_current_accounts();
		if (null != queryOrderResponse.getOut_trade_no()) {
			pc_current_accounts.setOut_trade_no(queryOrderResponse.getOut_trade_no());
		}
		if (null != queryOrderResponse.getParkid()) {
			pc_current_accounts.setPark_id(queryOrderResponse.getParkid());
		}
		if (null != queryOrderResponse.getPrepayid()) {
			pc_current_accounts.setWx_prepayid(queryOrderResponse.getPrepayid());
		}
		if (null != queryOrderResponse.getTrade_type()) {
			pc_current_accounts.setPlatform(queryOrderResponse.getTrade_type());
		}
		
		pc_current_accounts = pc_current_accountsDao.search(pc_current_accounts);
		// 统一下单后给用户展示的实体
		QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
		if (null != pc_current_accounts) {
			queryOrderRequest.setPay_result(pc_current_accounts.getStauts());
		}
		return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), queryOrderRequest, "");
	}

	/**
	 * 关闭订单
	 */
	@Override
	public String offorder(String params) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		// 验证参数
		if (null == params || params == "") {
			return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
		}
		//返回结果
		String resultResponse = "";
		// 非空字段验证
		OffOrderResponse offOrderResponse = new OffOrderResponse();
		try {
			offOrderResponse = gson.fromJson(params, OffOrderResponse.class);
			/**判断参数错误的情况**/
			/*如果是支付宝*/
			if(offOrderResponse != null && PaymentPlatformConfig.ZHIFUBAO.equals(offOrderResponse.getTrade_type())){
				if (null == offOrderResponse.getOut_trade_no() || null == offOrderResponse.getParkid()) {
					return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
				}
				logger.info("订单号：{}，支付宝关闭订单开始...",offOrderResponse.getOut_trade_no());
				resultResponse = alipayService.offOrder(offOrderResponse);
				logger.info("订单号：{}，支付宝关闭订单结束。",offOrderResponse.getOut_trade_no());
			/*如果是微信*/
			}else if(offOrderResponse != null && PaymentPlatformConfig.WEIXIN.equals(offOrderResponse.getTrade_type())){
				//判断关键必传参数是否有值
				if (null == offOrderResponse.getOut_trade_no() || null == offOrderResponse.getParkid() || null == offOrderResponse.getPrepayid()) {
					return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
				}
				logger.info("订单号：{}，微信关闭订单开始...",offOrderResponse.getOut_trade_no());
				resultResponse = weiXinPayService.offOrder(offOrderResponse);
				logger.info("订单号：{}，微信关闭订单结束。",offOrderResponse.getOut_trade_no());
			/*参数为空或交易方式类型为空*/
			}else{
				logger.info("订单号：{}，Trade_type交易方式类型参数为空！",offOrderResponse.getOut_trade_no());
				return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), null, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单号：{}，关闭订单接口异常：{}",offOrderResponse == null ?"": offOrderResponse.getOut_trade_no(),e);
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent() + e.getMessage(), null, "");
		}
		return resultResponse;
	}
	/**
	 * 支付完成后通知
	 */
	public String notice_of_payment(String trade_type, String WXparams, Map<String, String> ZFBparams) {
		
		/**验证参数为空的情况*/
		if(trade_type == null || (WXparams == null && ZFBparams == null)){
			logger.info("参数为空，请检查参数");
			return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), "", "");
		}
		if(PaymentPlatformConfig.WEIXIN.equals(trade_type) && WXparams != null){
			logger.info("进入微信异步通知分支...");
			return weiXinPayService.notice_of_payment(WXparams);
		}else if(PaymentPlatformConfig.ZHIFUBAO.equals(trade_type) && ZFBparams != null){
			logger.info("进入支付宝异步通知分支...");
			return alipayService.notice_of_payment(ZFBparams);
		}
		return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), "", "");
		
	}
	/**
	 * 支付完成后返回，只针对支付宝的返回
	 */
	public String return_of_payment(String trade_type, String WXparams, Map<String, String> ZFBparams) {
		/**验证参数为空的情况*/
		if(trade_type == null || (WXparams == null && ZFBparams == null)){
			logger.info("参数为空，请检查参数");
			return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), "", "");
		}
		if(PaymentPlatformConfig.WEIXIN.equals(trade_type) && WXparams != null){
			logger.info("进入微信同步返回通知分支...");
		}else if(PaymentPlatformConfig.ZHIFUBAO.equals(trade_type) && ZFBparams != null){
			logger.info("进入支付宝同步返回通知分支...");
			return alipayService.return_of_payment(ZFBparams);
		}
		return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), "", "");
		
	}

}
