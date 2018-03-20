package com.lsd.excel.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.lsd.excel.util.GridHeaderDTO;
import com.lsd.excel.util.GridHeaderGroupDTO;

public abstract class ExcelExecutor {

	private Integer rowAccessWindowSize = 10000; // 多少行数，数据flush到磁盘

	private Integer dataCount; // 总的导出数据
	private Integer sheetCount; // sheet一页数据
	private Integer queryLimit; // 每次查询数量

	public ExcelExecutor(Integer sheetCount,  Integer dataCount, Integer queryLimit) {
		this.dataCount = dataCount;
		this.queryLimit = queryLimit;

		this.sheetCount = sheetCount;
	}

	public void execute(String reportName, String condition, List<GridHeaderDTO> headerDatas, boolean isAddSum,
			String filePath)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

		OutputStream out = null;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(rowAccessWindowSize);

			Integer sheetSize = dataCount / sheetCount;
			if (dataCount % sheetCount != 0) {
				sheetSize = sheetSize + 1;
			}

			for (int j = 0; j < sheetSize; j++) {

				SXSSFSheet sheet = workbook.createSheet(reportName + j);

				Row row1 = sheet.createRow(0);
				Cell cell1 = row1.createCell(0);
				cell1.setCellValue(reportName);

				Row row2 = sheet.createRow(1);
				Cell cell2 = row2.createCell(0);
				cell2.setCellValue(condition);

				if (j == 0) {
					sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
							0, // last row (0-based)
							0, // first column (0-based)
							headerDatas.size() - 1 // last column (0-based)
					));

					sheet.addMergedRegion(new CellRangeAddress(1, // first row (0-based)
							1, // last row (0-based)
							0, // first column (0-based)
							headerDatas.size() - 1 // last column (0-based)
					));
				}

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

				Integer queryCount = sheetCount / queryLimit;
				if (sheetCount % queryLimit != 0) {
					queryCount = queryCount + 1;
				}

				Integer limit = null;
				for (int a = 0; a < queryCount; a++) {
					
					if(j == sheetSize - 1 && a == queryCount - 1) {
						limit = dataCount - (sheetSize - 1) * sheetCount;
					}else {
						limit = queryLimit;
					}

					List<?> list = loadData((Integer) ((j * queryCount + a) * queryLimit), limit);
					if (list.size() == 0) {
						break;
					}

					exportBaseModelDetail(headerDatas, null, list, workbook, sheet, a * queryLimit);

				}

				if (isAddSum) {

					Integer lastRwow = null;
					if (j != sheetSize - 1) {
						lastRwow = 3 + sheetCount;
					} else {
						lastRwow = 3 + dataCount - (sheetSize - 1) * sheetCount;
					}

					Row row = sheet.createRow(lastRwow);

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
									cell.setCellValue(getFormat((java.lang.Number) value,
											new DecimalFormat(headerData.getNumberFormat())));
								} else {
									cell.setCellValue(getObjectFormat(value));
								}
							}
						}
						if (sumMap != null && sumMap.get(headerData.getPath() + "Sum") == null) {
							cell.setCellValue(lastRwow);
						}
					}
				}
			}

			out = new FileOutputStream(filePath);
			workbook.write(out);

		} catch (Exception e) {
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook.dispose();
			}
			if (out != null) {
				out.close();
			}
		}

	}

	private static String getFormat(Number value, DecimalFormat df) {
		if (value == null) {
			return "0.00";
		}
		return df.format(value);
	}

	private static String getObjectFormat(Object value) {
		if (value instanceof Date) {
			SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (DATE_TIME_FORMAT.format((Date) value).equals("1899-12-30 00:00")) {
				return "";
			}
			return DATE_TIME_FORMAT.format((Date) value);
		} else if (value instanceof Double) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format((Double) value);
		} else if (value instanceof Float) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format((Float) value);
		} else if (value instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format((BigDecimal) value);
		} else if (value instanceof Integer) {
			DecimalFormat df = new DecimalFormat("###0");
			return df.format((Integer) value);
		} else if (value instanceof Boolean) {
			return (Boolean) value == true ? "是" : "否";
		} else {
			if (value == null) {
				return "";
			} else {
				return value.toString();
			}
		}
	}

	private void exportBaseModelDetail(List<GridHeaderDTO> headerDatas, List<GridHeaderGroupDTO> headerGroupDatas,
			List<?> reportDatas, Workbook workbook, Sheet sheet, int index)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

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

	private String getNumberFormat(Object value, String format) {
		if (value instanceof Double) {
			DecimalFormat df = new DecimalFormat(format);
			return df.format((Double) value);
		} else if (value instanceof Float) {
			DecimalFormat df = new DecimalFormat(format);
			return df.format((Float) value);
		} else if (value instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat(format);
			return df.format((BigDecimal) value);
		} else if (value instanceof Integer) {
			DecimalFormat df = new DecimalFormat(format);
			return df.format((Integer) value);
		} else {
			if (value == null) {
				return "";
			} else {
				return value.toString();
			}
		}
	}

	private String getDateFormat(Object value, String format) {
		if (value instanceof Date) {
			SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(format);
			if (DATE_TIME_FORMAT.format((Date) value).equals("1899-12-30 00:00")) {
				return "";
			}
			return DATE_TIME_FORMAT.format((Date) value);
		}
		return "-";
	}

	public abstract List<?> loadData(int offset, int limit);
}
