package tracker.data.transfer;

import java.io.Serializable;
import java.util.Date;

import tracker.commons.shared.TransactionReportType;

import com.claymus.commons.client.Amount;

public interface TransactionReport extends Serializable {
	
	String getIndex();
	
	void setIndex( String index );
	
	void setTransactionItemTypeId( String transactionItemTypeId );
	
	String getTransactionItemTypeId();

	TransactionReportType getType();
	
	void setType( TransactionReportType type );
	
	Amount getAmount();
	
	void setAmount( Amount amount );
	
	Date getLastUpdationDate();
	
	void setLastUpdationDate( Date lastUpdationDate );

}
