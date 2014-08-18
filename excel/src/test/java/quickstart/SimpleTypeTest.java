package quickstart;


import java.sql.Timestamp;
import java.util.Date;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.SheetCfg;

@SheetCfg(sheetName="sheet")
public class SimpleTypeTest {

	@ColumnCfg(location=0,name="string类型")
	public String myString;
	@ColumnCfg(location=1,name="Integer类型")
	public Integer myInteger;
	@ColumnCfg(location=2,name="Long类型")
	public Long myLong;
	@ColumnCfg(location=3,name="Short类型")
	public Short myShort;
	@ColumnCfg(location=4,name="Double类型")
	public Double myDouble;
	@ColumnCfg(location=5,name="Float类型")
	public Float myFloat;
	@ColumnCfg(location=6,name="Boolean类型")
	public Boolean myBoolean;
	@ColumnCfg(location=7,name="Date类型")
	public Date myDate;
	@ColumnCfg(location=8,name="StringBuffer类型")
	public StringBuffer myStringBuffer;
	@ColumnCfg(location=9,name="Timestamp类型")
	public Timestamp timestampp;
	
	
	public Timestamp getTimestampp() {
		return timestampp;
	}

	public void setTimestampp(Timestamp timestampp) {
		this.timestampp = timestampp;
	}

	public SimpleTypeTest() {
		super();
	}

	public SimpleTypeTest(String myString, Integer myInteger, Long myLong,
			Short myShort, Double myDouble, Float myFloat, Boolean myBoolean,
			Date myDate, StringBuffer myStringBuffer,Timestamp timestampp) {
		super();
		this.myString = myString;
		this.myInteger = myInteger;
		this.myLong = myLong;
		this.myShort = myShort;
		this.myDouble = myDouble;
		this.myFloat = myFloat;
		this.myBoolean = myBoolean;
		this.myDate = myDate;
		this.myStringBuffer = myStringBuffer;
		this.timestampp=timestampp;
	}

	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	public Integer getMyInteger() {
		return myInteger;
	}

	public void setMyInteger(Integer myInteger) {
		this.myInteger = myInteger;
	}

	public Long getMyLong() {
		return myLong;
	}

	public void setMyLong(Long myLong) {
		this.myLong = myLong;
	}

	public Short getMyShort() {
		return myShort;
	}

	public void setMyShort(Short myShort) {
		this.myShort = myShort;
	}

	public Double getMyDouble() {
		return myDouble;
	}

	public void setMyDouble(Double myDouble) {
		this.myDouble = myDouble;
	}

	public Float getMyFloat() {
		return myFloat;
	}

	public void setMyFloat(Float myFloat) {
		this.myFloat = myFloat;
	}

	public Boolean getMyBoolean() {
		return myBoolean;
	}

	public void setMyBoolean(Boolean myBoolean) {
		this.myBoolean = myBoolean;
	}

	public Date getMyDate() {
		return myDate;
	}

	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}

	public StringBuffer getMyStringBuffer() {
		return myStringBuffer;
	}

	public void setMyStringBuffer(StringBuffer myStringBuffer) {
		this.myStringBuffer = myStringBuffer;
	}

	@Override
	public String toString() {
		return "SimpleTypeTest [myBoolean=" + myBoolean + ", myDate=" + myDate
				+ ", myDouble=" + myDouble + ", myFloat=" + myFloat
				+ ", myInteger=" + myInteger + ", myLong=" + myLong
				+ ", myShort=" + myShort + ", myString=" + myString
				+ ", myStringBuffer=" + myStringBuffer + "]";
	}
	
	
}
