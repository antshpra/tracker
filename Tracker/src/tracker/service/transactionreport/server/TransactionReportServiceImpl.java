package tracker.service.transactionreport.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionDataSourceFactory;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.service.transaction.shared.TransactionItemTypeData;
import tracker.service.transactionreport.client.TransactionReportService;
import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeRequest;
import tracker.service.transactionreport.shared.GetTotalAmountByTransactionItemTypeResponse;
import tracker.service.transactionreport.shared.GetYearlyReportRequest;
import tracker.service.transactionreport.shared.GetYearlyReportResponse;
import tracker.service.transactionreport.shared.TransactionReportData;
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
	public GetMonthlyReportResponse getMonthlyReport( GetMonthlyReportRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		TransactionItemTypeData transactionItemTypeData = loadTransactionItemTypeIdToTransactionItemTypeDataMap().get( request.getTransactionItemTypeId() );
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		
		List<TransactionItemTypeData> transactionItemTypeDataList = new ArrayList<>( 1 );
		transactionItemTypeDataList.add( transactionItemTypeData );
		List<TransactionReportData> transactionReportDataList = loadTransactionReportData( transactionItemTypeDataList, transactionDataSource );
		
		transactionDataSource.close();
		
		GetMonthlyReportResponse response = new GetMonthlyReportResponse();
		response.setTransactionReportData( transactionReportDataList.get( 0 ) );

		return response;
	}

	private List<TransactionReportData> loadTransactionReportData( List<TransactionItemTypeData> transactionItemTypeDataList, TransactionDataSource transactionDataSource ) {
		
		List<TransactionReportData> transactionReportDataList = new ArrayList<>( transactionItemTypeDataList.size() );
		
		for( TransactionItemTypeData transactionItemTypeData : transactionItemTypeDataList ) {
			TransactionReportData transactionReportData = new TransactionReportData();
			transactionReportData.setTransactionItemTypeId( transactionItemTypeData.getId() );
			transactionReportData.setTitle( transactionItemTypeData.getTitle() );

			TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
			transactionItemQuery.setTransactionItemTypeId( transactionItemTypeData.getId() );
			List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

			double amount = transactionItemTypeData.getInitialAmount();
			
			for( TransactionItemJDO transactionItem : transactionItemList )
				amount = amount + transactionItem.getAmount();

			transactionReportData.setAmount( new double[]{ amount } );
			
			for( TransactionReportData transactionReportDataChild : loadTransactionReportData( transactionItemTypeData.getChildren(), transactionDataSource ) )
				transactionReportData.addChild( transactionReportDataChild );
			
			transactionReportDataList.add( transactionReportData );
		}
		
		return transactionReportDataList;
	}
	
	@Override
	public GetYearlyReportResponse getYearlyReport( GetYearlyReportRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, TransactionItemTypeData> loadTransactionItemTypeIdToTransactionItemTypeDataMap() {
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

		TransactionItemTypeQuery transactionItemTypeQuery = transactionDataSource.newTransactionItemTypeQuery();
		List<TransactionItemTypeJDO> transactionItemTypeList = transactionItemTypeQuery.execute();
		
		// TODO: Cache the map in MemCache
		Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap = new LinkedHashMap<>( transactionItemTypeList.size() );
		
		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList ) {
			TransactionItemTypeData transactionItemTypeData = new TransactionItemTypeData();
			transactionItemTypeData.setId( transactionItemType.getId() );
			transactionItemTypeData.setTitle( transactionItemType.getTitle() );
			transactionItemTypeData.setInitialAmount( transactionItemType.getInitialAmount() );
			transactionItemTypeIdToTransactionItemTypeDataMap.put( transactionItemType.getId(), transactionItemTypeData );
		}

		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList ) {
			String id = transactionItemType.getId();
			String parentId = transactionItemType.getParentId();
			if( parentId != null ) {
				TransactionItemTypeData transactionItemTypeData = transactionItemTypeIdToTransactionItemTypeDataMap.get( id );
				TransactionItemTypeData parentTransactionItemTypeData = transactionItemTypeIdToTransactionItemTypeDataMap.get( parentId );
				transactionItemTypeData.setParent( parentTransactionItemTypeData );
				parentTransactionItemTypeData.addChild( transactionItemTypeData );
			}
		}
		
		transactionDataSource.close();
		
		return transactionItemTypeIdToTransactionItemTypeDataMap;
	}
	
}
