package tracker.service.transactionreport.server;

import java.util.List;
import java.util.logging.Logger;

import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionDataSourceFactory;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.service.transactionreport.client.TransactionReportService;
import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeResponse;
import tracker.service.transactionreport.shared.GetYearlyReportRequest;
import tracker.service.transactionreport.shared.GetYearlyReportResponse;
import antshpra.gwt.rpc.server.RequestValidator;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.ServerException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionReportServiceImpl extends RemoteServiceServlet implements TransactionReportService {

	private static final Logger logger = Logger.getLogger( TransactionReportServiceImpl.class.getName() );

	private static final TransactionDataSourceFactory transactionDataSourceFactory = new TransactionDataSourceFactory();
	
	@Override
	public GetTotalAmountByTransactionItemTypeResponse getTotalAmountByTransactionItemType( GetTotalAmountByTransactionItemTypeRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
		transactionItemQuery.setTransactionItemTypeId( request.getTransactionItemTypeId() );
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

		double amount = transactionDataSource
				.getTransactionItemType( request.getTransactionItemTypeId() )
				.getInitialAmount();
		
		for( TransactionItemJDO transactionItem : transactionItemList )
			amount = amount + transactionItem.getAmount();
		
		transactionDataSource.close();
		
		GetTotalAmountByTransactionItemTypeResponse response = new GetTotalAmountByTransactionItemTypeResponse();
		response.setAmount( amount );
		
		return response;
	}

	@Override
	public GetMonthlyReportResponse getMonthlyReport(
			GetMonthlyReportRequest reqest) throws InvalidRequestException,
			ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetYearlyReportResponse getYearlyReport(GetYearlyReportRequest reqest)
			throws InvalidRequestException, ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
