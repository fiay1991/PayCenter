package com.park.paycenter.service.aliparking.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoCreateRequest;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoUpdateRequest;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoCreateResponse;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoUpdateResponse;
import com.park.paycenter.dao.aliparking.ParkingInfoDao;
import com.park.paycenter.enums.ErrorCode;
import com.park.paycenter.service.aliparking.ParkingInfoService;
import com.park.paycenter.tools.BackResultTools;
import com.park.paycenter.tools.DataChangeTools;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingInfoServiceImpl")
public class ParkingInfoServiceImpl implements ParkingInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="parkingInfoDaoImpl")
	private ParkingInfoDao parkingInfoDao;
	
	@Override
	public String create(Map<String, String> paramMap) {
		try {
			logger.info("** 录入车场信息 - 传入参数：" + DataChangeTools.bean2gson(paramMap));
			// 开始准备调用支付宝
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
			AlipayEcoMycarParkingParkinglotinfoCreateRequest request = new AlipayEcoMycarParkingParkinglotinfoCreateRequest();
			request.setBizContent("{" +
			"\"city_id\":\"" + paramMap.get("city_id") + "\"," +
			"\"equipment_name\":\"" + paramMap.get("equipment_name") + "\"," +
			"\"out_parking_id\":\"" + paramMap.get("out_parking_id") + "\"," +
			"\"parking_address\":\"" + paramMap.get("parking_address") + "\"," +
			"\"longitude\":\"" + paramMap.get("longitude") + "\"," +
			"\"latitude\":\"" + paramMap.get("latitude") + "\"," +
			"\"parking_start_time\":\"" + paramMap.get("parking_start_time") + "\"," +
			"\"parking_end_time\":\"" + paramMap.get("parking_end_time") + "\"," +
			"\"parking_number\":\"" + paramMap.get("parking_number") + "\"," +
			"\"parking_lot_type\":\"" + paramMap.get("parking_lot_type") + "\"," +
			"\"parking_type\":\"" + paramMap.get("parking_type") + "\"," +
			"\"payment_mode\":\"" + paramMap.get("payment_mode") + "\"," +
			"\"pay_type\":\"" + paramMap.get("pay_type") + "\"," +
			"\"shopingmall_id\":\"" + paramMap.get("shopingmall_id") + "\"," +
			"\"parking_fee_description\":\"" + paramMap.get("parking_fee_description") + "\"," +
			"\"contact_name\":\"" + paramMap.get("contact_name") + "\"," +
			"\"contact_mobile\":\"" + paramMap.get("contact_mobile") + "\"," +
			"\"contact_tel\":\"" + paramMap.get("contact_tel") + "\"," +
			"\"contact_emali\":\"" + paramMap.get("contact_email") + "\"," +
			"\"contact_weixin\":\"" + paramMap.get("contact_weixin") + "\"," +
			"\"contact_alipay\":\"" + paramMap.get("contact_alipay") + "\"," +
			"\"parking_name\":\"" + paramMap.get("parking_name") + "\"," +
			"\"time_out\":\"" + paramMap.get("time_out") + "\"" + // time_out 超时时间，单位分钟
			"}");
			logger.info("** 录入车场信息 - 待发送参数：" + DataChangeTools.bean2gson(request));
			AlipayEcoMycarParkingParkinglotinfoCreateResponse response = alipayClient.execute(request);
			logger.info("** 录入车场信息 - 支付宝返回结果：" + DataChangeTools.bean2gson(response));
			// 根据返回的不同code返回相应结果
			String code = response.getCode();
			if("10000".equals(code)) {
				logger.info("** 录入车辆信息 - 录入成功！out_parking_id = " + paramMap.get("out_parking_id"));
				// 将支付宝返回的parking_id放入paramMap并将车场数据写入数据库
				paramMap.put("parking_id", response.getParkingId());
				int addResult = parkingInfoDao.addParkInfo(paramMap);
				if(0 > addResult) {
					logger.info("** 录入车场信息 - 录入失败。out_parking_id = " + paramMap.get("out_parking_id"));
				}
				return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), paramMap, "");
			}else {
				logger.info("** 录入车辆信息 - 录入失败。out_parking_id = " + paramMap.get("out_parking_id"));
				return BackResultTools.response(ErrorCode.失败.getCode(), response.getSubMsg(), paramMap, "");
			}
		} catch (Exception e) {
			logger.info("** 录入车场信息 - 服务器出现异常：" + e.getMessage());
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent(), "", "");
		}
	}

	@Override
	public String update(Map<String, String> paramMap) {
		try {
			logger.info("** 修改车场信息 - 传入参数：" + DataChangeTools.bean2gson(paramMap));
			// 开始准备调用支付宝
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
			AlipayEcoMycarParkingParkinglotinfoUpdateRequest request = new AlipayEcoMycarParkingParkinglotinfoUpdateRequest();
			request.setBizContent("{" +
			"\"parking_id\":\"" + paramMap.get("parking_id") + "\"," +
			"\"city_id\":\"" + paramMap.get("city_id") + "\"," +
			"\"equipment_name\":\"" + paramMap.get("equipment_name") + "\"," +
			"\"out_parking_id\":\"" + paramMap.get("out_parking_id") + "\"," +
			"\"parking_address\":\"" + paramMap.get("parking_address") + "\"," +
			"\"longitude\":\"" + paramMap.get("longitude") + "\"," +
			"\"latitude\":\"" + paramMap.get("latitude") + "\"," +
			"\"parking_start_time\":\"" + paramMap.get("parking_start_time") + "\"," +
			"\"parking_end_time\":\"" + paramMap.get("parking_end_time") + "\"," +
			"\"parking_name\":\"" + paramMap.get("parking_name") + "\"," +
			"\"parking_number\":\"" + paramMap.get("parking_number") + "\"," +
			"\"parking_lot_type\":\"" + paramMap.get("parking_lot_type") + "\"," +
			"\"parking_type\":\"" + paramMap.get("parking_type") + "\"," +
			"\"payment_mode\":\"" + paramMap.get("payment_mode") + "\"," +
			"\"pay_type\":\"" + paramMap.get("pay_type") + "\"," +
			"\"shopingmall_id\":\"" + paramMap.get("shopingmall_id") + "\"," +
			"\"parking_fee_description\":\"" + paramMap.get("parking_fee_description") + "\"," +
			"\"contact_name\":\"" + paramMap.get("contact_name") + "\"," +
			"\"contact_mobile\":\"" + paramMap.get("contact_mobile") + "\"," +
			"\"contact_tel\":\"" + paramMap.get("contact_tel") + "\"," +
			"\"contact_email\":\"" + paramMap.get("contact_email") + "\"," +
			"\"contact_weixin\":\"" + paramMap.get("contact_weixin") + "\"," +
			"\"contact_alipay\":\"" + paramMap.get("contact_alipay") + "\"," +
			"}");
			logger.info("** 修改车场信息 - 待发送参数：" + DataChangeTools.bean2gson(request));
			AlipayEcoMycarParkingParkinglotinfoUpdateResponse response = alipayClient.execute(request);
			logger.info("** 修改车场信息 - 支付宝返回结果：" + DataChangeTools.bean2gson(response));
			// 根据返回的不同code返回相应结果
			String code = response.getCode();
			if("10000".equals(code)) {
				logger.info("** 修改车辆信息 - 修改成功！parking_id = " + paramMap.get("parking_id"));
				// 将支付宝返回的parking_id放入paramMap并将车场数据写入数据库
				int addResult = parkingInfoDao.updateParkInfo(paramMap);
				if(0 > addResult) {
					logger.info("** 修改车场信息 - 修改失败。parking_id = " + paramMap.get("parking_id"));
				}
				return BackResultTools.response(ErrorCode.成功.getCode(), ErrorCode.成功.getContent(), paramMap, "");
			}else {
				logger.info("** 录入车辆信息 - 录入失败。out_parking_id = " + paramMap.get("out_parking_id"));
				return BackResultTools.response(ErrorCode.失败.getCode(), response.getSubMsg(), paramMap, "");
			}
		} catch (Exception e) {
			logger.info("** 修改车场信息 - 服务器出现异常：" + e.getMessage());
			e.printStackTrace();
			return BackResultTools.response(ErrorCode.服务器参数错误.getCode(), ErrorCode.服务器参数错误.getContent(), "", "");
		}
	}

}