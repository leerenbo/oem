package oem.union;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMColumns;

public class Body {
	@OEMColumns(@OEMColumn(sheetId=3,location=3,title="身中胳膊"))
	String arms;
	@OEMColumns(@OEMColumn(sheetId=3,location=4,title="身中退"))
	String legs;
	
	
	public String getArms() {
		return arms;
	}
	public void setArms(String arms) {
		this.arms = arms;
	}
	public String getLegs() {
		return legs;
	}
	public void setLegs(String legs) {
		this.legs = legs;
	}
	public Body(String arms, String legs) {
		super();
		this.arms = arms;
		this.legs = legs;
	}
	public Body() {
		super();
	}
	@Override
	public String toString() {
		return "Body [arms=" + arms + ", legs=" + legs + "]";
	}
	

}
