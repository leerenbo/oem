package oem.complex;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.datalook.excel.core.OEM;
import com.datalook.exceltool.exception.ExcelConstructException;
import com.datalook.exceltool.exception.ExcelNullListException;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class Main {

	public static void main(String[] args) {
		ArrayList<ComplexTypeTest> al=new ArrayList<ComplexTypeTest>();
		al.add(new ComplexTypeTest(new Date(), 1, "b", true, true,new Timestamp(new Date().getTime())));
		al.add(new ComplexTypeTest(new Date(), 2, "a", false, false,new Timestamp(new Date().getTime())));
		try {
			//生成
			OEM.generateExecl(al, "D:\\", "ComplexTypeTest的映射excel文件名.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//读取到list中
			List<ComplexTypeTest> lstt=OEM.readExecl(ComplexTypeTest.class, "D:\\", "ComplexTypeTest的映射excel文件名.xls");
			System.out.println(lstt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
