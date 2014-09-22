package tracker.service.client;

import tracker.service.shared.CreateTransactionResponse;
import tracker.service.shared.GetTransactionItemListRequest;
import tracker.service.shared.GetTransactionItemListResponse;
import tracker.service.shared.GetTransactionItemTypeListRequest;
import tracker.service.shared.GetTransactionItemTypeListResponse;
import tracker.service.shared.GetTransactionListRequest;
import tracker.service.shared.GetTransactionListResponse;
import tracker.service.shared.GetTransactionRequest;
import tracker.service.shared.GetTransactionResponse;
import tracker.service.shared.SaveTransactionRequest;

import com.claymus.commons.client.UnexpectedServerException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../service.tracker")
public interface TrackerService extends RemoteService {

	CreateTransactionResponse saveTransaction( SaveTransactionRequest request )
			throws UnexpectedServerException;

	
	
	
	GetTransactionResponse getTransaction( GetTransactionRequest request ) throws UnexpectedServerException;
	
	GetTransactionListResponse getTransactionList( GetTransactionListRequest request ) throws UnexpectedServerException;

	GetTransactionItemListResponse getTransactionItemList( GetTransactionItemListRequest request ) throws UnexpectedServerException;

	GetTransactionItemTypeListResponse getTransactionItemTypeList( GetTransactionItemTypeListRequest request ) throws UnexpectedServerException;

}
