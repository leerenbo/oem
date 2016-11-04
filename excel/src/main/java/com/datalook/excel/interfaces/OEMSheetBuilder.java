package com.datalook.excel.interfaces;

import org.apache.poi.ss.usermodel.Sheet;

public abstract class OEMSheetBuilder {

	protected abstract void buildTab();

	protected abstract void buildTitle();

	protected abstract void buildColumn();

	protected abstract void buildData();

	protected abstract void buildFoot();

	protected abstract void buildCopyRight();
	
	public abstract Sheet build();
}
