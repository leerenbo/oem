package Include;

import java.util.Date;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.SheetCfg;

@SheetCfg(sheetId=2,sheetName="多次被包含",title="多次被包含的内层类test")
public class OutSideObjectAnother {

	@ColumnCfg(location=-1,name="")
	InSideObject inSideObject;
	
	public InSideObject getInSideObject() {
		return inSideObject;
	}
	public void setInSideObject(InSideObject inSideObject) {
		this.inSideObject = inSideObject;
	}

	//下列属性从ComplexTypeTest中拷贝而来
	@ColumnCfg(location=0,name="Date对String的双向映射",map=true,simpleDateFormat="yyyy|MM|dd hh/mm/ss")
	Date now;
	@ColumnCfg(location=1,name="Integer对String的双向映射",map=true,mapString="1:正常,2:删除,3:异常,4:其他")
	Integer status;
	@ColumnCfg(location=2,name="String对String的双向映射",map=true,mapString="a:apple,b:banana")
	String fruit;
	@ColumnCfg(location=3,name="Boolean对String的双向映射",map=true,mapString="#true:导出成功,#false:导出失败")
	Boolean success;
	@ColumnCfg(location=4,name="默认的mapString是#true:真,#false:假",map=true)
	Boolean defaultMapString;
	
	
	@Override
	public String toString() {
		return "OutSideObjectAnother [defaultMapString=" + defaultMapString
				+ ", fruit=" + fruit + ", inSideObject=" + inSideObject
				+ ", now=" + now + ", status=" + status + ", success="
				+ success + "]";
	}
	public OutSideObjectAnother() {
		super();
	}
	public OutSideObjectAnother(InSideObject inSideObject, Date now,
			Integer status, String fruit, Boolean success,
			Boolean defaultMapString) {
		super();
		this.inSideObject = inSideObject;
		this.now = now;
		this.status = status;
		this.fruit = fruit;
		this.success = success;
		this.defaultMapString = defaultMapString;
	}
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFruit() {
		return fruit;
	}
	public void setFruit(String fruit) {
		this.fruit = fruit;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getDefaultMapString() {
		return defaultMapString;
	}
	public void setDefaultMapString(Boolean defaultMapString) {
		this.defaultMapString = defaultMapString;
	}

}
