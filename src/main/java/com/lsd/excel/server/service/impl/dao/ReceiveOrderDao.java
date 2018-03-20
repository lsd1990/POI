package com.lsd.excel.server.service.impl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lsd.excel.model.ReceiveOrder;


public interface ReceiveOrderDao {

	public List<ReceiveOrder> list(@Param("offset")Integer offset, @Param("limit")Integer limit);
	
	public Integer count();
	
	public void save(ReceiveOrder model);
	
	public void update();
}
