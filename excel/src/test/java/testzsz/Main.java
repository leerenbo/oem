package testzsz;

import java.io.IOException;
import java.util.List;

import com.datalook.exceltool.core.OEMContext;
import com.datalook.exceltool.exception.ExcelConstructException;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class Main {
	public static void main(String[] args) {
		List<PayDetailReversal> l = null;
		try {
			l=OEMContext.readExecl(PayDetailReversal.class, "d:\\", "2222.xls");
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
		System.out.println(l);
	}
}
