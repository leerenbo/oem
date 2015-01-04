package oem.complex;

import java.sql.Timestamp;
import java.util.Date;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMSheet;

@OEMSheet(title = "复杂属性的讲解", sheetName = "complexSheet", startRowNumber = 2)
public class ComplexTypeTest {
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
	@OEMColumn(location = 5, title = "Timastamp对String的双向映射", map = true, simpleDateFormat = "yyyy/MM/dd hh:mm:ss")
	Timestamp timestampp;

	public Timestamp getTimestampp() {
		return timestampp;
	}

	public void setTimestampp(Timestamp timestampp) {
		this.timestampp = timestampp;
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

	public ComplexTypeTest(Date now, Integer status, String fruit, Boolean success, Boolean defaultMapString, Timestamp timestampp) {
		super();
		this.now = now;
		this.status = status;
		this.fruit = fruit;
		this.success = success;
		this.defaultMapString = defaultMapString;
		this.timestampp = timestampp;
	}

	public ComplexTypeTest() {
		super();
	}

	@Override
	public String toString() {
		return "ComplexTypeTest [defaultMapString=" + defaultMapString + ", fruit=" + fruit + ", now=" + now + ", status=" + status + ", success=" + success + "]";
	}

}
