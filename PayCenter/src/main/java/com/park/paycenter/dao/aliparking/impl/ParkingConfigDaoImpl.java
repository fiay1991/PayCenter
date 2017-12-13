package com.park.paycenter.dao.aliparking.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.aliparking.ParkingConfigDao;
import com.park.paycenter.mapper.aliparking.ParkingConfigMapper;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingConfigDaoImpl")
public class ParkingConfigDaoImpl implements ParkingConfigDao {
	
	@Resource(name="parkingConfigMapepr")
	private ParkingConfigMapper parkingConfigMapper;

	@Override
	public int addConfig(Map<String, String> paramMap) {
		return parkingConfigMapper.insertConfig(paramMap);
	}

}
