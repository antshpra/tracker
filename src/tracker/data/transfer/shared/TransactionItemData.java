package tracker.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TransactionItemData implements Serializable {

	private String id;

	private String trId;

	private String triTypeId;

	private TransactionItemTypeData triType;
	
	private Long trDate;
	
	private Long amount;
	
	private String note;

	private Integer order;

	private Long creationDate;
	
	
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}
	
	public String getTransactionId() {
		return trId;
	}
	
	public void setTransactionId( String trId ) {
		this.trId = trId;
	}
	
	public String getTransactionItemTypeId() {
		return triTypeId;
	}
	
	public void setTransactionItemTypeId( String triTypeId ) {
		this.triTypeId = triTypeId;
	}
	
	public TransactionItemTypeData getTransactionItemType() {
		return triType;
	}
	
	public void setTransactionItemType( TransactionItemTypeData triType ) {
		this.triType = triType;
	}
	
	public Date getTransactionDate() {
		return trDate == null ? null : new Date( trDate );
	}
	
	public void setTransactionDate( Date trDate ) {
		this.trDate = trDate == null ? null : trDate.getTime();
	}
	
	public Long getAmount() {
		return amount;
	}
	
	public void setAmount( Long amount ) {
		this.amount = amount;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote( String note ) {
		this.note = note;
	}
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder( Integer order ) {
		this.order = order;
	}

	public Date getCreationDate() {
		return new Date( creationDate );
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate.getTime();
	}
	
}
