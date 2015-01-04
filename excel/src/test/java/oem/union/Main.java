package oem.union;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.datalook.excel.core.OEM;

public class Main {

	public static void main(String[] args) {
		ArrayList<PersonSheet> al = new ArrayList<PersonSheet>();
		al.add(new PersonSheet(new Head("大眼睛", "小耳朵"), new Body("短胳膊", "短腿"),"错误了"));
		al.add(new PersonSheet(new Head("小眼睛", "大耳朵"), new Body("长胳膊", "长腿"),"读不出来"));

		try {
			OEM.generateExecl(al, "D:\\", "PersonSheet的映射excel文件名.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// 读取到list中
			List<PersonSheet> lstt = OEM.readExecl(PersonSheet.class, "D:\\", "PersonSheet的映射excel文件名.xls");
			System.out.println(lstt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
