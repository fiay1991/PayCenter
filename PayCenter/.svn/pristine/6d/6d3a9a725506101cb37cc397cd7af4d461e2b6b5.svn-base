package com.park.paycenter.tools;

import com.google.gson.Gson;
import com.park.paycenter.requset.Response;

/**
 * @author liuyang
 * 时间：2015年7月27日
 * 功能：组装与外界返回json的根参数的工具类
 * 备注：
 */

public class BackResultTools {
	/**
	 * 返回通用的json
	 * @param code
	 * @param info
	 * @param object
	 * @param token
	 * @return
	 */
	public static String response(int code, String info, Object object, String token) {
		Response response = new Response();
		Gson gson = new Gson();
		response.setResult_code(code);
		response.setContent(info);
		response.setObject(object);
		response.setToken(token);
		return gson.toJson(response);
	}
	
	
}
