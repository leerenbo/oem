package oem.union;

import com.datalook.excel.annotation.OEMColumn;
import com.datalook.excel.annotation.OEMColumn.ReadWriteStrategy;
import com.datalook.excel.annotation.OEMSheet;

@OEMSheet(id=3,sheetName="union",title="我得sheetId=3，同一个工程的sheetId都不能重复")
public class PersonSheet {
	@OEMColumn(location=-1,title="")
	Head head;
	@OEMColumn(location=-1,title="")
	Body body;

	@OEMColumn(location=8,title="错误信息",strategy=ReadWriteStrategy.writeOnly)
	String error;

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	public PersonSheet(Head head, Body body) {
		super();
		this.head = head;
		this.body = body;
	}
	
	
	public PersonSheet(Head head, Body body, String error) {
		super();
		this.head = head;
		this.body = body;
		this.error = error;
	}
	public PersonSheet() {
		super();
	}
	@Override
	public String toString() {
		return "PersonSheet [head=" + head + ", body=" + body + ", error=" + error + "]";
	}
	
}
