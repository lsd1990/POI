package com.lsd.excel.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JXLExcelUtil {
    
	
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

	public static void exportBaseModelDetail(
			List<GridHeaderDTO> headerDatas, List<GridHeaderGroupDTO> headerGroupDatas, Boolean isAddSum,
			String reportName, String condition, List<?> reportDatas, WritableWorkbook workbook, WritableSheet sheet) throws IOException, WriteException, BiffException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
    
        /***************** 设置单元格字体 ****************/
        WritableFont NormalFont = new WritableFont(WritableFont.createFont("宋体"), 12);
        WritableFont BoldFont = new WritableFont(WritableFont.createFont("宋体"), 14, WritableFont.BOLD);
        WritableFont tableFont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
        NumberFormat currencyFormat = new NumberFormat("#,##0.00#");

        /************** 创建工作表 *************/
        if (reportName == null || reportName.length() == 0) {
        		reportName = " ";
		}

		/***************** 设置单元格字体 ****************/
		NumberFormat currencyIntFormat = new NumberFormat("#,##0");
		NumberFormat currencyFourFormat = new NumberFormat("#,##0.00##");

        /***************** 以下设置几种格式的单元格 ****************/
        // 用于标题
        WritableCellFormat wcf_title = new WritableCellFormat(BoldFont);
        wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_title.setAlignment(Alignment.CENTRE); // 水平对齐
        wcf_title.setWrap(true); // 是否换行

        // 用于表格标题
        WritableCellFormat wcf_tabletitle = new WritableCellFormat(tableFont);
        wcf_tabletitle.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_tabletitle.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_tabletitle.setAlignment(Alignment.CENTRE); // 水平对齐
        wcf_tabletitle.setBackground(Colour.GREY_25_PERCENT);
        wcf_tabletitle.setWrap(true); // 是否换行
        
        // 用于表格标题
        WritableCellFormat wcf_lefttitle = new WritableCellFormat(tableFont);
        wcf_lefttitle.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_lefttitle.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_lefttitle.setAlignment(Alignment.LEFT); // 水平对齐
        wcf_lefttitle.setBackground(Colour.GREY_25_PERCENT);
        wcf_lefttitle.setWrap(true); // 是否换行

        // 用于正文左
        WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
        wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_left.setAlignment(Alignment.LEFT);
        wcf_left.setWrap(true); // 是否换行

        WritableCellFormat wcf_left_red = new WritableCellFormat(NormalFont);
        wcf_left_red.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_left_red.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_left_red.setAlignment(Alignment.LEFT);
        wcf_left_red.setWrap(true); // 是否换行

        // 用于正文中
        WritableCellFormat wcf_center = new WritableCellFormat(NormalFont);
        wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_center.setAlignment(Alignment.CENTRE);
        wcf_center.setWrap(true); // 是否换行
        
        // 用于正文右
        WritableCellFormat wcf_Right = new WritableCellFormat(NormalFont, currencyFormat);
        wcf_Right.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_Right.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_Right.setAlignment(Alignment.RIGHT);
        wcf_Right.setWrap(true); // 是否换行
        
        
        WritableCellFormat wcf_currency_total = new WritableCellFormat(NormalFont, currencyFormat);
        wcf_currency_total.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        wcf_currency_total.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        wcf_currency_total.setAlignment(Alignment.LEFT);
        wcf_currency_total.setBackground(Colour.GREY_25_PERCENT);
        wcf_currency_total.setWrap(false); // 是否换行
        
		WritableCellFormat wcf_currency_total_four = new WritableCellFormat(NormalFont, currencyFourFormat);
		wcf_currency_total_four.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
		wcf_currency_total_four.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
		wcf_currency_total_four.setAlignment(Alignment.RIGHT);
		wcf_currency_total_four.setBackground(Colour.GREY_25_PERCENT);
		wcf_currency_total_four.setWrap(true); // 是否换行
		
		WritableCellFormat wcf_currency_total_int = new WritableCellFormat(NormalFont, currencyIntFormat);
		wcf_currency_total_int.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
		wcf_currency_total_int.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
		wcf_currency_total_int.setAlignment(Alignment.RIGHT);
		wcf_currency_total_int.setBackground(Colour.GREY_25_PERCENT);
		wcf_currency_total_int.setWrap(true); // 是否换行
        
		
		sheet.getSettings().setSelected(true);
		
		// 设置页边距
		sheet.getSettings().setRightMargin(0.5);
		
		
		for (int i = 0; i < headerDatas.size(); i++) {
			sheet.setColumnView(i, headerDatas.get(i).getWidth()/5); // 第1列
		}
		
		// 设置行高
		sheet.setRowView(0, 600, false);
		
		//报表头       
		sheet.mergeCells(0, 0, headerDatas.size()-1, 0);
		sheet.addCell(new Label(0, 0, reportName, wcf_title));
		sheet.mergeCells(0, 1, headerDatas.size()-1, 1);
		
		//
		Label labelCodition = new Label(0, 1, condition, wcf_left);
		sheet.addCell(labelCodition);
		if (reportName.equals("日营业统计") || reportName.equals("月营业统计")) {
			sheet.setRowView(1, 1500, false);
		}
		
		int count = 0;
		if (headerGroupDatas != null && headerGroupDatas.size() > 0) {
			count = count + 1;
			for (int i = 0; i < headerGroupDatas.size(); i++) {
				GridHeaderGroupDTO headerGroupData = headerGroupDatas.get(i);
				sheet.mergeCells(headerGroupData.getGroupColumn(), 2, headerGroupData.getGroupColumn()+headerGroupData.getGroupColspan()-1, 2);
				sheet.addCell(new Label(headerGroupData.getGroupColumn(), 2, headerGroupData.getGroupName(), wcf_tabletitle));
			}
		}
		for (int i = 0; i < headerDatas.size(); i++) {
			GridHeaderDTO headerData = headerDatas.get(i);
			
			if (headerData.getHeader().equals("")) {
				sheet.addCell(new Label(i, 2 + count, "行号", wcf_tabletitle));
			}else {
				sheet.addCell(new Label(i, 2 + count, headerData.getHeader(), wcf_tabletitle));
			}
		}
		
		Integer maxCount = 0;
		
		for (int i = 0; i < reportDatas.size(); i++) {
			
			Object reportData = reportDatas.get(i);
			for (int j = 0; j < headerDatas.size(); j++) {
				GridHeaderDTO headerData = headerDatas.get(j);
				
				if (headerData.getMethod() == null) {
					sheet.addCell(new Label(j, 3 + count + i, getObjectFormat(i + 1), wcf_tabletitle));
				} else{
					Object objValue = headerData.getMethod().invoke(reportData);
					String value = getObjectFormat(objValue);
					
					if (headerData.getDateFormat() != null) {
						value = getDateFormat(objValue, headerData.getDateFormat());
					}
					
					if (headerData.getNumberFormat() != null) {
						value = getNumberFormat(objValue, headerData.getNumberFormat());
					}
					
					if(headerData.getPath() != null && headerData.getPath().endsWith("Rate")){
//						value = value + "%";
						value = getNumberFormat(objValue, "#,##0.000");
					}
					
					if (headerData.isRight()) {
						if(value == null || value.equals("")){
							value = "";
							sheet.addCell(new Label(j, 3 + count + i, value, wcf_Right));
						}else {
							try {
								WritableCell cell = new jxl.write.Number(j, 3 + count + i, Double.valueOf(value.replace(",", "")), wcf_Right);
								sheet.addCell(cell);
							} catch (Exception e) {
								sheet.addCell(new Label(j, 3 + count + i, value, wcf_Right));
							}

						}
				
					}else{
						sheet.addCell(new Label(j, 3 + count + i, value, wcf_left));
					}
					
				}
			}
			maxCount ++;
		}
		
		if (isAddSum != null && isAddSum) {
			GridHeaderDTO rootGridHeaderDTO = headerDatas.get(0);
			Map<String, Object> sumMap = rootGridHeaderDTO.getMap();
			for (int i = 0; i < headerDatas.size(); i++) {
				GridHeaderDTO headerData = headerDatas.get(i);
				if (i == 0) {
					sheet.addCell(new Label(i, 2 + count + maxCount + 1, "总合计", wcf_currency_total));
					continue;
				}
				
				if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") != null) {
					Object value = sumMap.get(headerData.getPath() + "Sum");
					WritableCell cell = null;
					if (value instanceof String) {
						cell = new Label(i, 2 + count + maxCount + 1, (String) value, wcf_Right);
					}else if(value instanceof java.lang.Number){
						if(headerData.getNumberFormat() != null){
							cell = new Label(i, 2 + count + maxCount + 1, AppUtil.getFormat((java.lang.Number)value, new DecimalFormat(headerData.getNumberFormat())) , wcf_Right);
						}else{
							cell = new Label(i, 2 + count + maxCount + 1,  getObjectFormat(value), wcf_Right);
						}												
					}					
					sheet.addCell(cell);
				}				
				if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") == null) {
					sheet.addCell(new Label(i, 2 + count + maxCount + 1, "" , wcf_Right));
				}				
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
	
	
	 

		
}
