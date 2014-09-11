package escape;

import java.io.IOException;
import java.util.ArrayList;

import com.datalook.exceltool.core.OEMContext;
import com.datalook.exceltool.exception.ExcelNullListException;
import com.datalook.exceltool.exception.ExcelWrongAnnotationException;

public class main {

	public static void main(String[] args) throws ExcelNullListException, ExcelWrongAnnotationException, IOException {
		ArrayList<ScheduleEmployee> al=new ArrayList<ScheduleEmployee>();
		al.add(new ScheduleEmployee(1l, "asddf", "01", "34,35"));
		OEMContext.generateExecl(al, "d:/", "asd.xls");
	}
}
