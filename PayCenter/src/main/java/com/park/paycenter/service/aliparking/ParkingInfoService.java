package com.park.paycenter.service.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/12
 * @function 停车场信息的新增和修改操作
 * 
 */
@Repository(value="parkingInfoService")
public interface ParkingInfoService {
	
	/**
	 * 录入停车场信息
	 * @param paramMap
	 * @return
	 */
	String create(Map<String, String> paramMap);

	/**
	 * 修改停车场信息
	 * @param paramMap
	 * @return
	 */
	String update(Map<String, String> paramMap);
	
}
