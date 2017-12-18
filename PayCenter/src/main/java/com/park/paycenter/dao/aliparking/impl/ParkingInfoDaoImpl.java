package com.park.paycenter.dao.aliparking.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.aliparking.ParkingInfoDao;
import com.park.paycenter.mapper.aliparking.ParkingInfoMapper;

@Repository(value="parkingInfoDaoImpl")
public class ParkingInfoDaoImpl implements ParkingInfoDao{

	@Resource(name="parkingInfoMapper")
	private ParkingInfoMapper parkingInfoMapper;
	
	@Override
	public int addParkInfo(Map<String, String> paramMap) {
		return parkingInfoMapper.insertParkInfo(paramMap);
	}

	@Override
	public int updateParkInfo(Map<String, String> paramMap) {
		return parkingInfoMapper.updateParkInfo(paramMap);
	}

	@Override
	public String getParkingIdByOutParkingId(String out_parking_id) {
		return parkingInfoMapper.selectParkingIdByOutParkingId(out_parking_id);
	}

	@Override
	public Map<String, String> getParkingIdAndParkingNameByOutParkingId(String out_parking_id) {
		return parkingInfoMapper.selectParkingIdAndParkingNameByParkingId(out_parking_id);
	}
	
}
