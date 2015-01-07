package com.datalook.excel.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.datalook.excel.annotation.OEMColumn.ReadWriteStrategy;
import com.datalook.excel.core.holder.ClassHolder;
import com.datalook.excel.core.holder.ColumnInfo;
import com.datalook.excel.core.holder.SheetInfo;
import com.datalook.exceltool.exception.ExcelConstructException;

public class OEM {
	public final static int OFFICE03 = 3;
	public final static int OFFICE07 = 7;

	private static java.text.NumberFormat nf = java.text.NumberFormat.getInstance();

	private static String separator = java.io.File.separator;

	static {
		nf.setGroupingUsed(false);
	}

	public static <E> void generateExecl(List<E> list, String path, String name) throws IOException {
		FileOutputStream out;
		if (path.endsWith(separator)) {
			out = new FileOutputStream(path + name);
		} else {
			out = new FileOutputStream(path + separator + name);
		}
		if (name.endsWith(".xls")) {
			generateExecl(list, out, OFFICE03);
		} else if (name.endsWith(".xlsx")) {
			generateExecl(list, out, OFFICE07);
		}
	}

	public static <E> void generateExecl(List<E> list, OutputStream out, int officeVersion) throws IOException {
		if (list == null || list.size() == 0) {
			throw new RuntimeException("list为空");
		}
		Class<? extends Object> transformClass = list.get(0).getClass();

		SheetInfo si = null;
		try {
			si = ClassHolder.getSheetInfo(transformClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("注释编写异常");
		}

		Workbook wb = null;
		switch (officeVersion) {
		case OFFICE03:
			wb = new HSSFWorkbook();
			break;
		case OFFICE07:
			wb = new SXSSFWorkbook(1000);
			break;
		default:
			throw new RuntimeException("传入office版本号有误");
		}

		Sheet sheet = null;
		sheet = wb.createSheet(si.sheetName);

		CellStyle dateStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		dateStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));

		titleToExecl(sheet, si);

		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + si.startRowNumber + 1);
			listToExecl(list.get(i), wb, sheet, row, si.columns, dateStyle);
		}

		wb.write(out);
		out.close();
	}
	
	public static <E> void generateExeclTitle(Class<E> transformClass,OutputStream out,int officeVersion) throws IOException{
		SheetInfo si = null;
		try {
			si = ClassHolder.getSheetInfo(transformClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("注释编写异常");
		}

		Workbook wb = null;
		switch (officeVersion) {
		case OFFICE03:
			wb = new HSSFWorkbook();
			break;
		case OFFICE07:
			wb = new SXSSFWorkbook(1000);
			break;
		default:
			throw new RuntimeException("传入office版本号有误");
		}

		Sheet sheet = null;
		sheet = wb.createSheet(si.sheetName);

		CellStyle dateStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		dateStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));

		titleToExecl(sheet, si);

		wb.write(out);
		out.close();
	}

	private static void titleToExecl(Sheet sheet, SheetInfo si) {
		Row startRow = sheet.createRow(si.startRowNumber);

		int width = columnToExcel(sheet, startRow, si.columns);

		if (si.startRowNumber >= 1) {
			sheet.createRow(0).createCell(0);
			CellRangeAddress cra = new CellRangeAddress(0, si.startRowNumber - 1, 0, width);
			sheet.addMergedRegion(cra);
			sheet.getRow(0).getCell(0).setCellValue(si.title);
		}
	}

	private static int columnToExcel(Sheet sheet, Row startRow, List<ColumnInfo> cis) {
		int width = 0;

		for (ColumnInfo ci : cis) {
			width = Math.max(width, ci.location);
			if (ci.location != -1) {
				Cell cell = startRow.createCell(ci.location);
				cell.setCellValue(ci.title);
				sheet.autoSizeColumn(ci.location);
			} else {
				width = Math.max(width, columnToExcel(sheet, startRow, ci.insideColumns));
			}
		}
		return width;
	}

	private static void listToExecl(Object object, Workbook wb, Sheet sheet, Row row, List<ColumnInfo> cis, CellStyle dateStyle) {

		field: for (ColumnInfo ci : cis) {
			if (ci.strategy == ReadWriteStrategy.writeOnly || ci.strategy == ReadWriteStrategy.readAndWrite) {
				try {
					if (ci.location >= 0) {
						Cell cell = row.createCell(ci.location);
						Object o = ci.field.get(object);
						if (o == null) {
							cell.setCellValue("");
							continue field;
						} else if (o instanceof String) {
							if (ci.map) {
								if (ci.mapJsonObject.containsKey(o)) {
									cell.setCellValue(ci.mapJsonObject.getString(String.valueOf(o)));
									continue field;
								} else {
									cell.setCellValue(String.valueOf(o));
									continue field;
								}
							} else {
								cell.setCellValue((String) o);
								continue field;
							}
						} else if (o instanceof StringBuffer) {
							cell.setCellValue(((StringBuffer) o).toString());
							continue field;
						} else if (o instanceof Integer) {
							if (ci.map) {
								if (ci.mapJsonObject.containsKey(o)) {
									cell.setCellValue(ci.mapJsonObject.getString(String.valueOf(o)));
									continue field;
								} else {
									cell.setCellValue((Integer) o);
									continue field;
								}
							} else {
								cell.setCellValue((Integer) o);
								continue field;
							}
						} else if (o instanceof Long) {
							cell.setCellValue((Long) o);
							continue field;
						} else if (o instanceof Short) {
							cell.setCellValue((Short) o);
							continue field;
						} else if (o instanceof Float) {
							cell.setCellValue((Float) o);
							continue field;
						} else if (o instanceof Double) {
							cell.setCellValue((Double) o);
							continue field;
						} else if (o instanceof Boolean) {
							if (ci.map) {
								if (ci.mapJsonObject.containsKey("true") && (Boolean) o) {
									cell.setCellValue(ci.mapJsonObject.getString("true"));
									continue field;
								} else if (ci.mapJsonObject.containsKey("false") && !(Boolean) o) {
									cell.setCellValue(ci.mapJsonObject.getString("false"));
									continue field;
								}
							} else {
								cell.setCellValue((Boolean) o);
								continue field;
							}
						} else if (o instanceof Date) {
							if (ci.map) {
								String datestr = ci.sdf.format((Date) o);
								cell.setCellValue(datestr);
								sheet.setColumnWidth(ci.location, datestr.length() * 260);
								continue field;
							} else {
								sheet.setColumnWidth(ci.location, 4000);
								cell.setCellStyle(dateStyle);
								cell.setCellValue((Date) o);
								continue field;
							}
						}
					} else {
						listToExecl(FieldUtils.readDeclaredField(object, ci.field.getName(), true), wb, sheet, row, ci.insideColumns, dateStyle);
					}
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static <T> List<T> readExecl(Class<T> clazz, String path, String name) throws IOException {
		FileInputStream in;
		if (path.endsWith(separator)) {
			in = new FileInputStream(path + name);
		} else {
			in = new FileInputStream(path + separator + name);
		}
		if (name.endsWith(".xls")) {
			return readExecl(clazz, in, OFFICE03);
		} else if (name.endsWith(".xlsx")) {
			return readExecl(clazz, in, OFFICE07);
		} else {
			return null;
		}
	}

	public static <T> List<T> readExecl(Class<T> clazz, InputStream in, int officeVersion) throws IOException {
		Workbook wb = null;
		switch (officeVersion) {
		case OFFICE03:
			wb = new HSSFWorkbook(in);
			break;
		case OFFICE07:
			wb = new XSSFWorkbook(in);
			break;
		default:
			throw new RuntimeException("传入版本号有误");
		}

		SheetInfo si = null;
		try {
			si = ClassHolder.getSheetInfo(clazz);
		} catch (Exception e) {
			throw new RuntimeException("注释编写异常");
		}
		Sheet sheet = wb.getSheet(si.sheetName);
		;

		ArrayList<T> list = new ArrayList<T>();
		int startRowNumber = si.startRowNumber;

		for (int i = startRowNumber + 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			T t = null;
			try {
				t = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			rowToObject(row, t, si.columns);
			list.add(t);
		}
		return list;
	}

	private static void rowToObject(Row row, Object o, List<ColumnInfo> cis) {
		rows: for (ColumnInfo ci : cis) {
			if (ci.strategy == ReadWriteStrategy.readOnly || ci.strategy == ReadWriteStrategy.readAndWrite) {

				try {
					if (ci.location < 0) {
						Object insideo = ci.field.getType().newInstance();
						rowToObject(row, insideo, ci.insideColumns);
						FieldUtils.writeDeclaredField(o, ci.field.getName(), insideo, true);
						continue rows;
					}
					Cell cell = row.getCell(ci.location);
					if (cell == null) {
						continue rows;
					}
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						continue rows;
					case Cell.CELL_TYPE_BOOLEAN:
						FieldUtils.writeDeclaredField(o, ci.field.getName(), cell.getBooleanCellValue(), true);
						continue rows;
					case Cell.CELL_TYPE_STRING:
						if (StringUtils.isBlank(cell.getStringCellValue())) {
							continue rows;
						}
						if (ci.map) {
							if (ci.field.getType().equals(String.class)) {
								String info = ci.getKey(cell.getStringCellValue());
								if (info != null) {
									FieldUtils.writeDeclaredField(o, ci.field.getName(), info, true);
									continue rows;
								} else {
									throw new RuntimeException("map映射中，" + cell.getStringCellValue() + "不在mapString的value列中");
								}
							} else if (ci.field.getType().equals(Integer.class)) {
								Integer info = Integer.valueOf(ci.getKey(cell.getStringCellValue().toString()));
								if (info != null) {
									FieldUtils.writeDeclaredField(o, ci.field.getName(), info, true);
									continue rows;
								} else {
									throw new RuntimeException("map映射中，" + cell.getStringCellValue() + "不在mapString的value列中");
								}
							} else if (ci.field.getType().equals(Boolean.class)) {
								Boolean info = Boolean.valueOf(ci.getKey(cell.getStringCellValue()));
								if (info != null) {
									FieldUtils.writeDeclaredField(o, ci.field.getName(), info, true);
									continue rows;
								} else {
									throw new RuntimeException("map映射中，" + cell.getStringCellValue() + "不在mapString的value列中");
								}
							} else if (ci.field.getType().equals(Date.class)) {
								Date info = ci.sdf.parse(cell.getStringCellValue());
								if (info != null) {
									FieldUtils.writeDeclaredField(o, ci.field.getName(), info, true);
									continue rows;
								} else {
									throw new RuntimeException("日期映射中，" + cell.getStringCellValue() + "无法用" + ci.simpleDateFormatString + "格式解析");
								}
							} else if (ci.field.getType().equals(Timestamp.class)) {
								Timestamp info = new Timestamp(ci.sdf.parse(cell.getStringCellValue()).getTime());
								if (info != null) {
									FieldUtils.writeDeclaredField(o, ci.field.getName(), info, true);
									continue rows;
								} else {
									throw new RuntimeException("日期映射中，" + cell.getStringCellValue() + "无法用" + ci.simpleDateFormatString + "格式解析");
								}
							} else {
								throw new ExcelConstructException("map类型错误");
							}
						} else {
							if (ci.field.getType().equals(String.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), cell.getStringCellValue(), true);
								continue rows;
							} else if (ci.field.getType().equals(StringBuffer.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), new StringBuffer(cell.getStringCellValue()), true);
								continue rows;
							} else if (ci.field.getType().equals(Boolean.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), cell.getBooleanCellValue(), true);
								continue rows;
							} else if (ci.field.getType().equals(Integer.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), Integer.valueOf(cell.getStringCellValue()), true);
								continue rows;
							} else if (ci.field.getType().equals(Long.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), Long.valueOf(cell.getStringCellValue()), true);
								continue rows;
							} else if (ci.field.getType().equals(Double.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), Double.valueOf(cell.getStringCellValue()), true);
								continue rows;
							} else if (ci.field.getType().equals(Short.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), Short.valueOf(cell.getStringCellValue()), true);
								continue rows;
							} else if (ci.field.getType().equals(Float.class)) {
								FieldUtils.writeDeclaredField(o, ci.field.getName(), Float.valueOf(cell.getStringCellValue()), true);
								continue rows;
							}
							throw new RuntimeException(ci.field.getType().getName() + "  " + ci.field.getName() + "的参数类型不是 Date Boolean String StringBuffer Long Short Float Double Integer");
						}

					case Cell.CELL_TYPE_NUMERIC:
						if (ci.field.getType().equals(Integer.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Double(cell.getNumericCellValue()).intValue(), true);
							continue rows;
						} else if (ci.field.getType().equals(Float.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Double(cell.getNumericCellValue()).floatValue(), true);
							continue rows;
						} else if (ci.field.getType().equals(Double.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Double(cell.getNumericCellValue()), true);
							continue rows;
						} else if (ci.field.getType().equals(Long.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Double(cell.getNumericCellValue()).longValue(), true);
							continue rows;
						} else if (ci.field.getType().equals(Short.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Double(cell.getNumericCellValue()).shortValue(), true);
							continue rows;
						} else if (ci.field.getType().equals(Date.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Date(cell.getDateCellValue().getTime()), true);
							continue rows;
						} else if (ci.field.getType().equals(Timestamp.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), new Timestamp(cell.getDateCellValue().getTime()), true);
							continue rows;
						} else if (ci.field.getType().equals(String.class)) {
							FieldUtils.writeDeclaredField(o, ci.field.getName(), nf.format(cell.getNumericCellValue()), true);
							continue rows;
						}
						throw new RuntimeException(ci.field.getType().getName() + "  " + ci.field.getName() + "的参数类型不是Integer Float Double Long Short Date String");
					default:
						throw new RuntimeException("excel表格中出现无法识别的类型,类型参数为cell.getCellType()（非String Numeric Boolean）");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(new Boolean(true).toString());
		System.out.println(Boolean.valueOf("falses"));
	}

}
