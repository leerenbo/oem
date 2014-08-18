package Include;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.ColumnCfgs;

public class InSideObject {
	@ColumnCfgs({@ColumnCfg(location=6,sheetId=2,name="我只映射一个sheet，sheetId=2")})
	Integer integer;
	
	@ColumnCfgs({
		@ColumnCfg(location=9,sheetId=1,name="我是一个内部，映射多个sheet，我在sheetId=1的表单中,location=9"),
		@ColumnCfg(location=5,sheetId=2,name="我是同一个内部类，的另一个@ColumnCfg，我映射多个sheet，映射在不同的外层类的表单sheetId=2，位于不同的location=5")
				})
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
		return "InSideObject [string=" + string + "]";
	}
	
	
}
