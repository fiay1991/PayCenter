package com.park.paycenter.service.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
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
