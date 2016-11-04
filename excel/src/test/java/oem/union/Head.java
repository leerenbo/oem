package oem.union;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMColumns;

public class Head {
	@OEMColumns(@OEMColumn(sheetId=3,location=0,title="头中眼睛"))
	String eyes;
	@OEMColumns(@OEMColumn(sheetId=3,location=1,title="头中耳朵"))
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
