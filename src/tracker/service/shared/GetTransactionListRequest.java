package tracker.service.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionListRequest implements IsSerializable {
	
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
	
	
	public void setTransactionDateStart( Date transactionDateStart, boolean inclusive ) {
		this.transactionDateStart = transactionDateStart;
		this.transactionDateStartInclusive = inclusive;
	}
	
	public void setTransactionDateEnd( Date transactionDateEnd, boolean inclusive ) {
		this.transactionDateEnd = transactionDateEnd;
		this.transactionDateEndInclusive = inclusive;
	}

	public void setCreationDateStart( Date creationDateStart, boolean inclusive ) {
		this.creationDateStart = creationDateStart;
		this.creationDateStartInclusive = inclusive;
	}
	
	public void setCreationDateEnd( Date creationDateEnd, boolean inclusive ) {
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
		this.pageSize = pageSize;
	}
	
}