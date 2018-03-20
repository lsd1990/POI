package com.lsd.excel.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 仅用于报表对象总合计！
 * @author nhsoft.lsd@hotmail.com
 * @date 2016年11月30日
 *
 */
public class BaseReportData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2053184006388550870L;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public void set(String property, Object value){
		map.put(property, value);
	}
	
	public Object get(String property){
		return map.get(property);
	}

	public Map<String, Object> getMap(){
		return map;
	}
}
