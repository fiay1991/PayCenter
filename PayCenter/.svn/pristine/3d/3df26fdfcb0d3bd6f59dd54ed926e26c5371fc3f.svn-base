package com.park.paycenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.TargetDataSource;
import com.park.paycenter.dao.DemoDao;
import com.park.paycenter.domain.Demo;
import com.park.paycenter.service.DemoService;

/**
 * @author liuyang
 * 时间：2016年3月24日
 * 功能：
 * 备注：
 */
@Repository(value = "DemoServiceImpl")
public class DemoServiceImpl implements DemoService {
	
	@Resource(name = "DemoDaoImpl")
	private DemoDao demoDao;
	
	@Override
	@TargetDataSource(name="ds2")
	public List<Demo> findAll() {
		// TODO Auto-generated method stub
		List<Demo>list = demoDao.findAll();
		return list;
	}

}
