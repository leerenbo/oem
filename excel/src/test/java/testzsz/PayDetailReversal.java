package testzsz;


import java.sql.Timestamp;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.SheetCfg;

/**
 * 
 * 功能描述：新生财务收费上传实体类
 * 时间：2014-7-28
 * @author ：zhaoshizhuo
 */
@SheetCfg(title="新生财务收费",sheetName="sheet1",startRowNumber=1)
public class PayDetailReversal {
    private Long id;
    @ColumnCfg(location=0,name="证件号")
    private String idserial;
    @ColumnCfg(location=1,name="学生姓名")
    private String username;
    @ColumnCfg(location=2,name="缴费代码")
    private String paycode;
    @ColumnCfg(location=3,name="缴费金额")
    private Long payamt;
    private String paystatus;
    private Timestamp paytime;
    
    private String errorMessage;
    @ColumnCfg(location=4,name="错误信息")
    public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public  PayDetailReversal(){
    	paystatus = "0";
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdserial() {
		return idserial;
	}
	public void setIdserial(String idserial) {
		this.idserial = idserial;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public Long getPayamt() {
		return payamt;
	}
	public void setPayamt(Long payamt) {
		this.payamt = payamt;
	}
	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	public Timestamp getPaytime() {
		return paytime;
	}
	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "PayDetailReversal [id=" + id + ", idserial=" + idserial + ", username=" + username + ", paycode=" + paycode + ", payamt=" + payamt + ", paystatus=" + paystatus + ", paytime=" + paytime + ", errorMessage=" + errorMessage + "]";
	}
    
}
