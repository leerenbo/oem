package com.datalook.excel.spring.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;

import com.datalook.excel.annotation.OEMSheet;
import com.datalook.excel.core.holder.SheetInfo;

public class ClassToSheetInfoConverter implements Converter<Class, SheetInfo>, ConditionalConverter {

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return sourceType.getAnnotation(OEMSheet.class) != null;
	}

	@Override
	public SheetInfo convert(Class source) {
		return null;
	}

}
