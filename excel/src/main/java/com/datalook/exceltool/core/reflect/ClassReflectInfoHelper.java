package com.datalook.exceltool.core.reflect;

import java.util.HashMap;
import java.util.Map;

public class ClassReflectInfoHelper {
	
	private static Map<String, ClassReflectInfos> classReflectInfosMap=new HashMap<String, ClassReflectInfos>();
	
	private static ClassReflectInfoHelper classReflectInfoHelper=new ClassReflectInfoHelper();
	
	public ClassReflectInfoHelper getInstance(){
		return classReflectInfoHelper;
	}
	
	private ClassReflectInfoHelper() {
		super();
	}

	public static ClassReflectInfos getClassReflectInfos(String className){
		ClassReflectInfos cri;
		if(classReflectInfosMap.containsKey(className)){
			cri= classReflectInfosMap.get(className);
		}else {
			cri=new ClassReflectInfos();
			cri.init(className);
			classReflectInfosMap.put(className, cri);
		}
		return cri;
	}
}
