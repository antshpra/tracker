package tracker.data.transfer;

import java.io.Serializable;
import java.util.Date;

import tracker.commons.shared.TransactionState;

import com.claymus.commons.client.Amount;

public interface TransactionItem extends Serializable {

	String getId();
	
	String getTransactionId();

	void setTransactionId( String transactionId );
	
	String getTransactionItemTypeId();
	
	void setTransactionItemTypeId( String transactionItemTypeId );
	
	Date getTransactionDate();

	void setTransactionDate( Date transactionDate );

	Long getAmount();
	
	void setAmount( Long amount );

	@Deprecated
	void setAmount( Amount amount );
	
	String getNote();
	
	void setNote( String note );
	
	TransactionState getState();
	
	void setState( TransactionState transactionState );
	
	Long getOrder();
	
	void setOrder( Long order );
	
	Date getCreationDate();

	void setCreationDate( Date creationDate );
	
	String getCreatedBy();

	void setCreatedBy( String createdBy );
	
	Date getLastUpdationDate();
	
	void setLastUpdationDate( Date lastUpdationDate );

}
