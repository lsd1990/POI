package com.lsd.excel.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class POIExcelUtil {
	

	public static void exportBaseModelDetail(List<GridHeaderDTO> headerDatas,
			List<GridHeaderGroupDTO> headerGroupDatas, Boolean isAddSum, String reportName, String condition,
			List<?> reportDatas, Workbook workbook, Sheet sheet, int index) throws IOException,IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
			 

		for (int i = 0; i < reportDatas.size(); i++) {
		
			Row row = sheet.createRow(3 + i + index);
			
			Object reportData = reportDatas.get(i);
			for (int j = 0; j < headerDatas.size(); j++) {
				GridHeaderDTO headerData = headerDatas.get(j);

				Cell cell = row.createCell(j);
				
				if (headerData.getMethod() == null) {
					cell.setCellValue(getObjectFormat(i + 1));
				} else {
					Object objValue = headerData.getMethod().invoke(reportData);
					String value = getObjectFormat(objValue);

					if (headerData.getDateFormat() != null) {
						value = getDateFormat(objValue, headerData.getDateFormat());
					}

					if (headerData.getNumberFormat() != null) {
						value = getNumberFormat(objValue, headerData.getNumberFormat());
					}

					if (headerData.getPath() != null && headerData.getPath().endsWith("Rate")) {
						// value = value + "%";
						value = getNumberFormat(objValue, "#,##0.000");
					}

					if (headerData.isRight()) {
						if (value == null || value.equals("")) {
							value = "";
							cell.setCellValue(value);
						} else {
							try {
								cell.setCellValue(Double.valueOf(value.replace(",", "")));
							} catch (Exception e) {
								cell.setCellValue(value);
							}

						}

					} else {
						cell.setCellValue(value);
					}

				}
			}
		}

		
	}
	
	
	public static String getFormat(Number value, DecimalFormat df) {
		if (value == null) {
			return "0.00";
		}
		return df.format(value);
	}
	
	private static String getObjectFormat(Object value){ 
		if (value instanceof Date){
			SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(DATE_TIME_FORMAT.format((Date)value).equals("1899-12-30 00:00")){
				return "";
			}
			return DATE_TIME_FORMAT.format((Date)value);
		}else if (value instanceof Double){
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format((Double)value);
		}else if (value instanceof Float){
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format((Float)value);
		}else if (value instanceof BigDecimal){
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format((BigDecimal) value);
		}else if (value instanceof Integer){
			DecimalFormat df = new DecimalFormat("###0");
			return df.format((Integer) value);
		}else if (value instanceof Boolean){
			return (Boolean)value == true ? "是":"否";
		}else {
			if (value == null){
				return "";
			}else{
				return value.toString();
			} 
		}
	}
	
	private static String getNumberFormat(Object value, String format){
		if (value instanceof Double){
			DecimalFormat df = new DecimalFormat(format);
			return df.format((Double)value);
		}else if (value instanceof Float){
			DecimalFormat df = new DecimalFormat(format);
			return df.format((Float)value);
		}else if (value instanceof BigDecimal){
			DecimalFormat df = new DecimalFormat(format);
			return df.format((BigDecimal) value);
		}else if (value instanceof Integer){
			DecimalFormat df = new DecimalFormat(format);
			return df.format((Integer) value);
		}else {
			if (value == null){
				return "";
			}else{
				return value.toString();
			} 
		}
	}
	
	private static String getDateFormat(Object value, String format){ 
		if (value instanceof Date){
			SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(format);
			if(DATE_TIME_FORMAT.format((Date)value).equals("1899-12-30 00:00")){
				return "";
			}
			return DATE_TIME_FORMAT.format((Date)value);
		}
		return "-";
	}
}
