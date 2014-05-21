package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetMonthlyReportRequest extends Request {

	private static final long serialVersionUID = -7943074790482734240L;

	public enum YearType {
		CALENDAR,
		FINANCIAL
	}
	
	public enum ReportType {
		PERODIC,
		CUMULATIVE
	}

	
	@RequiredField
	public Integer year;
	
	@RequiredField
	public YearType yearType;

	@RequiredField
	public String transactionItemTypeId;

	@RequiredField
	public ReportType reportType;

	
	public int getYear() { return year; }

	public YearType getYearType() { return this.yearType; }

	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public ReportType getReportType() { return this.reportType; }

	
	public void setYear( int year  ) {
		assertNonZero( year );
		assertNonNegative( year );
		this.year = year;
	}

	public void setYearType( YearType yearType  ) {
		assertNonNull( yearType );
		this.yearType = yearType;
	}

	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		assertNonNull( transactionItemTypeId );
		assertNonEmpty( transactionItemTypeId );
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public void setReportType( ReportType reportType ) {
		assertNonNull( reportType );
		this.reportType = reportType;
	}
	
}