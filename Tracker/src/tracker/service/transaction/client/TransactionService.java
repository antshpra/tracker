package tracker.service.transaction.client;

import tracker.service.transaction.shared.CreateTransactionRequest;
import tracker.service.transaction.shared.GetTransactionListRequest;
import tracker.service.transaction.shared.GetTransactionListResponse;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.ServerException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../_ah/transaction")
public interface TransactionService extends RemoteService {

	String createTransaction( CreateTransactionRequest request ) throws InvalidRequestException, ServerException;

	GetTransactionListResponse getTransactionList( GetTransactionListRequest request ) throws InvalidRequestException, ServerException;

}
