package com.datalook.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于生成唯一的 Excel表单。sheetId不能重复
 */
@Documented 
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE) 
public @interface OEMSheet {
	/**
	 * 默认为 空
	 * startRowNumber为0时，不显示title。
	 * @return
	 */
	public String title() default "";
	/**
	 * 默认为sheet1
	 * @return
	 */
	public String sheetName() default "sheet1";
	/**
	 * 默认为0
	 * startRowNumber之前的行保存title，startRowNumber该行保存columnName，接着保存数据
	 * @return
	 */
	public int startRowNumber() default 0;
	/**
	 * 必填
	 * 用于区分sheet，用于关联SheetId相同的ColumnCfg生成的的列。-1,-2不能使用
	 * @return
	 */
	public int id() default -1;
}
