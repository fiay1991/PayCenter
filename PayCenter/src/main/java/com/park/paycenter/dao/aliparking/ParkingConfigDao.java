package com.park.paycenter.dao.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingConfigDao")
public interface ParkingConfigDao {

	/**
	 * 配置ISV成功后写入数据库
	 * @param paramMap
	 * @return
	 */
	int addConfig(Map<String, String> paramMap);

}
