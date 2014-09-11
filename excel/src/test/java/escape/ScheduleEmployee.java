package escape;

import java.util.Set;

import com.datalook.exceltool.annotation.ColumnCfg;
import com.datalook.exceltool.annotation.SheetCfg;

/**
 * ScheduleEmployee entity. @author MyEclipse Persistence Tools
 */
@SheetCfg(sheetId=1,sheetName="个人出勤报表")
public class ScheduleEmployee implements java.io.Serializable {

	// Fields

	private Long id;
	@ColumnCfg(location=3, name = "日期",sheetId=1)
	private String scheduledate;
	@ColumnCfg(location=5,name="排班类型",sheetId=1,map=true,mapString="00:上班,01:加班,02:补班,10:休假,11:补休,12:请假")
	private String scheduletype;
	@ColumnCfg(location=4,name="签到状态",sheetId=1,map=true,mapString="02/,15:迟到并早退,12345:特殊情况,03/,11:异地上班,03/,12:异地上下班,30/,35:加班下班忘打卡,11111:带薪休息,02/,11:迟到,00000:无薪休息,30/,31:加班,01/,11:出勤,34/,31:加班上班忘打卡,01/,10:下班忘记打卡,01/,13:晚下班,01/,12:异地下班,01/,15:早退,34/,35:未加班,00/,11:上班忘记打卡,32/,33:异地加班,00/,10:缺勤")
	private String attendencetype;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getScheduledate() {
		return scheduledate;
	}
	public void setScheduledate(String scheduledate) {
		this.scheduledate = scheduledate;
	}
	public String getScheduletype() {
		return scheduletype;
	}
	public void setScheduletype(String scheduletype) {
		this.scheduletype = scheduletype;
	}
	public String getAttendencetype() {
		return attendencetype;
	}
	public void setAttendencetype(String attendencetype) {
		this.attendencetype = attendencetype;
	}
	public ScheduleEmployee(Long id, String scheduledate, String scheduletype, String attendencetype) {
		super();
		this.id = id;
		this.scheduledate = scheduledate;
		this.scheduletype = scheduletype;
		this.attendencetype = attendencetype;
	}
}