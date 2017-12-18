package com.park.paycenter.service.aliparking;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author WangYuefei
 * @time 2017/12/18
 * @function 对订单信息的同步和更新操作
 * 
 */
@Repository(value="orderService")
public interface OrderService {

	/**
	 * 订单信息同步
	 * @param paramMap
	 * @return
	 */
	String orderSync(Map<String, String> paramMap);

	/**
	 * 订单信息更新
	 * @param paramMap
	 * @return
	 */
	String orderUpdate(Map<String, String> paramMap);

}
