package com.datalook.exceltool.core.util;

public class MethodFieldStringUtil {
	
	public static String addPrefix(String prefix,String field){
		String first = field.substring(0, 1).toUpperCase();
		String rest = field.substring(1);
		return new StringBuffer(prefix).append(first).append(rest).toString();
	}

}
