package tracker.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

import com.claymus.commons.client.Amount;

@SuppressWarnings("serial")
public class TransactionItemData implements Serializable {

	private String id;

	private String trId;

	private TransactionItemTypeData triType;
	
	private Long trDate;
	
	private Amount amount;
	
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
	
	public TransactionItemTypeData getTransactionItemType() {
		return triType;
	}
	
	public void setTransactionItemType( TransactionItemTypeData triType ) {
		this.triType = triType;
	}
	
	public Date getTransactionDate() {
		return new Date( trDate );
	}
	
	public void setTransactionDate( Date trDate ) {
		this.trDate = trDate.getTime();
	}
	
	public Amount getAmount() {
		return amount;
	}
	
	public void setAmount( Amount amount ) {
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
