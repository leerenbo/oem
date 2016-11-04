package quickstart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Workbook;

import com.datalook.excel.OEMService;
import com.datalook.excel.builder.OfficeVersion;

public class Main {

	public static void main(String[] args) throws IOException {
		OEMService oemService = new OEMService();
		System.out.println(new Date());
		ArrayList<SimpleTypeTest> al = new ArrayList<SimpleTypeTest>();
		for (int i = 0; i < 1000; i++) {
			SimpleTypeTest re = new SimpleTypeTest("字符串类型", 1, 2l, (short) 3, 4d, 5f, true, new Date(), new StringBuffer("StringBuffer类型"), new Timestamp(new Date().getTime()));
			re.map.put("int", 123);
			re.map.put("string", "asdf");
			al.add(re);
		}
		// 生成
		System.out.println("写:" + new Date());
		Workbook wb = oemService.renderSheet(al, oemService.getSheetInfo(SimpleTypeTest.class), OfficeVersion.OFFICE03);
		OutputStream out=new FileOutputStream("/Users/lirenbo/Desktop/test.xls");
		wb.write(out);
		out.close();

		System.out.println(new Date());

	}
}
