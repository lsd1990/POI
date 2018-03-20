package com.lsd.excel.util;

import java.io.Serializable;
import java.util.Map;

public class GridHeaderData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6178241600969041204L;
	private String header;
	private int width;
	private boolean right;
	private String numberFormat;
	private String dateFormat;
	private String path;
	private Map<String, Object> map ;
	
	public GridHeaderData() {
		
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
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
