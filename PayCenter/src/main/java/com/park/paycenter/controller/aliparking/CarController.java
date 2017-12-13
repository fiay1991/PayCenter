package com.park.paycenter.controller.aliparking;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.paycenter.service.aliparking.CarService;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/13
 * @function 对车辆进出场信息的同步和查询操作
 * 
 */
@Controller
@RequestMapping(value="/car")
public class CarController {
	
	@Resource(name="carServiceImpl")
	private CarService carService;

	@ResponseBody
	@RequestMapping(value="enter", produces="text/html;charset=UTF-8")
	public String carEnter(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("parking_id", request.getParameter("parking_id"));
		paramMap.put("car_number", request.getParameter("car_number"));
		paramMap.put("in_time", request.getParameter("in_time"));
		return carService.carEnter(paramMap);
	}
	
}
