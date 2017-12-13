package com.park.paycenter.tools;

import java.lang.reflect.Type;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataChangeTools{
	private static Logger logger = Logger.getLogger(DataChangeTools.class);
	
	/***
	 * gson 转bean
	 * @param params
	 * @param object
	 * @param gson
	 * @return
	 */
	public static <T> T gson2bean(String params,Class<T> clazz){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			 gson =new GsonBuilder().serializeNulls().create();
		try {
			return gson.fromJson(params, clazz);
		} catch (Exception e) {
			logger.error("***  请求参数转换错误");
			logger.error("***  参数为"+params);
			logger.error("** "+e);
			return null;
		}
	}
	
	/***
	 * gson 转list
	 * @param params
	 * @param object
	 * @param gson
	 * @return
	 */
	public static <T> T gson2List(String params,Type type){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		try {
			return gson.fromJson(params, type);
		} catch (Exception e) {
			logger.error("***  请求参数转换错误");
			logger.error("** "+e);
			return null;
		}
	}
	/**
	 * bean转gson
	 * @param <T>
	 */
	public static <T> String bean2gson(Object object){
		Gson gson =new Gson();
		return gson.toJson(object);
	}
	
	/**
	 * fastjson   json 转bean
	 * @return 
	 */
//	public static <T> T fastjson2bean(String params,Class<T> clazz){
//		return 	JSON.parseObject(params, clazz);
//	}
//	/**
//	 * fastjson   bean转json 
//	 */
//	public static <T> String bean2json(Object object){
//		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
//	}
	/** 
     * 将json格式封装的字符串数据转换成java中的Map数据 
	 * @param <T>
     * @return 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Map<String, String> json2Map(String params) {
        Gson gson =new Gson();
        return (Map)gson.fromJson(params, new TypeToken<T>() {}.getType());
    } 
}
