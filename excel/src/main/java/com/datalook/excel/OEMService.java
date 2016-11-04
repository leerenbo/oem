package com.datalook.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.datalook.excel.builder.OfficeVersion;
import com.datalook.excel.builder.SheetinfosWorkbookBuilder;
import com.datalook.excel.core.holder.SheetInfo;
import com.datalook.excel.interfaces.OEM;

public class OEMService implements OEM {

	private static Map<Class, SheetInfo> sheets = new HashMap<Class, SheetInfo>();

	@Override
	public Workbook renderSheet(List<?> data, SheetInfo sheetInfo, OfficeVersion officeVersion) {
		List<SheetInfo> sheet = new ArrayList<SheetInfo>();
		sheet.add(sheetInfo);
		List<List> datas = new ArrayList<>();
		datas.add(data);
		return new SheetinfosWorkbookBuilder().setSheetInfos(sheet).setDatas(datas).setOfficeVersion(officeVersion).build();
	}

	@Override
	public Workbook renderSheet(List<?> data, SheetInfo sheetInfo) {
		return renderSheet(data, sheetInfo, OfficeVersion.OFFICE03);
	}

	@Override
	public Workbook renderSheet(List<?> data) {
		if (data!=null&&data.size()>0) {
			return renderSheet(data, getSheetInfo(data.get(0).getClass()));
		} else {
			return null;
		}
	}

	@Override
	public Workbook renderSheets(List<?>[] datas) {
		return null;
	}

	@Override
	public Workbook renderSheets(List<?>[] datas, SheetInfo[] sheetInfo) {
		return null;
	}

	@Override
	public SheetInfo getSheetInfo(Class<?> clazz) {
		if (sheets.containsKey(clazz)) {
			return sheets.get(clazz);
		} else {
			SheetInfo si = new SheetInfo(clazz);
			sheets.put(clazz, si);
			return si;
		}
	}

}
