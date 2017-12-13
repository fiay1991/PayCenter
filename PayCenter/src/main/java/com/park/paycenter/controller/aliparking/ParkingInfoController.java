package com.park.paycenter.controller.aliparking;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.paycenter.service.aliparking.ParkingInfoService;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/12
 * @function 停车场信息的新增和修改操作
 * 
 */
@Controller
@RequestMapping(value="/parkinginfo")
public class ParkingInfoController {
	
	@Resource(name="parkingInfoServiceImpl")
	private ParkingInfoService parkingInfoService;

	@ResponseBody
	@RequestMapping(value="/create", produces="text/html;charset=UTF-8")
	public String parkingCreate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city_id", request.getParameter("city_id"));
		paramMap.put("equipment_name", request.getParameter("equipment_name"));
		paramMap.put("out_parking_id", request.getParameter("park_id"));
		paramMap.put("parking_address", request.getParameter("parking_address"));
		paramMap.put("longitude", request.getParameter("longitude"));
		paramMap.put("latitude", request.getParameter("latitude"));
		paramMap.put("parking_start_time", request.getParameter("parking_start_time"));
		paramMap.put("parking_end_time", request.getParameter("parking_end_time"));
		paramMap.put("parking_number", request.getParameter("parking_number"));
		paramMap.put("parking_lot_type", request.getParameter("parking_lot_type"));
		paramMap.put("parking_type", request.getParameter("parking_type"));
		paramMap.put("payment_mode", request.getParameter("payment_mode"));
		paramMap.put("pay_type", request.getParameter("pay_type"));
		paramMap.put("shopingmall_id", request.getParameter("shopingmall_id"));
		paramMap.put("parking_fee_description", request.getParameter("parking_fee_description"));
		paramMap.put("contact_name", request.getParameter("contact_name"));
		paramMap.put("contact_mobile", request.getParameter("contact_mobile"));
		paramMap.put("contact_tel", request.getParameter("contact_tel"));
		paramMap.put("contact_email", request.getParameter("contact_email"));
		paramMap.put("contact_weixin", request.getParameter("contact_weixin"));
		paramMap.put("contact_alipay", request.getParameter("contact_alipay"));
		paramMap.put("parking_name", request.getParameter("parking_name"));
		paramMap.put("time_out", request.getParameter("time_out"));
		return parkingInfoService.create(paramMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/update", produces="text/html;charset=UTF-8")
	public String parkingUpdate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city_id", request.getParameter("city_id"));
		paramMap.put("equipment_name", request.getParameter("equipment_name"));
		paramMap.put("out_parking_id", request.getParameter("park_id"));
		paramMap.put("parking_address", request.getParameter("parking_address"));
		paramMap.put("longitude", request.getParameter("longitude"));
		paramMap.put("latitude", request.getParameter("latitude"));
		paramMap.put("parking_start_time", request.getParameter("parking_start_time"));
		paramMap.put("parking_end_time", request.getParameter("parking_end_time"));
		paramMap.put("parking_number", request.getParameter("parking_number"));
		paramMap.put("parking_lot_type", request.getParameter("parking_lot_type"));
		paramMap.put("parking_type", request.getParameter("parking_type"));
		paramMap.put("payment_mode", request.getParameter("payment_mode"));
		paramMap.put("pay_type", request.getParameter("pay_type"));
		paramMap.put("shopingmall_id", request.getParameter("shopingmall_id"));
		paramMap.put("parking_fee_description", request.getParameter("parking_fee_description"));
		paramMap.put("contact_name", request.getParameter("contact_name"));
		paramMap.put("contact_mobile", request.getParameter("contact_mobile"));
		paramMap.put("contact_tel", request.getParameter("contact_tel"));
		paramMap.put("contact_email", request.getParameter("contact_email"));
		paramMap.put("contact_weixin", request.getParameter("contact_weixin"));
		paramMap.put("contact_alipay", request.getParameter("contact_alipay"));
		paramMap.put("parking_name", request.getParameter("parking_name"));
		return parkingInfoService.update(paramMap);
	}
	
}
