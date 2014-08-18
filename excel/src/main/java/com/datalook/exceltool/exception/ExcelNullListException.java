package com.datalook.exceltool.exception;

public class ExcelNullListException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcelNullListException(){
		super("传入的list是空的");
	}
	
}
