package tracker.service.transaction.client;

import java.util.Date;
import java.util.List;

import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionsResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../_ah/transaction")
public interface TransactionService extends RemoteService {

	String createTransaction( CreateTransactionRequest request );

	String createTransactionItem( CreateTransactionItemRequest request );

	List<String> createTransactionItemList( List<CreateTransactionItemRequest> requestList );

	List<GetTransactionsResponse> getTransactions(Date olderThan, int count);

}
