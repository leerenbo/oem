package com.datalook.excel.core.holder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.AnnotationUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ReflectionUtils;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMSheet;
import com.datalook.util.ReflectUtil;

public class SheetInfo implements Cloneable{
	public Class clazz;

	public int id;

	public String sheetName;

	public int startRowNumber;

	public String title;

	public List<ColumnInfo> columns;

	public SheetInfo(Class clazz) {
		this.clazz = clazz;
		OEMSheet oems = (OEMSheet) clazz.getAnnotation(OEMSheet.class);
		id = oems.id();
		title = oems.title();
		sheetName = oems.sheetName();
		startRowNumber = oems.startRowNumber();
		Field[] fields = clazz.getDeclaredFields();
		HashSet<ColumnInfo> columnInfoSet = new HashSet<>();

		for (Field field : fields) {
			Arrays.stream(field.getAnnotationsByType(OEMColumn.class)).filter((column) -> {
				return column.sheetId() == id||column.sheetId()==-1;
			}).forEach((column) -> {
				columnInfoSet.add(new ColumnInfo(column, field, id));
			});
		}

		for (ColumnInfo columnInfo : columnInfoSet) {
			columnInfo.addInsideColumnsTo(columnInfoSet);
		}

		columns = new ArrayList<>(columnInfoSet);
		Collections.sort(columns);

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
