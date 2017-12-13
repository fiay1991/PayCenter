package com.park.paycenter.service.aliparking.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingEnterinfoSyncRequest;
import com.alipay.api.response.AlipayEcoMycarParkingEnterinfoSyncResponse;
import com.park.paycenter.dao.aliparking.ParkingInfoDao;
import com.park.paycenter.enums.ErrorCode;
import com.park.paycenter.service.aliparking.CarService;
import com.park.paycenter.tools.BackResultTools;
import com.park.paycenter.tools.DataChangeTools;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="carServiceImpl")
public class CarServiceImpl implements CarService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="parkingInfoDaoImpl")
	private ParkingInfoDao parkingInfoDao;
	
	@Override
	public String carEnter(Map<String, String> paramMap) {
		try {
			logger.info("** 车辆入场信息同步 - 传入参数：" + DataChangeTools.bean2gson(paramMap));
			// 验证必传参数
			String out_parking_id = paramMap.get("out_parking_id");
			if(null == out_parking_id || "".equals(out_parking_id)) {
				logger.info("** 车辆入场信息同步 - park_id为空：park_id = " + out_parking_id);
				return BackResultTools.response(ErrorCode.传入参数错误.getCode(), ErrorCode.传入参数错误.getContent(), "", "");
			}
			// 通过out_parking_id查询parking_id
			String parking_id = parkingInfoDao.getParkingIdByOutParkingId(out_parking_id);
			// 开始准备向支付宝发送请求
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
			AlipayEcoMycarParkingEnterinfoSyncRequest request = new AlipayEcoMycarParkingEnterinfoSyncRequest();
			request.setBizContent("{" +
			"\"parking_id\":\"" + parking_id + "\"," +
			"\"car_number\":\"" + paramMap.get("car_number") + "\"," +
			"\"in_time\":\"" + paramMap.get("in_time") + "\"" +
			"  }");
			logger.info("** 车辆入场信息同步 - 待发送参数：" + DataChangeTools.bean2gson(request));
			AlipayEcoMycarParkingEnterinfoSyncResponse response = alipayClient.execute(request);
			logger.info("** 车辆入场信息同步 - 支付宝返回结果：" + DataChangeTools.bean2gson(response));
			// 根据返回的code进行不同的处理
			paramMap.remove("in_time");
			logger.info("** 车辆入场信息同步 - 返回给调用方：" + DataChangeTools.bean2gson(paramMap));
			String code = response.getCode();
			if("10000".equals(code)) {
				logger.info("** 车辆入场信息同步 - 同步成功！" + DataChangeTools.bean2gson(paramMap));
				return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), paramMap, "");
			}else {
				logger.info("** 车辆入场信息同步 - 同步失败。" + DataChangeTools.bean2gson(paramMap));
				return BackResultTools.response(ErrorCode.失败.getCode(), response.getSubMsg(), paramMap, "");
			}
		} catch (Exception e) {
			logger.info("** 车辆入场信息同步 - 服务器出现异常：" + e.getMessage());
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent(), "", "");
		}
	}

}
