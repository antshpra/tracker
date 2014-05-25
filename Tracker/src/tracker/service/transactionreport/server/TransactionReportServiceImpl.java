package tracker.service.transactionreport.server;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import tracker.commons.server.DateUtil;
import tracker.commons.server.JDOToDataConverter;
import tracker.commons.shared.Amount;
import tracker.commons.shared.TransactionReportType;
import tracker.commons.shared.TransactionState;
import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionDataSourceFactory;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionItemTypeJDO;
import tracker.datasource.jdo.TransactionReportJDO;
import tracker.service.transaction.shared.TransactionItemTypeData;
import tracker.service.transactionreport.client.TransactionReportService;
import tracker.service.transactionreport.shared.GetMonthlyReportRequest;
import tracker.service.transactionreport.shared.GetMonthlyReportRequest.YearType;
import tracker.service.transactionreport.shared.GetMonthlyReportResponse;
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
	public GetYearlyReportResponse getYearlyReport( GetYearlyReportRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		logger.log( Level.INFO, request.toString() );
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

		GetYearlyReportResponse response = new GetYearlyReportResponse();
		List<TransactionReportData> transactionReportDataList = new LinkedList<>();
		response.setTransactionReportDataList( transactionReportDataList );

		TransactionItemQuery query = transactionDataSource.newTransactionItemQuery();
		query.setTransactionItemTypeId( request.getTransactionItemTypeId() );
		query.orderByTransactionDate( true );
		List<TransactionItemJDO> transactionItemList = query.execute( 0, 1 );
		if( transactionItemList.size() == 0 )
			return response;
		int reportYearStart = DateUtil.getYear( transactionItemList.get( 0 ).getTransactionDate() );
		
		query = transactionDataSource.newTransactionItemQuery();
		query.setTransactionItemTypeId( request.getTransactionItemTypeId() );
		query.orderByTransactionDate( false );
		transactionItemList = query.execute( 0, 1 );
		int reportYearEnd = DateUtil.getYear( transactionItemList.get( 0 ).getTransactionDate() );
		
		logger.log( Level.INFO, "reportYearStart = " + reportYearStart + ", reportYearEnd = " + reportYearEnd );

		int yearFrom = request.getYearFrom() == null || request.getYearFrom() < reportYearStart ? reportYearStart : request.getYearFrom();
		int yearTo = request.getYearTo() == null || request.getYearTo() > reportYearEnd ? reportYearEnd : request.getYearTo();
		
		logger.log( Level.INFO, "yearFrom = " + yearFrom + ", yearTo = " + yearTo );

		TransactionItemTypeData transactionItemTypeData = loadTransactionItemTypeIdToTransactionItemTypeDataMap( transactionDataSource ).get( request.getTransactionItemTypeId() );

		int year = request.isAscendingOrder() ? yearFrom : yearTo;
		while( year >= yearFrom && year <= yearTo && transactionReportDataList.size() < request.getPageSize() ) {
			
			logger.log( Level.INFO, "Getting report data for Year " + year );
			
			TransactionReportData transactionReportData = getYearlyReportData(
					year, transactionItemTypeData,
					transactionDataSource );
			
			transactionReportDataList.add( transactionReportData );
				
			year = request.isAscendingOrder() ? year + 1 : year - 1 ;
		}
		
		transactionDataSource.close();
		
		return response;
	}

	@Override
	public GetMonthlyReportResponse getMonthlyReport( GetMonthlyReportRequest request ) throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		logger.log( Level.INFO, request.toString() );

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

		GetMonthlyReportResponse response = new GetMonthlyReportResponse();
		List<TransactionReportData> transactionReportDataList = new LinkedList<>();
		response.setTransactionReportDataList( transactionReportDataList );

		TransactionItemTypeData transactionItemTypeData = loadTransactionItemTypeIdToTransactionItemTypeDataMap().get( request.getTransactionItemTypeId() );

		for( int i = 0; i < 12; i++ ) {
			int month;
			if( request.getYearType() == YearType.CALENDAR ) {
				month = i;
			} else if( request.getYearType() == YearType.FINANCIAL ) {
				month = i + 3;
			} else {
				throw new UnsupportedOperationException( "YearType '" + request.getYearType() + "' is not yet supported." );
			}

			logger.log( Level.INFO, "Getting report data for Year " + request.getYear() + ", Month " + month );
			
			TransactionReportData transactionReportData = getMonthlyReportData(
					request.getYear(), month,
					transactionItemTypeData,
					transactionDataSource );
			
			transactionReportDataList.add( transactionReportData );
		}
		
		transactionDataSource.close();

		return response;
	}
	
	private TransactionReportData getYearlyReportData(
			int year,
			TransactionItemTypeData transactionItemTypeData,
			TransactionDataSource transactionDataSource ) {
		
		logger.log( Level.INFO, "Getting report for Year " + year + ", TransactionItemTypeId " + transactionItemTypeData.getId() );

		TransactionReportJDO transactionReport = getYearlyReport(
				year, transactionItemTypeData.getId(),
				transactionItemTypeData.getInitialAmount(),
				transactionItemTypeData.getTransactionReportType(), 
				transactionDataSource );
		
		TransactionReportData transactionReportData = JDOToDataConverter.convert( transactionReport, year, transactionItemTypeData );
		
		for( TransactionItemTypeData transactionItemTypeDataChild : transactionItemTypeData.getChildren() )
			transactionReportData.addChild( getYearlyReportData( year, transactionItemTypeDataChild, transactionDataSource ) );
		
		return transactionReportData;
	}

	private TransactionReportData getMonthlyReportData(
			int year, int month,
			TransactionItemTypeData transactionItemTypeData,
			TransactionDataSource transactionDataSource ) {
		
		logger.log( Level.INFO, "Getting report for Year " + year + ", Month " + month + ", TransactionItemTypeId " + transactionItemTypeData.getId() );

		TransactionReportJDO transactionReport = getMonthlyReport(
				year, month, transactionItemTypeData.getId(),
				transactionItemTypeData.getInitialAmount(),
				transactionItemTypeData.getTransactionReportType(), 
				transactionDataSource );
		
		TransactionReportData transactionReportData = JDOToDataConverter.convert( transactionReport, month, transactionItemTypeData );
		
		for( TransactionItemTypeData transactionItemTypeDataChild : transactionItemTypeData.getChildren() )
			transactionReportData.addChild( getMonthlyReportData( year, month, transactionItemTypeDataChild, transactionDataSource ) );
		
		return transactionReportData;
	}

	private TransactionReportJDO getYearlyReport(
			int year, String transactionItemTypeId,
			Amount initialAmount,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		TransactionReportQuery query = transactionDataSource.newTransactionReportQuery();
		query.setIndex( "YEAR_" + year );
		query.setTransactionItemTypeId( transactionItemTypeId );
		query.setReportType( transactionReportType );
		query.orderByLastUpdationDate( false );
		List<TransactionReportJDO> transactionReportList = query.execute( 0, 1 );
		
		TransactionReportJDO transactionReport;
		if( transactionReportList.size() == 0 ) {
			transactionReport = new TransactionReportJDO();
			transactionReport.setIndex( "YEAR_" + year );
			transactionReport.setTransactionItemTypeId( transactionItemTypeId );
			transactionReport.setType( transactionReportType );
			
		} else {
			transactionReport = transactionReportList.get( 0 );
		}

		if( transactionReport.getLastUpdationDate() == null
				|| isReportRegenerationRequired( year, transactionReport.getLastUpdationDate(), transactionReportType, transactionDataSource ) ) {
			
			logger.log( Level.INFO, "(Re-)Generating report for Year " + year + ", TransactionItemTypeId " + transactionItemTypeId + ", TransactionReportType " + transactionReportType );
			
			Amount amount;
			if( transactionReportType == TransactionReportType.PERODIC ) {
				
				amount = new Amount( 0 );
				for( int month = 0; month < 12; month++ ) {
					TransactionReportJDO monthlyTransactionReport = getMonthlyReport(
							year, month, transactionItemTypeId,
							initialAmount,
							transactionReportType,
							transactionDataSource );
					amount = amount.add( monthlyTransactionReport.getAmount() );
				}
				
			} else if( transactionReportType == TransactionReportType.CUMULATIVE ) {
				
				TransactionReportJDO monthlyTransactionReport = getMonthlyReport(
						year, 11, transactionItemTypeId,
						initialAmount,
						transactionReportType,
						transactionDataSource );
				amount = monthlyTransactionReport.getAmount();
				
			} else {
				
				throw new UnsupportedOperationException( "TransactionReportType '" + transactionReportType + "' is not yet supported." );
			
			}
			
			transactionReport.setAmount( amount );
			transactionReport.setLastUpdationDate( new Date( new Date().getTime() - 1000 ) );
			transactionDataSource.persistTransactionReport( transactionReport );
			
		} else {
			
			logger.log( Level.INFO, "Returning pe-generated report for Year " + year + ", TransactionItemTypeId " + transactionItemTypeId + ", TransactionReportType " + transactionReportType );
			
		}
		
		return transactionReport;
	}
	
	private TransactionReportJDO getMonthlyReport(
			int year, int month, String transactionItemTypeId,
			Amount initialAmount,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		TransactionReportQuery query = transactionDataSource.newTransactionReportQuery();
		query.setIndex( "YEAR_" + year + "_MONTH_" + month );
		query.setTransactionItemTypeId( transactionItemTypeId );
		query.setReportType( transactionReportType );
		query.orderByLastUpdationDate( false );
		List<TransactionReportJDO> transactionReportList = query.execute( 0, 1 );
		
		TransactionReportJDO transactionReport;
		if( transactionReportList.size() == 0 ) {
			transactionReport = new TransactionReportJDO();
			transactionReport.setIndex( "YEAR_" + year + "_MONTH_" + month );
			transactionReport.setTransactionItemTypeId( transactionItemTypeId );
			transactionReport.setType( transactionReportType );
		} else {
			transactionReport = transactionReportList.get( 0 );
		}

		if( transactionReport.getLastUpdationDate() == null
				|| isReportRegenerationRequired( year, month, transactionReport.getLastUpdationDate(), transactionReportType, transactionDataSource ) ) {
			
			logger.log( Level.INFO, "(Re-)Generating report for Year " + year + ", Month " + month + ", TransactionItemTypeId " + transactionItemTypeId + ", TransactionReportType " + transactionReportType );
			
			Date transactionDateStart = DateUtil.getDateFor( year, month, 1 );
			Date transactionDateEnd = DateUtil.getDateFor( year, month + 1, 1 );
			
			Amount amount;
			if( transactionReportType == TransactionReportType.PERODIC ) {
				
				amount = new Amount( 0 );
				
			} else if( transactionReportType == TransactionReportType.CUMULATIVE ) {

				TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
				transactionItemQuery.setTransactionDate( null, false, transactionDateStart, false );
				List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute( 0 , 1 );

				if( transactionItemList.size() == 0 ) {
					amount = initialAmount;
				} else {
					TransactionReportJDO previousMonthTransactionReport = getMonthlyReport(
							year, month - 1, transactionItemTypeId,
							initialAmount,
							transactionReportType,
							transactionDataSource );
					amount = previousMonthTransactionReport.getAmount();
				}
			
			} else {
				
				throw new UnsupportedOperationException( "TransactionReportType '" + transactionReportType + "' is not yet supported." );
			
			}
			
			TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
			transactionItemQuery.setTransactionItemTypeId( transactionItemTypeId );
			transactionItemQuery.setTransactionDate( transactionDateStart, true, transactionDateEnd, false );
			List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

			for( TransactionItemJDO transactionItem : transactionItemList )
				if( transactionItem.getState() != TransactionState.DELETED )
					amount = amount.add( transactionItem.getAmount() );
			
			transactionReport.setAmount( amount );
			transactionReport.setLastUpdationDate( new Date( new Date().getTime() - 1000 ) );
			transactionDataSource.persistTransactionReport( transactionReport );
			
		} else {
			
			logger.log( Level.INFO, "Returning pre-generated report for Year " + year + ", Month " + month + ", TransactionItemTypeId " + transactionItemTypeId + ", TransactionReportType " + transactionReportType );

		}

		return transactionReport;
	}

	private boolean isReportRegenerationRequired(
			int year, Date lastUpdationDate, 
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {

		return isReportRegenerationRequired( year, -1, lastUpdationDate, transactionReportType, transactionDataSource );
	}

	private boolean isReportRegenerationRequired(
			int year, int month, Date lastUpdationDate,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		Date transactionDateStart;
		Date transactionDateEnd;
		if( month == -1 ) {
			transactionDateStart = DateUtil.getDateFor( year, 0, 1 );
			transactionDateEnd = DateUtil.getDateFor( year + 1, 0, 1 );
		} else {
			transactionDateStart = DateUtil.getDateFor( year, month, 1 );
			transactionDateEnd = DateUtil.getDateFor( year, month + 1, 1 );
		}
		
		TransactionItemQuery query = transactionDataSource.newTransactionItemQuery();
		if( transactionReportType == TransactionReportType.PERODIC )
			query.setTransactionDate( transactionDateStart, true, transactionDateEnd, false );
		else if( transactionReportType == TransactionReportType.CUMULATIVE )
			query.setTransactionDate( null, false, transactionDateEnd, false );
		
		query.orderByTransactionDate( true );
		query.orderByLastupdationDate( false );

		List<TransactionItemJDO> transactionItemList = query.execute( 0, 1 );
		
		return transactionItemList.size() != 0
				&& transactionItemList.get( 0 ).getLastUpdationDate().after( lastUpdationDate );
	}
	
	private Map<String, TransactionItemTypeData> loadTransactionItemTypeIdToTransactionItemTypeDataMap() {
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();
		// TODO: Cache the map in MemCache
		Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap = loadTransactionItemTypeIdToTransactionItemTypeDataMap( transactionDataSource );
		transactionDataSource.close();
		return transactionItemTypeIdToTransactionItemTypeDataMap;
	}
	
	private Map<String, TransactionItemTypeData> loadTransactionItemTypeIdToTransactionItemTypeDataMap( TransactionDataSource transactionDataSource ) {
		
		TransactionItemTypeQuery transactionItemTypeQuery = transactionDataSource.newTransactionItemTypeQuery();
		List<TransactionItemTypeJDO> transactionItemTypeList = transactionItemTypeQuery.execute();
		
		// TODO: Cache the map in MemCache
		Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap = new LinkedHashMap<>( transactionItemTypeList.size() );
		
		for( TransactionItemTypeJDO transactionItemType : transactionItemTypeList ) {
			TransactionItemTypeData transactionItemTypeData = new TransactionItemTypeData();
			transactionItemTypeData.setId( transactionItemType.getId() );
			transactionItemTypeData.setTitle( transactionItemType.getTitle() );
			transactionItemTypeData.setInitialAmount( transactionItemType.getInitialAmount() );
			transactionItemTypeData.setTransactionReportType( transactionItemType.getTransactionReportType() );
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
		
		return transactionItemTypeIdToTransactionItemTypeDataMap;
	}
	
}
