package com.lsd.excel.util;

import java.text.DecimalFormat;
import java.util.UUID;

public class AppUtil {

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
	
	public static String getFormat(Number value, DecimalFormat df) {
		if (value == null) {
			return "0.00";
		}
		return df.format(value);
	}
}
