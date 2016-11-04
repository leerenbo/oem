package com.datalook.excel.builder;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.datalook.excel.core.holder.SheetInfo;

public class SheetinfosWorkbookBuilder {

	private Workbook workbook;
	private List<SheetInfo> sheetInfos;
	private List<List> datas;
	private OfficeVersion officeVersion = OfficeVersion.OFFICE03;

	public List<SheetInfo> getSheetInfos() {
		return sheetInfos;
	}

	public SheetinfosWorkbookBuilder setSheetInfos(List<SheetInfo> sheetInfos) {
		this.sheetInfos = sheetInfos;
		return this;
	}

	public OfficeVersion getOfficeVersion() {
		return officeVersion;
	}

	public SheetinfosWorkbookBuilder setOfficeVersion(OfficeVersion officeVersion) {
		this.officeVersion = officeVersion;
		return this;
	}

	public List<List> getDatas() {
		return datas;
	}

	public SheetinfosWorkbookBuilder setDatas(List<List> datas) {
		this.datas = datas;
		return this;
	}

	public Workbook build() {
		buildinitWorkbook();
		buildSheets();
		return workbook;
	}

	protected void buildinitWorkbook() {
		switch (officeVersion) {
		case OFFICE03:
			workbook = new HSSFWorkbook();
			break;
		case OFFICE07:
			workbook = new SXSSFWorkbook(1000);
			break;
		default:
			throw new RuntimeException("传入office版本号有误");
		}
	}

	public Workbook buildSheets() {
		for (int i = 0; i < datas.size(); i++) {
			new SheetInfoSheetBuilder(datas.get(i), sheetInfos.get(i), workbook).build();
		}
		return workbook;
	}

}
