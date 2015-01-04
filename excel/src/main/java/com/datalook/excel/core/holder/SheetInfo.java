package com.datalook.excel.core.holder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMRelatedColumns;
import com.datalook.excel.annotation.OEMSheet;
import com.datalook.lrb.reflectutils.ReflectUtil;

public class SheetInfo {
	public Class clazz;
	public int id;
	public String title;
	public String sheetName;
	public int startRowNumber;

	public List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
	

	public SheetInfo(Class clazz) {
		this.clazz = clazz;
		OEMSheet oems = (OEMSheet) clazz.getAnnotation(OEMSheet.class);
		id = oems.id();
		title = oems.title();
		sheetName = oems.sheetName();
		startRowNumber = oems.startRowNumber();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			OEMColumn oemc = ReflectUtil.getAnnotation(field, OEMColumn.class);
			if (oemc != null && oemc.sheetId() == -1) {
//				if (oemc.location() >= 0) {
//					ColumnInfo ci = new ColumnInfo(oemc, field);
//					columns.add(ci);
//				} else {
					columns.add(new ColumnInfo(oemc, field,id));
//				}
			}
		}
	}
}
