package com.park.paycenter.mapper.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingInfoMapper")
public interface ParkingInfoMapper {

	/**
	 * 录入车场成功写入数据库
	 * @param paramMap
	 * @return
	 */
	int insertParkInfo(Map<String, String> paramMap);

	/**
	 * 修改车场成功写入数据库
	 * @param paramMap
	 * @return
	 */
	int updateParkInfo(Map<String, String> paramMap);

	/**
	 * 通过out_parking_id查询支付宝parking_id
	 * @param out_parking_id
	 * @return
	 */
	String selectParkingIdByOutParkingId(String out_parking_id);
	
}
