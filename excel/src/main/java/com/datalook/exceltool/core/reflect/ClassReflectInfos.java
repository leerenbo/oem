package com.datalook.exceltool.core.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.ColumnCfgs;
import com.datalook.exceltool.core.util.MethodFieldStringUtil;

public class ClassReflectInfos {
	
	public ArrayList<ReflectInfo> reflectInfoList=new ArrayList<ReflectInfo>();
	
	public ArrayList<ReflectInfo> init(String className){
		Class clazz = null;
		try {
			clazz = getClass().forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Field[] fields=clazz.getDeclaredFields();
		Method[] methods=clazz.getMethods();
		for(int i=0;i<fields.length;i++){
			ReflectInfo reflectInfotemp=new ReflectInfo();
			reflectInfotemp.field=fields[i];
			reflectInfotemp.fieldName=fields[i].getName();
			if(reflectInfotemp.field.getAnnotation(ColumnCfg.class)!=null){
				reflectInfotemp.fieldAnnotations.add(reflectInfotemp.field.getAnnotation(ColumnCfg.class));
			}
			if(reflectInfotemp.field.getAnnotation(ColumnCfgs.class)!=null){
				ColumnCfgs columnCfgs=reflectInfotemp.field.getAnnotation(ColumnCfgs.class);
				for(int j=0;j<columnCfgs.value().length;j++){
					reflectInfotemp.fieldAnnotations.add(columnCfgs.value()[j]);
				}
			}
			for(int j=0;j<methods.length;j++){
				if(methods[j].getName().equals(MethodFieldStringUtil.addPrefix("get", fields[i].getName()))&&methods[j].getParameterTypes().length==0){
					if(methods[j].getAnnotation(ColumnCfg.class) != null){
						reflectInfotemp.annotationGets.add(methods[j].getAnnotation(ColumnCfg.class));
					}
					if(methods[j].getAnnotation(ColumnCfgs.class)!=null){
						ColumnCfg[] columnCfgs=methods[j].getAnnotation(ColumnCfgs.class).value();
						if(columnCfgs!= null){
							for(int k=0;k<columnCfgs.length;k++){
								reflectInfotemp.annotationGets.add(columnCfgs[k]);
							}
						}
					}
					reflectInfotemp.methodGet=methods[j];
					
				}
				if(methods[j].getName().equals(MethodFieldStringUtil.addPrefix("set", fields[i].getName()))&&methods[j].getReturnType().equals(Void.TYPE)){
					if(methods[j].getAnnotation(ColumnCfg.class)!=null){
						reflectInfotemp.annotationSets.add(methods[j].getAnnotation(ColumnCfg.class));
					}
					if(methods[j].getAnnotation(ColumnCfgs.class)!=null){
						ColumnCfg[] columnCfgs=methods[j].getAnnotation(ColumnCfgs.class).value();
						if(columnCfgs!= null){
							for(int k=0;k<columnCfgs.length;k++){
								reflectInfotemp.annotationSets.add(columnCfgs[k]);
							}
						}
					}
					reflectInfotemp.methodSet=methods[j];

				}
			}
			if(reflectInfotemp.fieldAnnotations.size()==0&&reflectInfotemp.annotationSets.size()==0&&reflectInfotemp.annotationGets.size()==0){
				continue;
			}
			for(ColumnCfg columnCfg:reflectInfotemp.fieldAnnotations){
				if(columnCfg.location()==-1){
					reflectInfotemp.classReflectInfos=ClassReflectInfoHelper.getClassReflectInfos(reflectInfotemp.methodGet.getReturnType().getName());
				}
			}
			reflectInfoList.add(reflectInfotemp);
		}
		return reflectInfoList;
	}
	
	public Integer size(){
		return reflectInfoList.size();
	}
}
