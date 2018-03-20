package com.lsd.excel.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsd.excel.model.ReceiveOrder;
import com.lsd.excel.server.service.ReceiveOrderService;
import com.lsd.excel.server.service.impl.dao.ReceiveOrderDao;

@Service
public class ReceiveOrderServiceImpl implements ReceiveOrderService {
	
	@Resource
	private ReceiveOrderDao receiveOrderDao;

	@Override
	public Integer count() {
		return 
				receiveOrderDao.count();
	}

	@Override
	public void save(ReceiveOrder model) {
		receiveOrderDao.save(model);
	}

}
