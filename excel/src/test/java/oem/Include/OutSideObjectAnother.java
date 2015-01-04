package oem.Include;

import java.util.Date;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMSheet;

@OEMSheet(id = 2, sheetName = "多次被包含", title = "多次被包含的内层类test", startRowNumber = 1)
public class OutSideObjectAnother {

	@OEMColumn(location = -1, title = "")
	InSideObject inSideObject;

	// 下列属性从ComplexTypeTest中拷贝而来
	@OEMColumn(location = 0, title = "Date对String的双向映射", map = true, simpleDateFormat = "yyyy|MM|dd hh/mm/ss")
	Date now;

	@OEMColumn(location = 1, title = "Integer对String的双向映射", map = true, mapString = "{\"1\":\"正常\",\"2\":\"删除\",\"3\":\"异常\",\"4\":\"其他\"}")
	Integer status;

	@OEMColumn(location = 2, title = "String对String的双向映射", map = true, mapString = "{\"a\":\"apple\",\"b\":\"banana\"}")
	String fruit;
	@OEMColumn(location = 3, title = "Boolean对String的双向映射", map = true, mapString = "{\"true\":\"导出成功\",\"false\":\"导出失败\"}")
	Boolean success;
	@OEMColumn(location = 4, title = "默认的mapString是true:真,false:假", map = true)
	Boolean defaultMapString;

	public OutSideObjectAnother() {
		super();
	}

	public OutSideObjectAnother(InSideObject inSideObject, Date now, Integer status, String fruit, Boolean success, Boolean defaultMapString) {
		super();
		this.inSideObject = inSideObject;
		this.now = now;
		this.status = status;
		this.fruit = fruit;
		this.success = success;
		this.defaultMapString = defaultMapString;
	}

	public Boolean getDefaultMapString() {
		return defaultMapString;
	}

	public String getFruit() {
		return fruit;
	}

	public InSideObject getInSideObject() {
		return inSideObject;
	}

	public Date getNow() {
		return now;
	}

	public Integer getStatus() {
		return status;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setDefaultMapString(Boolean defaultMapString) {
		this.defaultMapString = defaultMapString;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	public void setInSideObject(InSideObject inSideObject) {
		this.inSideObject = inSideObject;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "OutSideObjectAnother [defaultMapString=" + defaultMapString + ", fruit=" + fruit + ", inSideObject=" + inSideObject + ", now=" + now + ", status=" + status + ", success=" + success + "]";
	}

}
