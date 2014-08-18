package union;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.datalook.exceltool.core.OEMContext;
import com.datalook.exceltool.exception.ExcelConstructException;
import com.datalook.exceltool.exception.ExcelNullListException;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class Main {

	public static void main(String[] args) {
		ArrayList<PersonSheet> al=new ArrayList<PersonSheet>();
		al.add(new PersonSheet(new Head("大眼睛", "小耳朵"), new Body("短胳膊","短腿")));
		al.add(new PersonSheet(new Head("小眼睛", "大耳朵"), new Body("长胳膊","长腿")));
		
		try {
			OEMContext.generateExecl(al, "D:\\", "PersonSheet的映射excel文件名.xls");
		} catch (ExcelNullListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcelWrongAnnotationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//读取到list中
			List<PersonSheet> lstt=OEMContext.readExecl(PersonSheet.class, "D:\\", "PersonSheet的映射excel文件名.xls");
			System.out.println(lstt);
		} catch (ExcelWrongAnnotationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcelConstructException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
