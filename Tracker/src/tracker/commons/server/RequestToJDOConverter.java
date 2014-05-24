package tracker.commons.server;

import java.util.Date;

import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;

public class RequestToJDOConverter {

	public static TransactionJDO convert( CreateTransactionRequest request ) {
		TransactionJDO transaction = new TransactionJDO();
		transaction.setTransactionDate( request.getTransactionDate() == null ? new Date() : request.getTransactionDate() );
		transaction.setDescription( request.getDescription() );
		transaction.setCreationDate( new Date() );
		transaction.setCreatedBy( "antshpra@gmail.com" ); // TODO: Fetch and set user id instead of hard coded id

		return transaction;
	}
	
	public static TransactionItemJDO convert( CreateTransactionItemRequest itemRequest ) {
		TransactionItemJDO transactionItem = new TransactionItemJDO();
		transactionItem.setTransactionId( itemRequest.getTransactionId() );
		transactionItem.setTransactionItemTypeId( itemRequest.getTransactionItemTypeId() );
		transactionItem.setTransactionDate( itemRequest.getTransactionDate() == null ? new Date() : itemRequest.getTransactionDate() );
		transactionItem.setAmount( itemRequest.getAmount() );
		transactionItem.setNote( itemRequest.getNote() );
		transactionItem.setCreationDate( new Date() );
		transactionItem.setCreatedBy( "antshpra@gmail.com" ); // TODO: Fetch and set user id instead of hard coded id

		return transactionItem;
	}
	
}
