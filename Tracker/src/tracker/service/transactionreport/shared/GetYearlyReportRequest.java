package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetYearlyReportRequest extends Request {

	private static final long serialVersionUID = -4087368586479020540L;

	@RequiredField
	public Integer yearFrom;
	
	@RequiredField
	public Integer yearTo;

	@RequiredField
	public String transactionItemTypeId;
	
	
	public int getYearFrom() { return yearFrom; }

	public int getYearTo() { return yearTo; }

	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	
	public void setYearFrom( int yearFrom ) {
		assertNonZero( yearFrom );
		this.yearFrom = yearFrom;
	}

	public void setYearTo( int yearTo ) {
		assertNonZero( yearTo );
		this.yearTo = yearTo;
	}

}
