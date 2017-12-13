package com.park.paycenter.controller.aliparking;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.paycenter.service.aliparking.ParkingConfigService;

/**
 * 
 * @author WangYuefei
 *
 */
@Controller
@RequestMapping(value = "/parkingconfig")
public class ParkingConfigController {
	
	@Resource(name="parkingConfigServiceImpl")
	private ParkingConfigService parkingConfigService;
	
	/**
	 * ISV系统配置接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/set", produces="text/html;charset=UTF-8")
	public String configSet(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("merchant_name", request.getParameter("merchant_name"));
		paramMap.put("merchant_service_phone", request.getParameter("merchant_service_phone"));
		paramMap.put("account_no", request.getParameter("account_no"));
		paramMap.put("interface_url", request.getParameter("interface_url"));
		paramMap.put("merchant_logo", request.getParameter("merchant_logo"));
		
		return parkingConfigService.configSet(paramMap);
	}
	
	/**
	 * ISV系统配置查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/config", produces="text/html;charset=UTF-8")
	public String configQuery(HttpServletRequest request, HttpServletResponse response) {
		return parkingConfigService.configQuery();
	}
	
}
