package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetYearlyReportRequest extends Request {

	private static final long serialVersionUID = -4087368586479020540L;

	public enum ReportType {
		PERODIC,
		CUMULATIVE
	}
	

	@RequiredField
	public Integer yearFrom;
	
	@RequiredField
	public Integer yearTo;

	@RequiredField
	public String transactionItemTypeId;
	
	@RequiredField
	public ReportType reportType;
	
	
	public int getYearFrom() { return yearFrom; }

	public int getYearTo() { return yearTo; }

	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public ReportType getReportType() { return this.reportType; }
	
	
	public void setYearFrom( int yearFrom ) {
		assertNonZero( yearFrom );
		assertNonNegative( yearFrom );
		this.yearFrom = yearFrom;
	}

	public void setYearTo( int yearTo ) {
		assertNonZero( yearTo );
		assertNonNegative( yearTo );
		this.yearTo = yearTo;
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
