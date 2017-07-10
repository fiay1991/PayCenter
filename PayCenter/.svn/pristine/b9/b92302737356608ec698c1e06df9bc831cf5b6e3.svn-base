package com.park.paycenter.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liuyang
 *         时间：2016年4月15日
 *         功能：支付宝的通用配置类
 *         备注：
 */
@Component
@ConfigurationProperties(prefix = "AlipayProfile")
public class AlipayProfile {
	private String notify_url;// 支付结果通用通知

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	

}
