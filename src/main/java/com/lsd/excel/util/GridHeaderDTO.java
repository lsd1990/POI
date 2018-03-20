package com.lsd.excel.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;


public class GridHeaderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6855704350636146730L;
	private String header;
	private int width;
	private boolean right;
	private String numberFormat;
	private String dateFormat;
	private String path;
	private Method method;
	
	private Map<String, Object> map ;
	
	public GridHeaderDTO() {
		
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public String getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
