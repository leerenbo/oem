package com.datalook.excel.builder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.datalook.excel.core.holder.ColumnInfo;
import com.datalook.excel.core.holder.SheetInfo;
import com.datalook.excel.interfaces.OEMSheetBuilder;

public class SheetInfoSheetBuilder extends OEMSheetBuilder {

	SheetInfo sheetInfo;
	Workbook workbook;
	Sheet sheet;
	List data;

	private Integer rowIndex = 0;

	public SheetInfoSheetBuilder(List data, SheetInfo sheetInfo, Workbook workbook) {
		super();
		this.data = data;
		this.sheetInfo = sheetInfo;
		this.workbook = workbook;
	}

	@Override
	protected void buildTab() {
		sheet = workbook.createSheet(sheetInfo.sheetName);
	}

	@Override
	protected void buildTitle() {
		sheet.createRow(0).createCell(0);
		CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex++, 0, sheetInfo.columns.size()-1);
		sheet.addMergedRegion(cra);
		sheet.getRow(0).getCell(0).setCellValue(sheetInfo.title);
	}

	@Override
	protected void buildColumn() {
		Row row = sheet.createRow(rowIndex++);
		for (int columnIndex = 0; columnIndex < sheetInfo.columns.size(); columnIndex++) {
			Cell cell = row.createCell(columnIndex);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(sheetInfo.columns.get(columnIndex).title);
		}
	}

	@Override
	protected void buildData() {
		data.forEach(this::buildRowData);
	}

	protected void buildRowData(Object rowData) {
		if (rowData == null) {
			return;
		}
		Row row = sheet.createRow(rowIndex++);
		for (int columnIndex = 0; columnIndex < sheetInfo.columns.size(); columnIndex++) {
			Cell cell = row.createCell(columnIndex);
			ColumnInfo columnInfo = sheetInfo.columns.get(columnIndex);

			Object value = null;
			Class type = null;
			// 如果是map，根据key取值
			if (Map.class.isAssignableFrom(columnInfo.field.getType())) {
				try {
					Map map = (Map) FieldUtils.readField(columnInfo.field, rowData, true);
					value = map.get(columnInfo.mapKey);
					type = columnInfo.mapClass;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					value = FieldUtils.readField(columnInfo.field, rowData, true);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				type = columnInfo.field.getType();
			}
			cellSetValue(cell,type,value);
		}

	}

	private void cellSetValue(Cell cell, Class type, Object value) {
		if (value==null) {
			return;
		}
		if (Date.class.isAssignableFrom(type)) {
			cell.setCellValue((Date)value);	
		}else {
			cell.setCellValue(value.toString());	
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
	}

	@Override
	protected void buildFoot() {

	}

	@Override
	protected void buildCopyRight() {

	}

	public Sheet build() {
		buildTab();
		buildTitle();
		buildColumn();
		buildData();
		buildFoot();
		buildCopyRight();
		return sheet;
	}

	public static void main(String[] args) {
	}
}
