package tracker.commons.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import tracker.commons.shared.TransactionReportType;
import tracker.data.access.DataAccessor;
import tracker.data.access.DataAccessorFactory;
import tracker.data.access.gae.TransactionItemEntity;
import tracker.data.access.gae.TransactionReportEntity;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.data.transfer.TransactionItemType;
import tracker.service.shared.data.TransactionData;
import tracker.service.shared.data.TransactionItemData;
import tracker.service.shared.data.TransactionItemTypeData;
import tracker.service.transactionreport.shared.data.TransactionReportData;

import com.claymus.commons.client.Amount;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.Memcache;

@SuppressWarnings("serial")
public class TrackerHelper extends ClaymusHelper {
	
	protected TrackerHelper( HttpServletRequest request ) {
		super( request );
	}
	
	public static TrackerHelper get( HttpServletRequest request ) {
		Memcache memcache = DataAccessorFactory.getL1CacheAccessor();
		TrackerHelper trackerHelper = memcache.get( "TrackerHelper-" + request.hashCode() );
		if( trackerHelper == null ) {
			trackerHelper = new TrackerHelper( request );
			memcache.put( "ClaymusHelper-" + request.hashCode(), trackerHelper );
			memcache.put( "TrackerHelper-" + request.hashCode(), trackerHelper );
		}
		return trackerHelper;
	}

	
	@Deprecated
	public List<TransactionData> createTransactionDataList( List<Transaction> trList ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		List<TransactionItemType> triTypeList = dataAccessor.getTransactionItemTypeList();
		Map<String, TransactionItemTypeData> triTypeIdToTriTypeDataMap = new LinkedHashMap<>( triTypeList.size() );
		
		for( TransactionItemType triType : triTypeList ) {
			TransactionItemTypeData triTypeData = new TransactionItemTypeData();
			triTypeData.setId( triType.getId() );
			triTypeData.setTitle( triType.getTitle() );
			triTypeData.setInitialAmount( triType.getInitialAmount() );
			triTypeData.setTransactionReportType( triType.getTransactionReportType() );
			triTypeIdToTriTypeDataMap.put( triType.getId(), triTypeData );
		}

		for( TransactionItemType triType : triTypeList ) {
			String id = triType.getId();
			String parentId = triType.getParentId();
			if( parentId != null ) {
				TransactionItemTypeData triTypeData = triTypeIdToTriTypeDataMap.get( id );
				TransactionItemTypeData parentTriTypeData = triTypeIdToTriTypeDataMap.get( parentId );
				triTypeData.setParent( parentTriTypeData );
				parentTriTypeData.addChild( triTypeData );
			}
		}

		
		List<TransactionData> trDataList = new ArrayList<>( trList.size() );
		for( Transaction tr : trList ) {

			TransactionData trData = new TransactionData();
			
			trData.setId( tr.getId() );
			trData.setTransactionDate( tr.getTransactionDate() );
			trData.setDescription( tr.getDescription() );
			trData.setCreationDate( tr.getCreationDate() );
			trData.setCreatedBy( tr.getCreatedBy() );
			
			List<TransactionItem> triList = dataAccessor.getTransactionItemList( tr.getId() );
			for( TransactionItem tri : triList ) {
			
				TransactionItemData triData = new TransactionItemData();
				
				triData.setId( tri.getId() );
				triData.setTransactionId( tri.getTransactionId() );
				triData.setTransactionItemTypeId( tri.getTransactionItemTypeId() );
				triData.setTransactionItemType( triTypeIdToTriTypeDataMap.get( tri.getTransactionItemTypeId() ) );
				triData.setTransactionDate( tri.getTransactionDate() );
				triData.setAmount( new Amount( tri.getAmount() ) );
				triData.setNote( tri.getNote() );
				triData.setOrder( tri.getOrder() );
				triData.setCreationDate( tri.getCreationDate() );
				triData.setCreatedBy( tri.getCreatedBy() );
				
				trData.addTransactionItemData( triData );
			}

			trDataList.add( trData );
		}
		
		return trDataList;
	}
	
	
	@Deprecated
	public static TransactionData convert(
			Transaction transaction,
			List<TransactionItemEntity> transactionItemList,
			Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		TransactionData transactionData = new TransactionData();
		transactionData.setId( transaction.getId() );
		transactionData.setTransactionDate( transaction.getTransactionDate() );
		transactionData.setDescription( transaction.getDescription() );
		transactionData.setCreationDate( transaction.getCreationDate() );
		transactionData.setCreatedBy( transaction.getCreatedBy() );
		
		if( transactionItemList != null ) {
			for( TransactionItemEntity transactionItem : transactionItemList ) {
				TransactionItemData transactionItemData = convert(
						transactionItem,
						transactionData,
						transactionItemTypeIdToTransactionItemTypeDataMap );
				transactionData.addTransactionItemData( transactionItemData );
			}
		}
		
		return transactionData;
	}

	@Deprecated
	public static TransactionItemData convert(
			TransactionItemEntity transactionItem,
			TransactionData transactionData,
			Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		TransactionItemData transactionItemData = new TransactionItemData();
		transactionItemData.setId( transactionItem.getId() );
		transactionItemData.setTransactionId( transactionItem.getTransactionId() );
		transactionItemData.setTransactionItemType(
				transactionItemTypeIdToTransactionItemTypeDataMap.get(
						transactionItem.getTransactionItemTypeId() ) );
		transactionItemData.setTransactionDate( transactionItem.getTransactionDate() );
		transactionItemData.setAmount( new Amount( transactionItem.getAmount() ) );
		if( transactionItem.getOrder() != null )
			transactionItemData.setNote(
					transactionItem.getOrder() + ". "
					+ ( transactionItem.getNote() == null ? "" : transactionItem.getNote() ) );
		else
			transactionItemData.setNote( transactionItem.getNote() );
		transactionItemData.setOrder( transactionItem.getOrder() );
		transactionItemData.setCreationDate( transactionItem.getCreationDate() );
		transactionItemData.setCreatedBy( transactionItem.getCreatedBy() );
		
		return transactionItemData;
	}
	
	@Deprecated
	public static TransactionReportData convert(
			TransactionReportEntity transactionReportJDO,
			TransactionItemTypeData transactionItemTypeData ) {
		
		TransactionReportData transactionReportData = new TransactionReportData();
		transactionReportData.setTransactionItemTypeId( transactionItemTypeData.getId() );
		transactionReportData.setIndex( transactionReportJDO.getIndex() );
		transactionReportData.setTitle( transactionItemTypeData.getTitle() );
		if( transactionItemTypeData.getTransactionReportType() == TransactionReportType.CUMULATIVE )
			transactionReportData.setAmount( transactionReportJDO.getAmount().add( transactionItemTypeData.getInitialAmount() ) );
		else 
			transactionReportData.setAmount( transactionReportJDO.getAmount() );
		return transactionReportData;
	}
	
}
