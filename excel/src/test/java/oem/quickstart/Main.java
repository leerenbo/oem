package oem.quickstart;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.datalook.excel.core.OEM;
import com.datalook.exceltool.core.OEMContext;
import com.datalook.exceltool.exception.ExcelConstructException;
import com.datalook.exceltool.exception.ExcelNullListException;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class Main {

	public static void main(String[] args) {
		System.out.println(new Date());
		ArrayList<SimpleTypeTest> al=new ArrayList<SimpleTypeTest>();
		for(int i=0;i<10000;i++){
			al.add(new SimpleTypeTest("字符串类型", 1, 2l, (short)3, 4d, 5f, true, new Date(), new StringBuffer("StringBuffer类型"),new Timestamp(new Date().getTime())));
		}
		try {
			//生成
			System.out.println("写:"+new Date());
			OEM.generateExecl(al, "D:\\", "SimpleTypeTest的映射excel文件名.xlsx");
			System.out.println(new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//读取到list中
			System.out.println("读:"+new Date());
			List<SimpleTypeTest> lstt=OEM.readExecl(SimpleTypeTest.class, "D:\\", "SimpleTypeTest的映射excel文件名.xlsx");
			System.out.println(new Date());
//			System.out.println(lstt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
