package tracker.commons.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionFilter implements IsSerializable {
	
	private String triTypeId;
	
	
	private Date trDateStart;

	private Date trDateEnd;

	private boolean trDateStartInclusive = true;

	private boolean trDateEndInclusive = false;

	
	private Date creationDateStart;

	private Date creationDateEnd;

	private boolean creationDateStartInclusive = true;

	private boolean creationDateEndInclusive = false;

	
	private Boolean trDateChronologicalOrder;
	
	private Boolean creationDateChronologicalOrder;

	
	public String getTransactionItemTypeId() {
		return triTypeId;
	}

	public void setTransactionItemTypeId( String triTypeId ) {
		this.triTypeId = triTypeId;
	}

	public Date getTransactionDateStart() {
		return trDateStart;
	}

	public boolean isTransactionDateStartInclusive() {
		return trDateStartInclusive;
	}

	public void setTransactionDateStart( Date trDateStart, boolean inclusive ) {
		this.trDateStart = trDateStart;
		this.trDateStartInclusive = inclusive;
	}
	
	public Date getTransactionDateEnd() {
		return trDateEnd;
	}

	public boolean isTransactionDateEndInclusive() {
		return trDateEndInclusive;
	}

	public void setTransactionDateEnd( Date trDateEnd, boolean inclusive ) {
		this.trDateEnd = trDateEnd;
		this.trDateEndInclusive = inclusive;
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
		return trDateChronologicalOrder;
	}
	
	public void setTransactionDateChronologicalOrder( boolean chronologicalOrder ) {
		this.trDateChronologicalOrder = chronologicalOrder;
	}

	public Boolean getCreationDateChronologicalOrder() {
		return creationDateChronologicalOrder;
	}
	
	public void setCreationDateChronologicalOrder( boolean chronologicalOrder ) {
		this.creationDateChronologicalOrder = chronologicalOrder;
	}

}
