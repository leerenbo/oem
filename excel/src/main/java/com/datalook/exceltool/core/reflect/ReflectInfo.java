package com.datalook.exceltool.core.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class ReflectInfo {
	
	private static String separatedouhao=UUID.randomUUID().toString();//  /,
	private static String separatemaohao=UUID.randomUUID().toString();//  /:
	private static String separatexiegang=UUID.randomUUID().toString();// //
	
	String fieldName;
	Field field;
	Method methodGet;
	Method methodSet;
	ArrayList<ColumnCfg> fieldAnnotations=new ArrayList<ColumnCfg>();
	ArrayList<ColumnCfg> annotationSets=new ArrayList<ColumnCfg>();
	ArrayList<ColumnCfg> annotationGets=new ArrayList<ColumnCfg>();
	
	private HashMap<String, String> map;
	ClassReflectInfos classReflectInfos;
	
	private ArrayList<ColumnCfg> fieldsetAnnotations;
	private ArrayList<ColumnCfg> fieldgetAnnotations;
	
	public ClassReflectInfos getClassReflectInfos() {
		return classReflectInfos;
	}

	public  HashMap<String, String> getMap() {
		if(map==null){
			map=new HashMap<String, String>();
			ColumnCfg columnCfgTemp = null;
			if(fieldAnnotations.size()>0){
				columnCfgTemp=fieldAnnotations.get(0);
			}else if(annotationSets.size()>0){
				columnCfgTemp=annotationSets.get(0);
			}else if(annotationGets.size()>0){
				columnCfgTemp=annotationGets.get(0);
			}else{
				new ExcelWrongAnnotationException("没有定义columnCfg");
			}
			String mapString=columnCfgTemp.mapString();
			mapString=mapString.replace("//", separatexiegang);
			mapString=mapString.replace("/,", separatedouhao);
			mapString=mapString.replace("/:", separatemaohao);
			String[] mapStrings=mapString.split(",");
			for(String eachmapString:mapStrings){
				String[] keyvalue=eachmapString.split(":");
				
				keyvalue[0]=keyvalue[0].replace( separatexiegang,"/");
				keyvalue[0]=keyvalue[0].replace( separatedouhao,",");
				keyvalue[0]=keyvalue[0].replace( separatemaohao,":");
				keyvalue[1]=keyvalue[1].replace( separatexiegang,"/");
				keyvalue[1]=keyvalue[1].replace( separatedouhao,",");
				keyvalue[1]=keyvalue[1].replace( separatemaohao,":");

				map.put(keyvalue[0], keyvalue[1]);
			}
		}
		return map;
	}
	
	public String getKey(String value) throws ExcelWrongAnnotationException{
		Set<String>keys=getMap().keySet();
		String re = null;
		int count=0;
		for(String key:keys){
			if(value.equals(getMap().get(key))){
				re=key;
				count++;
				if(count>1){
					throw new ExcelWrongAnnotationException("mapString,Value有重复，无法映射回Key");
				}
			}
		}
		return re;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public Field getField() {
		return field;
	}
	public Method getMethodGet() {
		return methodGet;
	}
	public Method getMethodSet() {
		return methodSet;
	}

	public ArrayList<ColumnCfg> getFieldAnnotations() {
		return fieldAnnotations;
	}

	public ArrayList<ColumnCfg> getAnnotationSets() {
		if(fieldsetAnnotations==null){
			fieldsetAnnotations=new ArrayList<ColumnCfg>(fieldAnnotations);
			fieldsetAnnotations.addAll(annotationSets);
		}
		return fieldsetAnnotations;
	}

	public ArrayList<ColumnCfg> getAnnotationGets() {
		if(fieldgetAnnotations==null){
			fieldgetAnnotations=new ArrayList<ColumnCfg>(fieldAnnotations);
			fieldgetAnnotations.addAll(annotationGets);
		}
		return fieldgetAnnotations;
	}

	@Override
	public String toString() {
		return "ReflectInfo [fieldName=" + fieldName + ", field=" + field
				+ ", methodGet=" + methodGet + ", methodSet=" + methodSet
				+ ", fieldAnnotations=" + fieldAnnotations
				+ ", annotationSets=" + annotationSets + ", annotationGets="
				+ annotationGets + ", map=" + map + ", classReflectInfos="
				+ classReflectInfos + ", fieldsetAnnotations="
				+ fieldsetAnnotations + ", fieldgetAnnotations="
				+ fieldgetAnnotations + "]";
	}
	
}
