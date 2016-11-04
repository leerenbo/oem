package com.datalook.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仅用于内层类的属性映射标识。
 */
@Documented 
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD,ElementType.METHOD}) 
public @interface OEMColumns {

	/**
	 * 
	 * 功能描述：装载ColumnCfg，
	 * 格式ColumnCfgs({ColumnCfg(...),ColumnCfg(...)})
	 * 或ColumnCfgs(value={ColumnCfg(...),ColumnCfg(...)})
	 * 时间：2014年7月25日
	 * @author ：lirenbo
	 * @return
	 */
	public OEMColumn[] value();
}
