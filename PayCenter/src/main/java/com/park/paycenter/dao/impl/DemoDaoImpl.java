package com.park.paycenter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.DemoDao;
import com.park.paycenter.domain.Demo;
import com.park.paycenter.mapper.DemoMapper;

/**
 * @author liuyang
 * 时间：2016年3月25日
 * 功能：
 * 备注：
 */
//@Component
@Repository(value = "DemoDaoImpl")
public class DemoDaoImpl implements DemoDao {
	
	@Resource(name = "DemoMapper")
	private DemoMapper demoMapper;
	
	@Override
	public List<Demo> findAll() {
		// TODO Auto-generated method stub
		return demoMapper.findAll();
	}

}
