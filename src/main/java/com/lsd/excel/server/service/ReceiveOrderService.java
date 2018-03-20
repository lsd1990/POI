package com.lsd.excel.server.service;

import com.lsd.excel.model.ReceiveOrder;

public interface ReceiveOrderService {

	public Integer count();
	
	public void save(ReceiveOrder model);
}
