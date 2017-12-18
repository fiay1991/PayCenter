package com.park.paycenter.service.aliparking.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingOrderSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderUpdateRequest;
import com.alipay.api.response.AlipayEcoMycarParkingOrderSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderUpdateResponse;
import com.park.paycenter.dao.aliparking.ParkingInfoDao;
import com.park.paycenter.enums.ErrorCode;
import com.park.paycenter.profile.AlipayConfigProfile;
import com.park.paycenter.service.aliparking.OrderService;
import com.park.paycenter.tools.BackResultTools;
import com.park.paycenter.tools.DataChangeTools;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/18
 * @function 对订单信息的同步和更新操作
 * 
 */
@Repository(value="orderServiceImpl")
public class OrderServiceImpl implements OrderService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="parkingInfoDaoImpl")
	private ParkingInfoDao parkingInfoDao;
	
	@Autowired
	AlipayConfigProfile alipayConfigProfile;

	@Override
	public String orderSync(Map<String, String> paramMap) {
		try {
			logger.info("** 订单信息同步 - 传入参数：" + DataChangeTools.bean2gson(paramMap));
			// 验证必传参数
			String out_parking_id = paramMap.get("park_id");
			if(null == out_parking_id || "".equals(out_parking_id)) {
				logger.info("** 订单信息同步 - park_id为空：park_id = " + out_parking_id);
				return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), "", "");
			}
			// 根据park_id查询parking_id和parking_name
			Map<String, String> parkingMap = parkingInfoDao.getParkingIdAndParkingNameByOutParkingId(out_parking_id);
			// 开始准备向支付宝发送请求
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",alipayConfigProfile.getAppid(),
					alipayConfigProfile.getRsa_private_key(),alipayConfigProfile.getFormat(),
					alipayConfigProfile.getCharset(),alipayConfigProfile.getAlipay_public_key(),alipayConfigProfile.getSigntype());
			AlipayEcoMycarParkingOrderSyncRequest request = new AlipayEcoMycarParkingOrderSyncRequest();
			request.setBizContent("{" +
			"\"user_id\":\"" + paramMap.get("open_id") + "\"," +
			"\"out_parking_id\":\"" + out_parking_id + "\"," +
			"\"parking_name\":\"" + parkingMap.get("parking_name") + "\"," +
			"\"car_number\":\"" + paramMap.get("car_number") + "\"," +
			"\"out_order_no\":\"" + paramMap.get("out_order_no") + "\"," +
			"\"order_status\":\"" + paramMap.get("order_status") + "\"," +
			"\"order_time\":\"" + paramMap.get("order_time") + "\"," +
			"\"order_no\":\"" + paramMap.get("order_no") + "\"," +
			"\"pay_time\":\"" + paramMap.get("pay_time") + "\"," +
			"\"pay_type\":\"" + paramMap.get("pay_type") + "\"," +
			"\"pay_money\":\"" + paramMap.get("pay_money") + "\"," +
			"\"in_time\":\"" + paramMap.get("in_time") + "\"," +
			"\"parking_id\":\"" + parkingMap.get("parking_id") + "\"," +
			"\"in_duration\":\"" + paramMap.get("in_duration") + "\"," +
			"\"card_number\":\"" + paramMap.get("card_number") + "\"" +
			"  }");
			logger.info("** 订单信息同步 - 待发送参数：" + DataChangeTools.bean2gson(request));
			AlipayEcoMycarParkingOrderSyncResponse response = alipayClient.execute(request);
			logger.info("** 订单信息同步 - 支付宝返回结果：" + DataChangeTools.bean2gson(response));
			// 根据返回的code进行不同的处理
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("park_id", out_parking_id);
			resultMap.put("car_number", paramMap.get("car_number"));
			resultMap.put("out_order_no", paramMap.get("out_order_no"));
			String code = response.getCode();
			if("10000".equals(code)) {
				logger.info("** 订单信息同步 - 同步成功！" + DataChangeTools.bean2gson(paramMap));
				return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), resultMap, "");
			}else {
				logger.info("** 订单信息同步 - 同步失败。" + DataChangeTools.bean2gson(paramMap));
				return BackResultTools.response(ErrorCode.失败.getCode(), response.getSubMsg(), resultMap, "");
			}
		} catch (Exception e) {
			logger.info("** 订单信息同步 - 服务器出现异常：" + e.getMessage());
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent(), "", "");
		}
	}

	@Override
	public String orderUpdate(Map<String, String> paramMap) {
		try {
			logger.info("** 订单信息更新 - 传入参数：" + DataChangeTools.bean2gson(paramMap));
			// 开始准备向支付宝发送请求
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
			AlipayEcoMycarParkingOrderUpdateRequest request = new AlipayEcoMycarParkingOrderUpdateRequest();
			request.setBizContent("{" +
			"\"user_id\":\"" + paramMap.get("open_id") + "\"," +
			"\"order_no\":\"" + paramMap.get("order_no") + "\"," +
			"\"order_status\":\"" + paramMap.get("order_status") + "\"" +
			"  }");
			logger.info("** 订单信息更新 - 待发送参数：" + DataChangeTools.bean2gson(request));
			AlipayEcoMycarParkingOrderUpdateResponse response = alipayClient.execute(request);
			logger.info("** 订单信息更新 - 支付宝返回结果：" + DataChangeTools.bean2gson(response));
			// 根据返回的code进行不同的处理
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("order_no", paramMap.get("order_no"));
			String code = response.getCode();
			if("10000".equals(code)) {
				logger.info("** 订单信息更新 - 更新成功！" + DataChangeTools.bean2gson(paramMap));
				return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), resultMap, "");
			}else {
				logger.info("** 订单信息更新 - 更新失败。" + DataChangeTools.bean2gson(paramMap));
				return BackResultTools.response(ErrorCode.失败.getCode(), response.getSubMsg(), resultMap, "");
			}
		} catch (Exception e) {
			logger.info("** 订单信息更新 - 服务器出现异常：" + e.getMessage());
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent(), "", "");
		}
	}
	
}
