package com.datalook.excel.core.holder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClassHolder {

	public static Map<Class, SheetInfo> sheets = new HashMap<Class, SheetInfo>();

	public static SheetInfo getSheetInfo(Class clazz) {
		if (sheets.containsKey(clazz)) {
			return sheets.get(clazz);
		} else {
			SheetInfo si = new SheetInfo(clazz);
			sheets.put(clazz, si);
			return si;
		}
	}
	
}
