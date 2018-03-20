package com.lsd.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lsd.excel.model.ReceiveOrder;
import com.lsd.excel.server.service.impl.dao.ReceiveOrderDao;
import com.lsd.excel.util.GridHeaderDTO;
import com.lsd.excel.util.JXLExcelUtil;
import com.lsd.excel.util.POIExcelUtil;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@RestController
public class ExcelController {

	private static final Integer SHEET_SIZE = 100000;
	
	private static final Integer PAGE_SIZE = 10000;
	
	@Resource
	private ReceiveOrderDao receiveOrderDao;
	
	public Integer count() {
		return receiveOrderDao.count();
	}
	
	public List<ReceiveOrder> list(Integer offset, Integer limit) {
		
		List<ReceiveOrder> list = receiveOrderDao.list(offset, limit);
		
		ReceiveOrder receiveOrder = new ReceiveOrder();
		receiveOrder.setBranchNum(1);
		list.add(receiveOrder);
		return list;
	}
	
	@RequestMapping(value = "/export")
	public void export() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException {
//		Integer count = count();
//		
//		int size = count / MAX_COUNT;
//		
//		if (count % MAX_COUNT != 0) {
//			size = size + 1;
//		}
		System.out.println("临时文件路径：" + System.getProperty("java.io.tmpdir"));
		
//		exportBatch(1);
		
		exportBatch();
//		
	}
	
	@RequestMapping(value = "/poi/one")
	public void exportone() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException {
//		
		System.out.println("临时文件路径：" + System.getProperty("java.io.tmpdir"));
		
		exportOne();
		
	}
	
	@RequestMapping(value = "/jxl_export")
	public void jxlExport() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException, WriteException, BiffException {
		
		List<GridHeaderDTO> headerDatas = new ArrayList<GridHeaderDTO>();
		
		Field[] fields = ReceiveOrder.class.getDeclaredFields();
		
		for(int i=0; i<fields.length; i++) {
			
			String filedName = fields[i].getName();
			if(filedName.equals("serialVersionUID")) {
				continue;
			}
			
			GridHeaderDTO gridHeaderDTO = new GridHeaderDTO();
			gridHeaderDTO.setHeader(filedName);
			gridHeaderDTO.setPath(filedName);
			
			String gettter = toGetter(gridHeaderDTO.getPath());
			if ( gettter != null) {
				gridHeaderDTO.setMethod(ReceiveOrder.class.getMethod(gettter));
			}
			
			headerDatas.add(gridHeaderDTO);
			
		}
		
		
		
		for(int i = 0; i<10; i++) {
			File excelFile = new File("/Users/lusudong/Desktop/Download/static/jxk_test"+i+".xls");
	        if (!excelFile.exists()) {
	            excelFile.createNewFile();
	        }
	        /************** 创建工作簿 *************/
	        WritableWorkbook workbook = Workbook.createWorkbook(excelFile);
	        
	        for(int j = 0; j < 10; j++) {
				
				List<ReceiveOrder> list = list( (Integer)( (i * 10  + j) * SHEET_SIZE), SHEET_SIZE );
				System.out.println("开始导出sheet");
				WritableSheet sheet = workbook.createSheet("jxl测试" + j, 0);
				
				JXLExcelUtil.exportBaseModelDetail(headerDatas, null, false, "jxl测试", "123", list, workbook, sheet);
				System.out.println("结束导出sheet");
	        	
	        }
	        
	        //结束
	        workbook.write();
	        workbook.close();//必须关闭流，不然写不进excel
	        
	        FileInputStream in = new FileInputStream(excelFile);
	        byte[] fileContent = new byte[in.available()];
	        in.read(fileContent, 0, in.available());
	        excelFile.deleteOnExit();
	        in.close();
	        
	        System.out.println("结束导出xk_test"+i+".xls");
			
		}
		
//		System.out.println("开始查询");
//		
//		List<ReceiveOrder> list = list(0, 1000000);
//		
//		System.out.println("结束查询");
//		
		
		
		
		
//		
//		System.out.println("结束导出");
	}
	
	private void exportBatch() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		
		List<GridHeaderDTO> headerDatas = new ArrayList<GridHeaderDTO>();
		
		Field[] fields = ReceiveOrder.class.getDeclaredFields();
		
		for(int i=0; i<fields.length; i++) {
			
			String filedName = fields[i].getName();
			if(filedName.equals("serialVersionUID")) {
				continue;
			}
			
			GridHeaderDTO gridHeaderDTO = new GridHeaderDTO();
			gridHeaderDTO.setHeader(filedName);
			gridHeaderDTO.setPath(filedName);
			
			String gettter = toGetter(gridHeaderDTO.getPath());
			if ( gettter != null) {
				gridHeaderDTO.setMethod(ReceiveOrder.class.getMethod(gettter));
			}
			
			headerDatas.add(gridHeaderDTO);
			
		}
		
		ExcelExecutor excelExecutor = new ExcelExecutor(10000, 99999, 10000) {
			
			@Override
			public List<?> loadData(int offset, int limit) {
				return 	list( offset, limit );
			}
		};
		excelExecutor.execute("测试xlxls", "123", headerDatas, false, "/Users/lusudong/Desktop/Download/static/lsd.xlsx");
	}
	
	private void exportBatch(int fileSize) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException {
		String reportName = "测试xlxls";
		String condition = "123";
		boolean isAddSum = true;
		
		List<GridHeaderDTO> headerDatas = new ArrayList<GridHeaderDTO>();
		
		Field[] fields = ReceiveOrder.class.getDeclaredFields();
		
		for(int i=0; i<fields.length; i++) {
			
			String filedName = fields[i].getName();
			if(filedName.equals("serialVersionUID")) {
				continue;
			}
			
			GridHeaderDTO gridHeaderDTO = new GridHeaderDTO();
			gridHeaderDTO.setHeader(filedName);
			gridHeaderDTO.setPath(filedName);
			
			String gettter = toGetter(gridHeaderDTO.getPath());
			if ( gettter != null) {
				gridHeaderDTO.setMethod(ReceiveOrder.class.getMethod(gettter));
			}
			
			headerDatas.add(gridHeaderDTO);
			
		}
		
		for(int i= 0; i< fileSize; i++) {
			
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);  
			
			System.out.println(Calendar.getInstance().getTime() + ": 开始导出"  );
			
			for(int j = 0; j < 10; j++) {
				
				System.out.println(Calendar.getInstance().getTime() + ": 开始导出sheet"  );
				SXSSFSheet sheet = workbook.createSheet();
				
				Row row1 = sheet.createRow(0);
				Cell cell1 = row1.createCell(0);
				cell1.setCellValue(reportName);
				 

				Row row2 = sheet.createRow(1);
				Cell cell2 = row2.createCell(0);
				cell2.setCellValue(condition);
				
				Row row3 = sheet.createRow(2);
				for (int b = 0; b < headerDatas.size(); b++) {
					GridHeaderDTO headerData = headerDatas.get(i);
					
					Cell cell = row3.createCell(i);

					if (headerData.getHeader().equals("")) {
						cell.setCellValue("行号");
					} else {
						cell.setCellValue(headerData.getHeader());
					}
				}
				
				if(i == 0) {
					sheet.addMergedRegion(new CellRangeAddress(
				            0, //first row (0-based)
				            0, //last row  (0-based)
				            0, //first column (0-based)
				            headerDatas.size() - 1  //last column  (0-based)
				    ));
					
					sheet.addMergedRegion(new CellRangeAddress(
				            1, //first row (0-based)
				            1, //last row  (0-based)
				            0, //first column (0-based)
				            headerDatas.size() - 1  //last column  (0-based)
				    ));
				}
				
				for(int a = 0; a<10; a ++) {
					System.out.println(Calendar.getInstance().getTime() + ": 开始查询sheet"  );
					List<ReceiveOrder> list = list( (Integer)( ((i * 10  + j) * 10 + a) * PAGE_SIZE), PAGE_SIZE );
					System.out.println(Calendar.getInstance().getTime() + ": 结束查询sheet"  );
					
					POIExcelUtil.exportBaseModelDetail(headerDatas, null, false, "测试", "123", list, workbook, sheet,  10000 * a);
					
				}
				
				if (isAddSum) {
					
					Row row = sheet.createRow(3 + 100000);
					
					GridHeaderDTO rootGridHeaderDTO = headerDatas.get(0);
					Map<String, Object> sumMap = rootGridHeaderDTO.getMap();
					for (int a = 0; a < headerDatas.size(); a++) {
						GridHeaderDTO headerData = headerDatas.get(a);
						
						Cell cell = row.createCell((short) i);
						
						if (i == 0) {
							cell.setCellValue("总合计");
							continue;
						}

						if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") != null) {
							Object value = sumMap.get(headerData.getPath() + "Sum");
							if (value instanceof String) {
								cell.setCellValue((String) value);
							} else if (value instanceof java.lang.Number) {
								if (headerData.getNumberFormat() != null) {
									cell.setCellValue(getFormat((java.lang.Number)value, new DecimalFormat(headerData.getNumberFormat())));
								} else {
									cell.setCellValue(getObjectFormat(value));
								}
							}
						}
						if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") == null) {
							cell.setCellValue(2 + 100000 + 1);
						}
					}
				}
				
				System.out.println(Calendar.getInstance().getTime() + ": 结束导出sheet"  );
				
			}
			
			OutputStream out = new FileOutputStream("/Users/lusudong/Desktop/Download/static/test_SXSSF"+i+".xlsx");
			
			System.out.println(Calendar.getInstance().getTime() + ": 结束导出"  );

			workbook.write(out);
			workbook.close();
			workbook.dispose();
			out.close();
			
		}
		

	}
	
	
	private void exportOne() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException {
		String reportName = "测试xlxls";
		String condition = "123";
		boolean isAddSum = true;
		
		List<GridHeaderDTO> headerDatas = new ArrayList<GridHeaderDTO>();
		
		Field[] fields = ReceiveOrder.class.getDeclaredFields();
		
		for(int i=0; i<fields.length; i++) {
			
			String filedName = fields[i].getName();
			if(filedName.equals("serialVersionUID")) {
				continue;
			}
			
			GridHeaderDTO gridHeaderDTO = new GridHeaderDTO();
			gridHeaderDTO.setHeader(filedName);
			gridHeaderDTO.setPath(filedName);
			
			String gettter = toGetter(gridHeaderDTO.getPath());
			if ( gettter != null) {
				gridHeaderDTO.setMethod(ReceiveOrder.class.getMethod(gettter));
			}
			
			headerDatas.add(gridHeaderDTO);
			
		}
		
			
		SXSSFWorkbook workbook = new SXSSFWorkbook(10000);  
		
		System.out.println(Calendar.getInstance().getTime() + ": 开始导出"  );
		
			
		System.out.println(Calendar.getInstance().getTime() + ": 开始导出sheet"  );
		SXSSFSheet sheet = workbook.createSheet();
		
		Row row1 = sheet.createRow(0);
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(reportName);
		 

		Row row2 = sheet.createRow(1);
		Cell cell2 = row2.createCell(0);
		cell2.setCellValue(condition);
		
		Row row3 = sheet.createRow(2);
		for (int b = 0; b < headerDatas.size(); b++) {
			GridHeaderDTO headerData = headerDatas.get(b);
			
			Cell cell = row3.createCell(b);

			if (headerData.getHeader().equals("")) {
				cell.setCellValue("行号");
			} else {
				cell.setCellValue(headerData.getHeader());
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(
	            0, //first row (0-based)
	            0, //last row  (0-based)
	            0, //first column (0-based)
	            headerDatas.size() - 1  //last column  (0-based)
	    ));
		
		sheet.addMergedRegion(new CellRangeAddress(
	            1, //first row (0-based)
	            1, //last row  (0-based)
	            0, //first column (0-based)
	            headerDatas.size() - 1  //last column  (0-based)
	    ));
		
		for(int a = 0; a<100; a ++) {
			System.out.println(Calendar.getInstance().getTime() + ": 开始查询sheet"  );
			List<ReceiveOrder> list = list( (Integer)(a * PAGE_SIZE), PAGE_SIZE );
			System.out.println(Calendar.getInstance().getTime() + ": 结束查询sheet"  );
			
			POIExcelUtil.exportBaseModelDetail(headerDatas, null, false, "测试", "123", list, workbook, sheet,  10000 * a );
			
		}
		
		if (isAddSum) {
			
			Row row = sheet.createRow(3 + 1000000);
			
			GridHeaderDTO rootGridHeaderDTO = headerDatas.get(0);
			Map<String, Object> sumMap = rootGridHeaderDTO.getMap();
			for (int a = 0; a < headerDatas.size(); a++) {
				GridHeaderDTO headerData = headerDatas.get(a);
				
				Cell cell = row.createCell((short) a);
				
				if (a == 0) {
					cell.setCellValue("总合计");
					continue;
				}

				if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") != null) {
					Object value = sumMap.get(headerData.getPath() + "Sum");
					if (value instanceof String) {
						cell.setCellValue((String) value);
					} else if (value instanceof java.lang.Number) {
						if (headerData.getNumberFormat() != null) {
							cell.setCellValue(getFormat((java.lang.Number)value, new DecimalFormat(headerData.getNumberFormat())));
						} else {
							cell.setCellValue(getObjectFormat(value));
						}
					}
				}
				if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") == null) {
					cell.setCellValue(2 + 1000000 + 1);
				}
			}
			
			System.out.println(Calendar.getInstance().getTime() + ": 结束导出sheet"  );
			
		}
		
		OutputStream out = new FileOutputStream("/Users/lusudong/Desktop/Download/static/test_SXSSF_ONE_SHEET.xlsx");
		
		System.out.println(Calendar.getInstance().getTime() + ": 结束导出"  );

		workbook.write(out);
		workbook.close();
		workbook.dispose();
		out.close();
			
		

	}
	
	public static String toGetter(String fieldname) {

		if (fieldname == null || fieldname.length() == 0) {
			return null;
		}
		if (fieldname.length() > 2) {
			String second = fieldname.substring(1, 2);
			if (second.equals(second.toUpperCase())) {
				return new StringBuffer("get").append(fieldname).toString();
			}
		}

		fieldname = new StringBuffer("get").append(fieldname.substring(0, 1).toUpperCase())
				.append(fieldname.substring(1)).toString();

		return fieldname;
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
