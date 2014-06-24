package tracker.service.transactionreport.server;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import tracker.commons.server.DateUtil;
import tracker.commons.server.JDOToDataConverter;
import tracker.commons.shared.Amount;
import tracker.commons.shared.TransactionReportType;
import tracker.commons.shared.TransactionReportUtil;
import tracker.commons.shared.TransactionState;
import tracker.commons.shared.YearType;
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
	
	private static final String MONTH_MIN_INDEX = TransactionReportUtil.getTransactionReportIndex( 1000, 0 );
	private static final String MONTH_MAX_INDEX = TransactionReportUtil.getTransactionReportIndex( 9999, 11 );
	

	@Override
	public GetYearlyReportResponse getYearlyReport( GetYearlyReportRequest request )
			throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		logger.log( Level.INFO, request.toString() );
		
		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

		TransactionItemTypeData transactionItemTypeData = loadTransactionItemTypeIdToTransactionItemTypeDataMap( transactionDataSource ).get( request.getTransactionItemTypeId() );

		updateReports( transactionItemTypeData, transactionDataSource );

		// TODO : Use caching. Return cached response if updateReports returns false.
		
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

		int year = request.isAscendingOrder() ? yearFrom : yearTo;
		while( year >= yearFrom && year <= yearTo && transactionReportDataList.size() < request.getPageSize() ) {
			
			TransactionReportData transactionReportData = getYearlyReportData(
					year, request.getYearType(),
					transactionItemTypeData,
					transactionDataSource );
			transactionReportDataList.add( transactionReportData );
				
			year = request.isAscendingOrder() ? year + 1 : year - 1 ;

		}
		
		transactionDataSource.close();
		
		return response;
	}

	@Override
	public GetMonthlyReportResponse getMonthlyReport( GetMonthlyReportRequest request )
			throws InvalidRequestException, ServerException {
		
		RequestValidator.validate( request );

		logger.log( Level.INFO, request.toString() );

		TransactionDataSource transactionDataSource = transactionDataSourceFactory.getTransactionDataSource();

		TransactionItemTypeData transactionItemTypeData = loadTransactionItemTypeIdToTransactionItemTypeDataMap( transactionDataSource ).get( request.getTransactionItemTypeId() );

		updateReports( transactionItemTypeData, transactionDataSource );
		
		// TODO : Use caching. Return cached response if updateReports returns false.
		
		GetMonthlyReportResponse response = new GetMonthlyReportResponse();
		List<TransactionReportData> transactionReportDataList = new LinkedList<>();
		response.setTransactionReportDataList( transactionReportDataList );
		
		for( int i = 0; i < 12; i++ ) {
			TransactionReportData transactionReportData = getMonthlyReportData(
					request.getYear(), i, request.getYearType(),
					transactionItemTypeData,
					transactionDataSource ); 
			transactionReportDataList.add( transactionReportData );
		}
		
		transactionDataSource.close();

		return response;
	}
	

	private boolean updateReports(
			TransactionItemTypeData transactionItemTypeData,
			TransactionDataSource transactionDataSource ) {
		
		boolean ret = updateReports(
				transactionItemTypeData.getId(),
				transactionItemTypeData.getTransactionReportType(),
				transactionDataSource );

		for( TransactionItemTypeData transactionItemTypeDataChild : transactionItemTypeData.getChildren() )
			ret = updateReports( transactionItemTypeDataChild, transactionDataSource ) && ret;
		
		return ret;
	}

	private boolean updateReports(
			String transactionItemTypeId,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		TransactionReportQuery transactionReportQuery = transactionDataSource.newTransactionReportQuery();
		transactionReportQuery.setTransactionItemTypeId( transactionItemTypeId );
		transactionReportQuery.setReportType( transactionReportType );
		transactionReportQuery.orderByLastUpdationDate( false );
		List<TransactionReportJDO> transactionReportList = transactionReportQuery.execute( 0, 1 );
		
		// We have to update reports for all additions/updations in TransactionItems happened since last update in TransactionReports
		Date startDate = transactionReportList.size() == 0 ? null : transactionReportList.get( 0 ).getLastUpdationDate();
		Date endDate = new Date( new Date().getTime() - 10000 );
		
		// Fetching list of TransactionItems created/updated since last update in TransactionReports
		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
		transactionItemQuery.setTransactionItemTypeId( transactionItemTypeId );
		transactionItemQuery.setLastupdationDate( startDate, false, endDate, true );
		transactionItemQuery.orderByLastupdationDate( true );
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

		if( transactionItemList.size() == 0 ) {
			logger.log( Level.INFO, "No updation required for"
					+ "\n\t transactionItemTypeId = " + transactionItemTypeId
					+ "\n\t transactionReportType = " + transactionReportType );
			return false;
		} else {
			logger.log( Level.INFO, "Updating reports for"
					+ "\n\t transactionItemTypeId = " + transactionItemTypeId
					+ "\n\t transactionReportType = " + transactionReportType );
		}

		// Last updation date for TransactionReports
		Date lastUpdationDate = transactionItemList.get( transactionItemList.size() - 1 ).getLastUpdationDate();

		// Create a set of months for which TransactionReport creation/updation is required
		SortedSet<Integer> yearMonthList = new TreeSet<>();
		for( TransactionItemJDO transactionItem : transactionItemList ) {
			int year = DateUtil.getYear( transactionItem.getTransactionDate() );
			int month = DateUtil.getMonth( transactionItem.getTransactionDate() );
			yearMonthList.add( year * 100 + month );
		}

		// For cumulative TransactionReportType, all future TransactionReports must be updated
		if( transactionReportType == TransactionReportType.CUMULATIVE ) {
			int year = yearMonthList.first() / 100;
			int month = yearMonthList.first() % 100;
			
			transactionReportQuery = transactionDataSource.newTransactionReportQuery();
			transactionReportQuery.setIndexRange(
					TransactionReportUtil.getTransactionReportIndex( year, month ), false,
					MONTH_MAX_INDEX, true );
			transactionReportQuery.setTransactionItemTypeId( transactionItemTypeId );
			transactionReportQuery.setReportType( transactionReportType );
			transactionReportQuery.orderByIndex( true );
			transactionReportList = transactionReportQuery.execute();

			for( TransactionReportJDO transactionReport : transactionReportList )
				yearMonthList.add(
						TransactionReportUtil.getTransactionReportYear( transactionReport.getIndex() ) * 100 +
						TransactionReportUtil.getTransactionReportMonth( transactionReport.getIndex() ) );
		}
		
		List<TransactionReportJDO> updatedTransactionReportList = new LinkedList<>();
		
		Iterator<Integer> iterator = yearMonthList.iterator();
		while ( iterator.hasNext() ) {
			int yearMonth = iterator.next();
			int year = yearMonth / 100;
			int month = yearMonth % 100;
			TransactionReportJDO transactionReport = createOrUpdateMonthlyReport(
					year, month,
					transactionItemTypeId,
					transactionReportType,
					transactionDataSource );
			transactionReport.setLastUpdationDate( lastUpdationDate );
			updatedTransactionReportList.add( transactionReport );
		}

		transactionDataSource.persistTransactionReportList( updatedTransactionReportList );
		
		return true;
	}
	
	TransactionReportJDO createOrUpdateMonthlyReport(
			int year, int month,
			String transactionItemTypeId,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		logger.log( Level.INFO, "Creating/Updating report for"
				+ "\n\t year = " + year + ", month = " + month
				+ "\n\t transactionItemTypeId = " + transactionItemTypeId
				+ "\n\t transactionReportType = " + transactionReportType );

		String transactionReportIndex = TransactionReportUtil.getTransactionReportIndex( year, month );
		TransactionReportJDO transactionReport = getTransactionReport(
				transactionReportIndex,
				transactionItemTypeId,
				transactionReportType,
				transactionDataSource );
		
		if( transactionReport == null )
			transactionReport = createTransactionReport(
					transactionReportIndex,
					transactionItemTypeId,
					transactionReportType,
					transactionDataSource );
		
		Amount amount;
		if( transactionReportType == TransactionReportType.PERODIC ) {
			
			amount = new Amount( 0 );
			
		} else if( transactionReportType == TransactionReportType.CUMULATIVE ) {

			TransactionReportQuery transactionReportQuery = transactionDataSource.newTransactionReportQuery();
			transactionReportQuery.setIndexRange(
					MONTH_MIN_INDEX, true,
					transactionReportIndex, false );
			transactionReportQuery.setTransactionItemTypeId( transactionItemTypeId );
			transactionReportQuery.setReportType( TransactionReportType.CUMULATIVE );
			transactionReportQuery.orderByIndex( false );
			transactionReportQuery.orderByLastUpdationDate( false );
			List<TransactionReportJDO> transactionReportList = transactionReportQuery.execute( 0 , 1 );

			if( transactionReportList.size() == 0 )
				amount = new Amount( 0 );
			else
				amount = transactionReportList.get( 0 ).getAmount();
		
		} else {
			
			throw new UnsupportedOperationException( "TransactionReportType '" + transactionReportType + "' is not yet supported." );
		
		}

		TransactionItemQuery transactionItemQuery = transactionDataSource.newTransactionItemQuery();
		transactionItemQuery.setTransactionItemTypeId( transactionItemTypeId );
		transactionItemQuery.setTransactionDate(
				DateUtil.getDateFor( year, month, 1 ), true,
				DateUtil.getDateFor( year, month + 1, 1 ), false );
		transactionItemQuery.orderByTransactionDate( true );
		
		List<TransactionItemJDO> transactionItemList = transactionItemQuery.execute();

		for( TransactionItemJDO transactionItem : transactionItemList )
			if( transactionItem.getState() != TransactionState.DELETED )
				amount = amount.add( transactionItem.getAmount() );
		
		transactionReport.setAmount( amount );
		
		return transactionDataSource.persistTransactionReport( transactionReport );
	}


	TransactionReportData getYearlyReportData(
			int year, YearType yearType,
			TransactionItemTypeData transactionItemTypeData,
			TransactionDataSource transactionDataSource) {
		
		TransactionReportJDO transactionReport = createTransactionReport(
				TransactionReportUtil.getTransactionReportIndex( year ),
				transactionItemTypeData.getId(),
				transactionItemTypeData.getTransactionReportType(),
				transactionDataSource );
		
		if( transactionItemTypeData.getTransactionReportType() == TransactionReportType.PERODIC ) {
			
			for( int i = 0; i < 12; i++ ) {
				int month;
				if( yearType == YearType.CALENDAR ) {
					month = i;
				} else if( yearType == YearType.FINANCIAL ) {
					month = i + 3;
				} else {
					throw new UnsupportedOperationException( "YearType '" + yearType + "' is not yet supported." );
				}
	
				TransactionReportJDO monthlyTransactionReport = getTransactionReport(
						TransactionReportUtil.getTransactionReportIndex( year, month ),
						transactionItemTypeData.getId(),
						transactionItemTypeData.getTransactionReportType(),
						transactionDataSource );
				
				if( monthlyTransactionReport != null )
					transactionReport.setAmount( transactionReport.getAmount().add( monthlyTransactionReport.getAmount() ) );
			}
		
		} else if( transactionItemTypeData.getTransactionReportType() == TransactionReportType.CUMULATIVE ) { 
			
			String transactionReportIndex; 
			if( yearType == YearType.CALENDAR ) {
				transactionReportIndex = TransactionReportUtil.getTransactionReportIndex( year, 11 );
			} else if( yearType == YearType.FINANCIAL ) {
				transactionReportIndex = TransactionReportUtil.getTransactionReportIndex( year, 14 );
			} else {
				throw new UnsupportedOperationException( "YearType '" + yearType + "' is not yet supported." );
			}
			
			TransactionReportQuery transactionReportQuery = transactionDataSource.newTransactionReportQuery();
			transactionReportQuery.setIndexRange( MONTH_MIN_INDEX, true, transactionReportIndex, true );
			transactionReportQuery.setTransactionItemTypeId( transactionItemTypeData.getId() );
			transactionReportQuery.setReportType( transactionItemTypeData.getTransactionReportType() );
			transactionReportQuery.orderByIndex( false );
			transactionReportQuery.orderByLastUpdationDate( false );
			List<TransactionReportJDO> transactionReportList = transactionReportQuery.execute( 0, 1 );

			if( transactionReportList.size() != 0 )
				transactionReport.setAmount( transactionReportList.get( 0 ).getAmount() );
			
		} else {
			
			throw new UnsupportedOperationException( "TransactionReportType '" + transactionItemTypeData.getTransactionReportType() + "' is not yet supported." );
		
		}

		TransactionReportData transactionReportData = JDOToDataConverter.convert( transactionReport, transactionItemTypeData );
		
		for( TransactionItemTypeData transactionItemTypeChildData : transactionItemTypeData.getChildren() )
			transactionReportData.addChild( getYearlyReportData( year, yearType, transactionItemTypeChildData, transactionDataSource ) );
		
		return transactionReportData;
	}
	
	TransactionReportData getMonthlyReportData(
			int year, int month, YearType yearType,
			TransactionItemTypeData transactionItemTypeData,
			TransactionDataSource transactionDataSource ) {

		if( transactionItemTypeData.getTransactionReportType() != TransactionReportType.PERODIC 
				&& transactionItemTypeData.getTransactionReportType() != TransactionReportType.CUMULATIVE )
			throw new UnsupportedOperationException( "TransactionReportType '" + transactionItemTypeData.getTransactionReportType() + "' is not yet supported." );
		
		int reportMonth;
		if( yearType == YearType.CALENDAR ) {
			reportMonth = month;
		} else if( yearType == YearType.FINANCIAL ) {
			reportMonth = month + 3;
		} else {
			throw new UnsupportedOperationException( "YearType '" + yearType + "' is not yet supported." );
		}
		
		TransactionReportJDO transactionReport = getTransactionReport(
				TransactionReportUtil.getTransactionReportIndex( year, reportMonth ),
				transactionItemTypeData.getId(),
				transactionItemTypeData.getTransactionReportType(),
				transactionDataSource );
		
		if( transactionReport == null )
			transactionReport = createTransactionReport(
					TransactionReportUtil.getTransactionReportIndex( year, reportMonth ),
					transactionItemTypeData.getId(),
					transactionItemTypeData.getTransactionReportType(),
					transactionDataSource );

		TransactionReportData transactionReportData = JDOToDataConverter.convert( transactionReport, transactionItemTypeData );
		
		for( TransactionItemTypeData transactionItemTypeDataChild : transactionItemTypeData.getChildren() )
			transactionReportData.addChild( getMonthlyReportData( year, month, yearType, transactionItemTypeDataChild, transactionDataSource ) );
		
		return transactionReportData;
	}

	
	TransactionReportJDO getTransactionReport(
			String transactionReportIndex,
			String transactionItemTypeId,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		TransactionReportQuery transactionReportQuery = transactionDataSource.newTransactionReportQuery();
		transactionReportQuery.setIndex( transactionReportIndex );
		transactionReportQuery.setTransactionItemTypeId( transactionItemTypeId );
		transactionReportQuery.setReportType( transactionReportType );
		transactionReportQuery.orderByLastUpdationDate( false );
		List<TransactionReportJDO> transactionReportList = transactionReportQuery.execute( 0, 1 );
		
		return transactionReportList.size() == 0 ? null : transactionReportList.get( 0 );
	}

	
	TransactionReportJDO createTransactionReport(
			String transactionReportIndex,
			String transactionItemTypeId,
			TransactionReportType transactionReportType,
			TransactionDataSource transactionDataSource ) {
		
		TransactionReportJDO transactionReport = new TransactionReportJDO();
		transactionReport.setIndex( transactionReportIndex );
		transactionReport.setTransactionItemTypeId( transactionItemTypeId );
		transactionReport.setType( transactionReportType );
		transactionReport.setAmount( new Amount( 0 ) );
		transactionReport.setLastUpdationDate( new Date( 0 ) );

		return transactionReport;
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
