package com.park.paycenter.mapper.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingConfigMapepr")
public interface ParkingConfigMapper {

	/**
	 * 配置ISV成功后写入数据库
	 * @param paramMap
	 * @return
	 */
	int insertConfig(Map<String, String> paramMap);
	
}
