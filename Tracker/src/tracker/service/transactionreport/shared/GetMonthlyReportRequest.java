package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetMonthlyReportRequest extends Request {

	public enum YearType {
		CALENDAR,
		FINANCIAL
	}

	
	private static final long serialVersionUID = -7943074790482734240L;

	@RequiredField
	public Integer year;
	
	@RequiredField
	public YearType yearType;

	@RequiredField
	public String transactionItemTypeId;
	
	
	public int getYear() { return year; }

	public YearType getYearType() { return this.yearType; }

	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	
	public void setYear( int year  ) {
		assertNonZero( year );
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
	
}