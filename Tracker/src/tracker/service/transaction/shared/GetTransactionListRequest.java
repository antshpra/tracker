package tracker.service.transaction.shared;

import java.util.Date;

import antshpra.gwt.rpc.shared.Request;

public class GetTransactionListRequest extends Request {
	
	private static final long serialVersionUID = 6764626699617192952L;

	private Date startDate;

	private Date endDate;

	private boolean startDateInclusive = true;
	
	private boolean endDateInclusive = false;
	
	private boolean chronologicalOrder = true;
	
	private int pageSize = 10;

	private boolean loadTransactionItemList = false;

	
	public Date getStartDate() { return this.startDate; }

	public Date getEndDate() { return this.endDate; }

	public boolean isStartDateInclusive() { return this.startDateInclusive; }

	public boolean isEndDateInclusive() { return this.endDateInclusive; }

	public boolean isChronologicalOrder() { return chronologicalOrder; }
	
	public int getPageSize() { return this.pageSize; }
	
	public boolean shouldLoadTransactionItemList() { return this.loadTransactionItemList; }

	
	public void setStartDate( Date startDate ) {
		assertNonNull( startDate );
		if( this.endDate != null )
			assertStartDateIsNotAfterEndDate( startDate, this.endDate );
		this.startDate = startDate;
	}
	
	public void setEndDate( Date endDate ) {
		assertNonNull( endDate );
		if( this.startDate != null )
			assertStartDateIsNotAfterEndDate( this.startDate, endDate );
		this.endDate = endDate;
	}

	public void setStartDateInclusive( boolean startDateInclusive ) {
		this.startDateInclusive = startDateInclusive;
	}

	public void setEndDateInclusive( boolean endDateInclusive ) {
		this.endDateInclusive = endDateInclusive;
	}

	public void setChronologicalOrder( boolean chronologicalOrder ) {
		this.chronologicalOrder = chronologicalOrder;
	}

	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}

	public void setLoadTransactionItemList( boolean loadTransactionItemList ) {
		this.loadTransactionItemList = loadTransactionItemList;
	}
	
}
