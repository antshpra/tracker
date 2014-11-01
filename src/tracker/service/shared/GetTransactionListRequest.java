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

	
	private String cursor = null;

	private int pageSize = 10;

	
	public Date getTransactionDateStart() {
		return transactionDateStart;
	}

	public boolean isTransactionDateStartInclusive() {
		return transactionDateStartInclusive;
	}

	public void setTransactionDateStart( Date transactionDateStart, boolean inclusive ) {
		this.transactionDateStart = transactionDateStart;
		this.transactionDateStartInclusive = inclusive;
	}
	
	public Date getTransactionDateEnd() {
		return transactionDateEnd;
	}

	public boolean isTransactionDateEndInclusive() {
		return transactionDateEndInclusive;
	}

	public void setTransactionDateEnd( Date transactionDateEnd, boolean inclusive ) {
		this.transactionDateEnd = transactionDateEnd;
		this.transactionDateEndInclusive = inclusive;
	}

	public Date getCreationDateStart() {
		return creationDateStart;
	}

	public boolean isCreationDateStartInclusive() {
		return creationDateStartInclusive;
	}

	public void setCreationDateStart( Date creationDateStart, boolean inclusive ) {
		this.creationDateStart = creationDateStart;
		this.creationDateStartInclusive = inclusive;
	}
	
	public Date getCreationDateEnd() {
		return creationDateEnd;
	}

	public boolean isCreationDateEndInclusive() {
		return creationDateEndInclusive;
	}
	
	public void setCreationDateEnd( Date creationDateEnd, boolean inclusive ) {
		this.creationDateEnd = creationDateEnd;
		this.creationDateEndInclusive = inclusive;
	}

	public Boolean getTransactionDateChronologicalOrder() {
		return transactionDateChronologicalOrder;
	}
	
	public void setTransactionDateChronologicalOrder( boolean chronologicalOrder ) {
		this.transactionDateChronologicalOrder = chronologicalOrder;
	}

	public Boolean getCreationDateChronologicalOrder() {
		return creationDateChronologicalOrder;
	}
	
	public void setCreationDateChronologicalOrder( boolean chronologicalOrder ) {
		this.creationDateChronologicalOrder = chronologicalOrder;
	}

	public String getCursor() {
		return cursor;
	}
	
	public void setPageSize( String cursor ) {
		this.cursor = cursor;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}

}
