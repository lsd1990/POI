package com.lsd.excel.util;

import java.io.Serializable;

public class GridHeaderGroupDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7109764997806505855L;
	private String groupName;
	private int groupColumn;
	private int groupColspan;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupColumn() {
		return groupColumn;
	}
	public void setGroupColumn(int groupColumn) {
		this.groupColumn = groupColumn;
	}
	public int getGroupColspan() {
		return groupColspan;
	}
	public void setGroupColspan(int groupColspan) {
		this.groupColspan = groupColspan;
	}
	
	
}
