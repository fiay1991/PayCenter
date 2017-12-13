package com.park.paycenter.dao.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 *
 */
@Repository(value="parkingInfoDao")
public interface ParkingInfoDao {

	/**
	 * 录入车场成功写入数据库
	 * @param paramMap
	 * @return
	 */
	int addParkInfo(Map<String, String> paramMap);

	/**
	 * 修改车场成功写入数据库
	 * @param paramMap
	 * @return
	 */
	int updateParkInfo(Map<String, String> paramMap);

	/**
	 * 通过out_parking_id查询支付宝parking_id
	 * @param string
	 * @return
	 */
	String getParkingIdByOutParkingId(String string);

}
