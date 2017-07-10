package com.park.paycenter.dao.impl;

import java.awt.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.park.paycenter.dao.Pc_current_accountsDao;
import com.park.paycenter.domain.Pc_current_accounts;
import com.park.paycenter.mapper.Pc_current_accountsMapper;

@Repository(value = "Pc_current_accountsDaoImpl")
public class Pc_current_accountsDaoImpl implements Pc_current_accountsDao {

	@Resource(name = "Pc_current_accountsMapper")
	private Pc_current_accountsMapper pc_current_accountsMapper;

	@Override
	public Integer add(Pc_current_accounts pc_current_accounts) {
		// TODO Auto-generated method stub
		return pc_current_accountsMapper.insert(pc_current_accounts);
	}

	@Override
	public Integer update(Pc_current_accounts pc_current_accounts) {
		// TODO Auto-generated method stub
		return pc_current_accountsMapper.update(pc_current_accounts);
	}

	@Override
	public Pc_current_accounts search(Pc_current_accounts pc_current_accounts) {
		// TODO Auto-generated method stub
		return pc_current_accountsMapper.search(pc_current_accounts);
	}

	@Override
	public List searchList(Pc_current_accounts pc_current_accounts) {
		// TODO Auto-generated method stub
		return pc_current_accountsMapper.searchList(pc_current_accounts);
	}
}
