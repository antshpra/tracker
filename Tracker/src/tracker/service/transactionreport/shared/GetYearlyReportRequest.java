package tracker.service.transactionreport.shared;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class GetYearlyReportRequest extends Request {

	private static final long serialVersionUID = -4087368586479020540L;

	@RequiredField
	public String transactionItemTypeId;
	
	public Integer yearFrom;
	
	public Integer yearTo;

	private boolean ascendingOrder = true;

	private int pageSize = 5;

	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public Integer getYearFrom() { return this.yearFrom; }

	public Integer getYearTo() { return this.yearTo; }

	public boolean isAscendingOrder() { return this.ascendingOrder; }
	
	public int getPageSize() { return this.pageSize; }

	
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		assertNonNull( transactionItemTypeId );
		assertNonEmpty( transactionItemTypeId );
		this.transactionItemTypeId = transactionItemTypeId;
	}

	public void setYearFrom( int yearFrom ) {
		assertNonZero( yearFrom );
		assertNonNegative( yearFrom );
		if( this.yearTo != null )
			assertLessThanOrEqual( yearFrom, this.yearTo );
		this.yearFrom = yearFrom;
	}

	public void setYearTo( int yearTo ) {
		assertNonZero( yearTo );
		assertNonNegative( yearTo );
		if( this.yearFrom != null )
			assertLessThanOrEqual( this.yearFrom, yearTo );
		this.yearTo = yearTo;
	}

	public void setAscendingOrder( boolean ascendingOrder ) {
		this.ascendingOrder = ascendingOrder;
	}
	
	public void setPageSize( int pageSize ) {
		assertNonZero( pageSize );
		assertNonNegative( pageSize );
		this.pageSize = pageSize;
	}
	
	public String toString() {
		return "transactionItemTypeId = " + this.transactionItemTypeId + ", "
				+ "yearFrom = " + this.yearFrom + ", "
				+ "yearTo = " + this.yearTo + ", "
				+ "ascendingOrder = " + this.ascendingOrder + ", "
				+ "pageSize = " + this.pageSize;
	}
	
}
