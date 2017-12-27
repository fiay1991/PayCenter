package com.park.paycenter.controller.aliparking;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.base.common.DataChangeTools;
import com.park.paycenter.service.aliparking.ParkingConfigService;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/11
 * @function ISV系统配置和查询
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
		String params = request.getParameter("params");
		Map<String, String> paramMap = DataChangeTools.json2Map(params);
		
		return parkingConfigService.configSet(paramMap);
	}
	
	/**
	 * ISV系统配置查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query", produces="text/html;charset=UTF-8")
	public String configQuery(HttpServletRequest request, HttpServletResponse response) {
		return parkingConfigService.configQuery();
	}
	
}
