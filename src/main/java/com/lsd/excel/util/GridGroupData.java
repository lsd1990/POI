package com.lsd.excel.util;

import java.io.Serializable;

public class GridGroupData extends BaseReportData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -814282489843126014L;

	private String groupName;
	private String groupValue;
	private String groupColumnId;
	
	public GridGroupData(){}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupValue() {
		return groupValue;
	}
	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}
	public String getGroupColumnId() {
		return groupColumnId;
	}
	public void setGroupColumnId(String groupColumnId) {
		this.groupColumnId = groupColumnId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridGroupData other = (GridGroupData) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}
	
}
