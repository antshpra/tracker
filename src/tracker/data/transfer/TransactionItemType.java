package tracker.data.transfer;

import java.io.Serializable;

import tracker.commons.shared.TransactionReportType;

import com.claymus.commons.client.Amount;

public interface TransactionItemType extends Serializable {
	
	String getId();
	
	String getParentId();
	
	void setParentId( String parentId );
	
	String getTitle();

	void setTitle( String title );
	
	Amount getInitialAmount();

	void setInitialAmount( Amount initialAmount );
	
	void setTransactionReportType( TransactionReportType transactionReportType );
	
	TransactionReportType getTransactionReportType();
	
}
