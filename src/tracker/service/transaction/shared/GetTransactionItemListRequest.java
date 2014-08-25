package tracker.service.transaction.shared;

import java.util.Date;

import antshpra.gwt.rpc.shared.Request;

public class GetTransactionItemListRequest extends Request {
	
	private String transactionItemTypeId;
	
	private Date transactionDateStart;

	private Date transactionDateEnd;

	private boolean transactionDateStartInclusive = true;

	private boolean transactionDateEndInclusive = false;

	private Date creationDateStart;

	private Date creationDateEnd;

	private boolean creationDateStartInclusive = true;

	private boolean creationDateEndInclusive = false;

	private Boolean transactionDateChronologicalOrder;
	
	private Boolean creationDateChronologicalOrder;

	private int pageSize = 10;

	
	public String getTransactionItemTypeId() { return this.transactionItemTypeId; }
	
	public Date getTransactionDateStart() { return this.transactionDateStart; }

	public Date getTransactionDateEnd() { return this.transactionDateEnd; }

	public boolean isTransactionDateStartInclusive() { return this.transactionDateStartInclusive; }

	public boolean isTransactionDateEndInclusive() { return this.transactionDateEndInclusive; }

	public Date getCreationDateStart() { return this.creationDateStart; }

	public Date getCreationDateEnd() { return this.creationDateEnd; }

	public boolean isCreationDateStartInclusive() { return this.creationDateStartInclusive; }

	public boolean isCreationDateEndInclusive() { return this.creationDateEndInclusive; }
	
	public Boolean getTransactionDateChronologicalOrder() { return transactionDateChronologicalOrder; }
	
	public Boolean getCreationDateChronologicalOrder() { return creationDateChronologicalOrder; }
	
	public int getPageSize() { return this.pageSize; }
	
	
	public void setTransactionItemTypeId( String transactionItemTypeId ) {
		assertNonNull( transactionItemTypeId );
		assertNonEmpty( transactionItemTypeId );
		this.transactionItemTypeId = transactionItemTypeId;
	}
	
	public void setTransactionDateStart( Date transactionDateStart, boolean inclusive ) {
		assertNonNull( transactionDateStart );
		if( this.transactionDateEnd != null )
			assertStartDateIsNotAfterEndDate( transactionDateStart, this.transactionDateEnd );
		this.transactionDateStart = transactionDateStart;
		this.transactionDateStartInclusive = inclusive;
	}
	
	public void setTransactionDateEnd( Date transactionDateEnd, boolean inclusive ) {
		assertNonNull( transactionDateEnd );
		if( this.transactionDateStart != null )
			assertStartDateIsNotAfterEndDate( this.transactionDateStart, transactionDateEnd );
		this.transactionDateEnd = transactionDateEnd;
		this.transactionDateEndInclusive = inclusive;
	}

	public void setCreationDateStart( Date creationDateStart, boolean inclusive ) {
		assertNonNull( creationDateStart );
		if( this.creationDateEnd != null )
			assertStartDateIsNotAfterEndDate( creationDateStart, this.creationDateEnd );
		this.creationDateStart = creationDateStart;
		this.creationDateStartInclusive = inclusive;
	}
	
	public void setCreationDateEnd( Date creationDateEnd, boolean inclusive ) {
		assertNonNull( creationDateEnd );
		if( this.creationDateStart != null )
			assertStartDateIsNotAfterEndDate( this.creationDateStart, creationDateEnd );
		this.creationDateEnd = creationDateEnd;
		this.creationDateEndInclusive = inclusive;
	}

	public void setTransactionDateChronologicalOrder( boolean chronologicalOrder ) {
		this.transactionDateChronologicalOrder = chronologicalOrder;
	}

	public void setCreationDateChronologicalOrder( boolean chronologicalOrder ) {
		this.creationDateChronologicalOrder = chronologicalOrder;
	}

	public void setPageSize( int pageSize ) {
		assertNonZero( pageSize );
		assertNonNegative( pageSize );
		this.pageSize = pageSize;
	}
	
}
