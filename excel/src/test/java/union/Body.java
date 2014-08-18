package union;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.ColumnCfgs;

public class Body {
	@ColumnCfgs(@ColumnCfg(sheetId=3,location=3,name="身中胳膊"))
	String arms;
	@ColumnCfgs(@ColumnCfg(sheetId=3,location=4,name="身中退"))
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
