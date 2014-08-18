package union;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.SheetCfg;

@SheetCfg(sheetId=3,sheetName="union",title="我得sheetId=3，同一个工程的sheetId都不能重复")
public class PersonSheet {
	@ColumnCfg(location=-1,name="")
	Head head;
	@ColumnCfg(location=-1,name="")
	Body body;

	String error;

	@ColumnCfg(location=88,name="错误信息")
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
	public PersonSheet() {
		super();
	}
	@Override
	public String toString() {
		return "PersonSheet [body=" + body + ", head=" + head + "]";
	}
	
	
}
