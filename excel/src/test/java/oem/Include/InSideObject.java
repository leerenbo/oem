package oem.Include;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMColumns;

public class InSideObject {
	@OEMColumns({ @OEMColumn(location = 6, sheetId = 2, title = "我只映射一个sheet，sheetId=2") })
	Integer integer;

	@OEMColumns({ @OEMColumn(location = 9, sheetId = 1, title = "我是一个内部，映射多个sheet，我在sheetId=1的表单中,location=9"), 
		@OEMColumn(location = 5, sheetId = 2, title = "我是同一个内部类，的另一个@OEMColumn，我映射多个sheet，映射在不同的外层类的表单sheetId=2，位于不同的location=5") })
	String string;

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public InSideObject(Integer integer, String string) {
		super();
		this.integer = integer;
		this.string = string;
	}

	public InSideObject() {
		super();
	}

	@Override
	public String toString() {
		return "InSideObject [integer=" + integer + ", string=" + string + "]";
	}


}
