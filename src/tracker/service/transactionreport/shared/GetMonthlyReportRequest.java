package tracker.service.transactionreport.shared;

import tracker.commons.shared.YearType;
import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetMonthlyReportRequest extends Request {

	@RequiredField
	public String transactionItemTypeId;

	@RequiredField
	public Integer year;
	
	@RequiredField
	public YearType yearType;

	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public int getYear() { return year; }

	public YearType getYearType() { return this.yearType; }

	
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		assertNonNull( transactionItemTypeId );
		assertNonEmpty( transactionItemTypeId );
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public void setYear( int year  ) {
		assertNonZero( year );
		assertNonNegative( year );
		this.year = year;
	}

	public void setYearType( YearType yearType  ) {
		assertNonNull( yearType );
		this.yearType = yearType;
	}

	public String toString() {
		return "transactionItemTypeId = " + this.transactionItemTypeId + ", "
				+ "year = " + this.year + ", "
				+ "yearType = " + this.yearType;
	}

}