package com.park.paycenter.service.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingConfigService")
public interface ParkingConfigService {

	/**
	 * ISV系统配置
	 * @param paramMap
	 * @return
	 */
	String configSet(Map<String, String> paramMap);

	/**
	 * ISV系统配置查询接口
	 * @return
	 */
	String configQuery();
	
}
