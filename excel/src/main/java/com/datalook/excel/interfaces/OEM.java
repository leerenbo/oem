package com.datalook.excel.interfaces;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.datalook.excel.builder.OfficeVersion;
import com.datalook.excel.core.holder.SheetInfo;

public interface OEM {

	public Workbook renderSheet(List<?> data, SheetInfo sheetInfo, OfficeVersion officeVersion);

	public Workbook renderSheet(List<?> data, SheetInfo sheetInfo);

	public Workbook renderSheet(List<?> data);

	public Workbook renderSheets(List<?>[] datas);

	public Workbook renderSheets(List<?>[] datas, SheetInfo[] sheetInfo);

	public SheetInfo getSheetInfo(Class<?> clazz);

}
