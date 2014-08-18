package union;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.ColumnCfgs;

public class Head {
	@ColumnCfgs(@ColumnCfg(sheetId=3,location=0,name="头中眼睛"))
	String eyes;
	@ColumnCfgs(@ColumnCfg(sheetId=3,location=1,name="头中耳朵"))
	String ears;
	public String getEyes() {
		return eyes;
	}
	public void setEyes(String eyes) {
		this.eyes = eyes;
	}
	public String getEars() {
		return ears;
	}
	public void setEars(String ears) {
		this.ears = ears;
	}
	public Head(String eyes, String ears) {
		super();
		this.eyes = eyes;
		this.ears = ears;
	}
	public Head() {
		super();
	}
	@Override
	public String toString() {
		return "Head [ears=" + ears + ", eyes=" + eyes + "]";
	}
	
	
}
