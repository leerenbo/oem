package com.datalook.exceltool.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.SheetCfg;
import com.datalook.exceltool.core.reflect.ClassReflectInfoHelper;
import com.datalook.exceltool.core.reflect.ClassReflectInfos;
import com.datalook.exceltool.core.reflect.ReflectInfo;
import com.datalook.exceltool.exception.ExcelConstructException;
import com.datalook.exceltool.exception.ExcelNullListException;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class OEMContext {

	public final static int OFFICE03=3;
	public final static int OFFICE07=7;
	
	private static java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
	
	static {
		nf.setGroupingUsed(false);  
	}
	
	/**
	 * 
	 * 功能描述：根据list生成Excel，存在path路径下的name文件中
	 * 时间：2014年7月25日
	 * @author ：lirenbo
	 * @param list	需要转换为Excel的泛型List
	 * @param path	本地文件路径
	 * @param name	文件名，带后缀，自动使用office03或07
	 * @throws ExcelNullListException
	 * @throws ExcelWrongAnnotationException
	 * @throws IOException
	 */
	public static <E> void generateExecl(List<E> list,String path,String name) throws ExcelNullListException, ExcelWrongAnnotationException, IOException{
		FileOutputStream out;
		if(path.endsWith("\\")){
			out  = new FileOutputStream(path+name);			
		}else{
			out  = new FileOutputStream(path+"\\"+name);
		}
		if(name.endsWith(".xls")){
			generateExecl(list, out,OFFICE03);			
		}else if(name.endsWith(".xlsx")){
			generateExecl(list, out, OFFICE07);
		}
	}
	private static int titleToExecl(ClassReflectInfos classReflectInfos,Row startRow,Sheet sheet,SheetCfg sheetCfg){
		int width=0;
		int inwidth = 0;
		titile:for (ReflectInfo reflectInfo:classReflectInfos.reflectInfoList) {
			for(ColumnCfg columnCfg:reflectInfo.getAnnotationGets()){
				if(columnCfg.sheetId()==-1||columnCfg.sheetId()==sheetCfg.sheetId()){
					if(columnCfg.location()==-1){
						inwidth=titleToExecl(reflectInfo.getClassReflectInfos(), startRow, sheet, sheetCfg);
						continue titile;
					}
					width=Math.max(width, columnCfg.location());
					Cell cell=startRow.createCell(columnCfg.location());
					cell.setCellValue(columnCfg.name());
					
					if(!reflectInfo.getMethodGet().getReturnType().equals(Date.class)){
						sheet.autoSizeColumn(columnCfg.location());
					}
				}
			}
		}
		return Math.max(width, inwidth);
	}
	
	private static void classReflectToExecl(ClassReflectInfos classReflectInfos,Object object,Workbook wb,Sheet sheet,Row row,SheetCfg sheetCfg, CellStyle dateStyle) throws ExcelWrongAnnotationException{
		if(classReflectInfos.reflectInfoList==null){
			throw new ExcelWrongAnnotationException("请将location=-1的类，标记在属性");
		}
		
		
		field:for (ReflectInfo reflectInfo:classReflectInfos.reflectInfoList) {
			for(ColumnCfg columnCfg:reflectInfo.getAnnotationGets()){
				if(columnCfg.sheetId()==-1||columnCfg.sheetId()==sheetCfg.sheetId()){
					try {
						if(columnCfg.location()==-1){
							classReflectToExecl(reflectInfo.getClassReflectInfos(), reflectInfo.getMethodGet().invoke(object, null), wb, sheet, row,sheetCfg,dateStyle);
							continue field;
						}
						Cell cell=row.createCell(columnCfg.location());
						Object o=reflectInfo.getMethodGet().invoke(object, null);
						if(o==null){
							cell.setCellValue("");
							continue field;
						}else if(o instanceof String){
							if(columnCfg.map()){
								HashMap<String, String> map=reflectInfo.getMap();
								if(map.containsKey((String)o)){
									cell.setCellValue(map.get((String)o));
									continue field;
								}else{
									cell.setCellValue((String)o);
									continue field;
								}
							}else{
								cell.setCellValue((String)o);
								continue field;
							}
						}
						else if(o instanceof StringBuffer){
							cell.setCellValue(((StringBuffer)o).toString());
							continue field;
						}
						else if(o instanceof Integer){
							if(columnCfg.map()){
								HashMap<String, String> map=reflectInfo.getMap();
								if(map.containsKey(((Integer)o).toString())){
									cell.setCellValue(map.get(((Integer)o).toString()));
									continue field;
								}else{
									cell.setCellValue((Integer)o);
									continue field;
								}
							}else{
								cell.setCellValue((Integer)o);
								continue field;
							}
						}
						else if(o instanceof Long){
							cell.setCellValue((Long)o);
							continue field;
						}
						else if(o instanceof Short){
							cell.setCellValue((Short)o);
							continue field;
						}
						else if(o instanceof Float){
							cell.setCellValue((Float)o);
							continue field;
						}
						else if(o instanceof Double){
							cell.setCellValue((Double)o);
							continue field;
						}
						else if(o instanceof Boolean){
							if(columnCfg.map()){
								HashMap<String, String> map=reflectInfo.getMap();
								if(map.containsKey("#true")&&(Boolean)o){
									cell.setCellValue(map.get("#true"));
									continue field;
								}else if(map.containsKey("#false")&&!(Boolean)o){
									cell.setCellValue(map.get("#false"));
									continue field;
								}
							}else{
								cell.setCellValue((Boolean)o);
								continue field;
							}
						}
						else if(o instanceof Date){
							if(columnCfg.map()){
								SimpleDateFormat sdf=new SimpleDateFormat(columnCfg.simpleDateFormat());
								String datestr=sdf.format((Date)o);
								cell.setCellValue(datestr);
								sheet.setColumnWidth(columnCfg.location(), datestr.length()*260);
								continue field;
							}else{
								sheet.setColumnWidth(columnCfg.location(), 4000);
								cell.setCellStyle(dateStyle);
								cell.setCellValue((Date)o);
								continue field;
							}
						}
						else if(o instanceof Timestamp){
							if(columnCfg.map()){
								SimpleDateFormat sdf=new SimpleDateFormat(columnCfg.simpleDateFormat());
								String datestr=sdf.format(new Date(((Timestamp)o).getTime()));
								cell.setCellValue(datestr);
								sheet.setColumnWidth(columnCfg.location(), datestr.length()*260);
								continue field;
							}else{
								sheet.setColumnWidth(columnCfg.location(), 4000);
								cell.setCellStyle(dateStyle);
								cell.setCellValue(new Date(((Timestamp)o).getTime()));
								continue field;
							}
						}
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * 
	 * 功能描述：根据list生成Excel，存在输出流中
	 * 时间：2014年7月25日
	 * @author ：lirenbo
	 * @param list 需要转换为Excel的泛型List
	 * @param out	输出流文件
	 * @throws ExcelNullListException
	 * @throws ExcelWrongAnnotationException
	 * @throws IOException
	 */
	public static <E> void generateExecl(List<E> list,OutputStream out,int officeVersion) throws ExcelNullListException, ExcelWrongAnnotationException, IOException{
		if(list==null||list.size()==0){
			throw new ExcelNullListException();
		}
		Class<? extends Object> transformClass = list.get(0).getClass();
		ClassReflectInfos classReflectInfos =ClassReflectInfoHelper.getClassReflectInfos(transformClass.getName());
		
		Workbook wb=null;
		switch (officeVersion) {
		case OFFICE03:
			wb=new HSSFWorkbook();
			break;
		case OFFICE07:
			wb = new SXSSFWorkbook(1000);
			break;
		default:
			throw new ExcelConstructException("传入版本号有误");
		}
		

		Sheet sheet = null;
		SheetCfg sheetCfg=null;
		if(transformClass.isAnnotationPresent(SheetCfg.class))
		{
			sheetCfg = (SheetCfg)transformClass.getAnnotation(SheetCfg.class);
			sheet= wb.createSheet(sheetCfg.sheetName());
		}else{
			throw new ExcelWrongAnnotationException(transformClass.getName()+"的SheetCfg注释未编写");
		}
		int startRowNumber=sheetCfg.startRowNumber();
		
		CellStyle dateStyle=wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		dateStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));
		
		Row startRow=sheet.createRow(startRowNumber);
		int width=titleToExecl(classReflectInfos, startRow, sheet, sheetCfg);

		for(int i=0;i<list.size();i++){
			Row row=sheet.createRow(i+startRowNumber+1);
			classReflectToExecl(classReflectInfos, list.get(i), wb, sheet, row,sheetCfg,dateStyle);
		}
		
		if(sheetCfg.startRowNumber()>=1){
			sheet.createRow(0).createCell(0);
			CellRangeAddress cra=new CellRangeAddress(0, sheetCfg.startRowNumber()-1, 0, width);
			sheet.addMergedRegion(cra);
			sheet.getRow(0).getCell(0).setCellValue(sheetCfg.title());
		}
		
		wb.write(out);
		out.close();
	}
	
	/**
	 * 
	 * 功能描述：从path路径，读取name文件，生成list。
	 * 时间：2014年7月25日
	 * @author ：lirenbo
	 * @param clazz	要生成的Object类型。需要使用SheetCfg标注。
	 * @param path	本地文件路径
	 * @param name	文件名，带后缀，自动使用office03或07
	 * @return
	 * @throws ExcelWrongAnnotationException
	 * @throws IOException
	 * @throws ExcelConstructException
	 */
	public static <T> List<T> readExecl(Class<T> clazz,String path,String name) throws ExcelWrongAnnotationException, IOException, ExcelConstructException{
		FileInputStream in;
		if(path.endsWith("\\")){
			in  = new FileInputStream(path+name);			
		}else{
			in  = new FileInputStream(path+"\\"+name);
		}
		if(name.endsWith(".xls")){
			return readExecl(clazz, in,OFFICE03);		
		}else if(name.endsWith(".xlsx")){
			return readExecl(clazz, in,OFFICE07);
		}else{
			return null;
		}
	}
	
	private static void rowToObject(ClassReflectInfos classReflectInfos,Row row,Object t,SheetCfg sheetCfg){
		rows:for (ReflectInfo reflectInfo:classReflectInfos.reflectInfoList) {
			for(ColumnCfg columnCfg:reflectInfo.getAnnotationSets()){
				try {
					if(columnCfg.sheetId()==sheetCfg.sheetId()||columnCfg.sheetId()==-1){
						if(columnCfg.location()==-1){
							reflectInfo.getMethodSet().invoke(t, reflectInfo.getMethodGet().getReturnType().newInstance());
							rowToObject(reflectInfo.getClassReflectInfos(), row, reflectInfo.getMethodGet().invoke(t, null),sheetCfg);
							continue rows;
						}
						Cell cell=row.getCell(columnCfg.location());
						if (cell==null) {
							continue rows;
						}
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BLANK:
							continue rows;
						case Cell.CELL_TYPE_BOOLEAN :
							reflectInfo.getMethodSet().invoke(t, cell.getBooleanCellValue());
							continue rows;
						case Cell.CELL_TYPE_STRING :
							if("".equals(cell.getStringCellValue())){
								continue rows;
							}
							if(columnCfg.map()){
								if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(String.class)){
									reflectInfo.getMethodSet().invoke(t,reflectInfo.getKey(cell.getStringCellValue()));
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Integer.class)){
									String info=reflectInfo.getKey(cell.getStringCellValue());
									if(info!=null){
										reflectInfo.getMethodSet().invoke(t,Integer.valueOf(info));
										continue rows;
									}else{
										throw new ExcelConstructException("map映射中，"+info+"不在mapString的value列中");
									}
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Boolean.class)){
									if(reflectInfo.getKey(cell.getStringCellValue())!=null&&"#true".equals(reflectInfo.getKey(cell.getStringCellValue()))){
										reflectInfo.getMethodSet().invoke(t, true);
										continue rows;
									}else if(reflectInfo.getKey(cell.getStringCellValue())!=null&&"#false".equals(reflectInfo.getKey(cell.getStringCellValue()))){
										reflectInfo.getMethodSet().invoke(t, false);
										continue rows;
									}else{
										throw new ExcelConstructException("map映射中，"+cell.getStringCellValue()+"不在mapString的value列中,或#true/#false不在mapString的key列中");
									}
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Date.class)){
									SimpleDateFormat sdf=new SimpleDateFormat(columnCfg.simpleDateFormat());
									try {
										reflectInfo.getMethodSet().invoke(t, sdf.parse(cell.getStringCellValue()));
										continue rows;
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Timestamp.class)){
									SimpleDateFormat sdf=new SimpleDateFormat(columnCfg.simpleDateFormat());
									try {
										reflectInfo.getMethodSet().invoke(t, new Timestamp(sdf.parse(cell.getStringCellValue()).getTime()));
										continue rows;
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}else{
									throw new ExcelConstructException("map类型错误");
								}
							}else{
								if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(String.class)){
									reflectInfo.getMethodSet().invoke(t, cell.getStringCellValue());
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(StringBuffer.class)){
									reflectInfo.getMethodSet().invoke(t, new StringBuffer(cell.getStringCellValue()));
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Boolean.class)){
									reflectInfo.getMethodSet().invoke(t,cell.getBooleanCellValue());
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Integer.class)){
									reflectInfo.getMethodSet().invoke(t,Integer.valueOf(cell.getStringCellValue()));
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Long.class)){
									reflectInfo.getMethodSet().invoke(t,Long.valueOf(cell.getStringCellValue()));
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Double.class)){
									reflectInfo.getMethodSet().invoke(t,Double.valueOf(cell.getStringCellValue()));
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Short.class)){
									reflectInfo.getMethodSet().invoke(t,Short.valueOf(cell.getStringCellValue()));
									continue rows;
								}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Float.class)){
									reflectInfo.getMethodSet().invoke(t,Float.valueOf(cell.getStringCellValue()));
									continue rows;
								}
								System.out.println("aa"+cell.getStringCellValue());
								System.out.println(reflectInfo);
								throw new ExcelConstructException(reflectInfo.getMethodSet().getName()+"  "+reflectInfo.getMethodSet().getParameterTypes()[0]+"的参数类型不是map=false的 Date Boolean String");
							}
							
						case Cell.CELL_TYPE_NUMERIC  :
							if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Integer.class)){
								reflectInfo.getMethodSet().invoke(t,new Double(cell.getNumericCellValue()).intValue());
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Float.class)){
								reflectInfo.getMethodSet().invoke(t,new Double(cell.getNumericCellValue()).floatValue());
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Double.class)){
								reflectInfo.getMethodSet().invoke(t,new Double(cell.getNumericCellValue()));
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Long.class)){
								reflectInfo.getMethodSet().invoke(t,new Double(cell.getNumericCellValue()).longValue());
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Short.class)){
								reflectInfo.getMethodSet().invoke(t,new Double(cell.getNumericCellValue()).shortValue());
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Date.class)){
								reflectInfo.getMethodSet().invoke(t,cell.getDateCellValue());
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(Timestamp.class)){
								reflectInfo.getMethodSet().invoke(t,new Timestamp(cell.getDateCellValue().getTime()));
								continue rows;
							}else if(reflectInfo.getMethodSet().getParameterTypes()[0].equals(String.class)){
								reflectInfo.getMethodSet().invoke(t,nf.format(cell.getNumericCellValue()));
								continue rows;
							}
							throw new ExcelConstructException(reflectInfo.getMethodSet().getName()+"的参数类型不是Integer Float Double Long Short Date String");
						default:
							throw new ExcelConstructException("excel表格中出现无法识别的类型,类型参数为cell.getCellType()（非String Numeric Boolean）");
						}
					}
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * 功能描述：从流中读取Excel，根据Excel生成list。
	 * 时间：2014年7月25日
	 * @author ：lirenbo
	 * @param clazz 要生成的Object类型。需要使用SheetCfg标注。
	 * @param in	输入流
	 * @return
	 * @throws ExcelWrongAnnotationException
	 * @throws IOException
	 * @throws ExcelConstructException
	 */
	public static <T> List<T> readExecl(Class<T> clazz,InputStream in,int officeVersion) throws ExcelWrongAnnotationException, IOException, ExcelConstructException{
		
		Workbook wb=null;
		switch (officeVersion) {
		case OFFICE03:
			wb=new HSSFWorkbook(in);
			break;
		case OFFICE07:
			wb = new XSSFWorkbook(in);
			break;
		default:
			throw new ExcelConstructException("传入版本号有误");
		}
		Sheet sheet = null;
		SheetCfg sheetCfg=null;
		ClassReflectInfos classReflectInfos =ClassReflectInfoHelper.getClassReflectInfos(clazz.getName());
		if(clazz.isAnnotationPresent(SheetCfg.class)){
			sheetCfg=clazz.getAnnotation(SheetCfg.class);
			sheet=wb.getSheet(sheetCfg.sheetName());
			if(sheet==null){
				throw new ExcelWrongAnnotationException("SheetCfg注释中sheetName="+sheetCfg.sheetName()+"在excel文件中并没有与之对应的模板");
			}
		}else{
			throw new ExcelWrongAnnotationException(clazz.getName()+"的SheetCfg注释未编写");
		}
		
		ArrayList<T> list=new ArrayList<T>();
		int startRowNumber=sheetCfg.startRowNumber();
		
		for(int i=startRowNumber+1;i<=sheet.getLastRowNum();i++){
			Row row=sheet.getRow(i);
			T t;
			try {
				t=clazz.newInstance();
			} catch (InstantiationException e) {
				throw new ExcelConstructException("没有空参数的构造方法");
			} catch (IllegalAccessException e) {
				throw new ExcelConstructException("空参数的构造方法为private");
			}
			rowToObject(classReflectInfos, row, t,sheetCfg);
			list.add(t);
		}
		return list;
	}
}
