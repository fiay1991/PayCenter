package com.park.paycenter.service.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/13
 * @function 对车辆进出场信息的同步和查询操作
 * 
 */
@Repository(value="carService")
public interface CarService {

	/**
	 * 车辆入场信息同步
	 * @param paramMap
	 * @return
	 */
	String carEnter(Map<String, String> paramMap);

	/**
	 * 通过car_id和access_token获取car_number
	 * @param paramMap
	 * @return
	 */
	String getCarNumber(Map<String, String> paramMap);
	
}
