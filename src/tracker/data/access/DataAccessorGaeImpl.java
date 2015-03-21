package tracker.data.access;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.Query;

import tracker.commons.shared.TransactionFilter;
import tracker.data.access.gae.TransactionEntity;
import tracker.data.access.gae.TransactionItemEntity;
import tracker.data.access.gae.TransactionItemTypeEntity;
import tracker.data.access.gae.TransactionReportEntity;
import tracker.data.transfer.Transaction;
import tracker.data.transfer.TransactionItem;
import tracker.data.transfer.TransactionItemType;
import tracker.datasource.TransactionItemQuery;
import tracker.datasource.TransactionItemQueryGAEImpl;
import tracker.datasource.TransactionItemTypeQuery;
import tracker.datasource.TransactionItemTypeQueryGAEImpl;
import tracker.datasource.TransactionQuery;
import tracker.datasource.TransactionQueryGAEImpl;
import tracker.datasource.TransactionReportQuery;
import tracker.datasource.TransactionReportQueryGAEImpl;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.GaeQueryBuilder;
import com.claymus.data.access.GaeQueryBuilder.Operator;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

@SuppressWarnings("serial")
public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {
	
	private static final Logger logger =
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );

	
	@Override
	public Transaction newTransaction() {
		return new TransactionEntity();
	}
	
	@Override
	public TransactionEntity getTransaction( Long id ) {
		return getEntity( TransactionEntity.class, id );
	}

	@Override
	public TransactionEntity getTransaction( String id ) {
		return getEntity( TransactionEntity.class, KeyFactory.stringToKey( id ) );
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataListCursorTuple<Transaction> getTransactionList(
			TransactionFilter trFilter, String cursorStr, Integer resultCount ) {
		
		Date trDateStart = trFilter.getTransactionDateStart();
		Date trDateEnd = trFilter.getTransactionDateEnd();

		Date creationDateStart = trFilter.getCreationDateStart();
		Date creationDateEnd = trFilter.getCreationDateEnd();

		
		// Checking for valid transactionDate range 
		if( trDateStart != null
				&& trDateEnd != null
				&& trDateStart.equals( trDateEnd )
				&& ( !trFilter.isTransactionDateStartInclusive() || !trFilter.isTransactionDateEndInclusive() ) ) {
			
			logger.log( Level.INFO, "Not enough time range for transactionDate. Returning empty transactionDataList." );
			return new DataListCursorTuple<>( new LinkedList<Transaction>(), null );
		}
		
		// Checking for valid creationDate range 
		if( creationDateStart != null
				&& creationDateEnd != null
				&& creationDateStart.equals( creationDateEnd )
				&& ( !trFilter.isCreationDateStartInclusive() || !trFilter.isCreationDateEndInclusive() ) ) {
		
			logger.log( Level.INFO, "Not enough time range for creationDate. Returning empty transactionDataList." );
			return new DataListCursorTuple<>( new LinkedList<Transaction>(), null );
		}		
		

		GaeQueryBuilder gaeQueryBuilder =
				trFilter.getTransactionItemTypeId() == null
						? new GaeQueryBuilder( pm.newQuery( TransactionEntity.class ) )
						: new GaeQueryBuilder( pm.newQuery( TransactionItemEntity.class ) );

						
		if( trFilter.getTransactionItemTypeId() != null )
			gaeQueryBuilder.addFilter( "transactionItemTypeId", trFilter.getTransactionItemTypeId() );
		
		
		if( trDateStart != null )
			gaeQueryBuilder.addFilter( "transactionDate", trDateStart, trFilter.isTransactionDateStartInclusive() ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
		
		if( trDateEnd != null )
			gaeQueryBuilder.addFilter( "transactionDate", trDateEnd, trFilter.isTransactionDateEndInclusive() ? Operator.LESS_THAN_OR_EQUAL : Operator.LESS_THAN );

		if( creationDateStart != null )
			gaeQueryBuilder.addFilter( "creationDate", creationDateStart, trFilter.isCreationDateStartInclusive() ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );

		if( creationDateEnd != null )
			gaeQueryBuilder.addFilter( "creationDate", creationDateEnd, trFilter.isCreationDateEndInclusive() ? Operator.LESS_THAN_OR_EQUAL : Operator.LESS_THAN );
	

		if( trFilter.getTransactionDateChronologicalOrder() != null )
			gaeQueryBuilder.addOrdering( "transactionDate", trFilter.getTransactionDateChronologicalOrder() );
			
		if( trFilter.getCreationDateChronologicalOrder() != null )
			gaeQueryBuilder.addOrdering( "creationDate", trFilter.getCreationDateChronologicalOrder() );
		
		
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );

		
		Query query = gaeQueryBuilder.build();
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions( extensionMap );
		}

		
		if( trFilter.getTransactionItemTypeId() == null ) {
			List<Transaction> trList = (List<Transaction>) query.executeWithMap(
					gaeQueryBuilder.getParamNameValueMap() );
			Cursor cursor = JDOCursorHelper.getCursor( trList );

			return new DataListCursorTuple<>(
					(List<Transaction>) pm.detachCopyAll( trList ),
					cursor == null ? null : cursor.toWebSafeString() );
		
		} else {
			List<TransactionItem> triList = (List<TransactionItem>) query.executeWithMap(
					gaeQueryBuilder.getParamNameValueMap() );
			Cursor cursor = JDOCursorHelper.getCursor( triList );
			
			List<String> trIdList = new LinkedList<>();
			for( TransactionItem tri : triList )
				if( ! trIdList.contains( tri.getTransactionId() ) )
					trIdList.add( tri.getTransactionId() );
					
			List<Transaction> trList = new ArrayList<>( triList.size() );
			for( String trId : trIdList )
				trList.add( getTransaction( trId ) );

			return new DataListCursorTuple<>(
					trList,
					cursor == null ? null : cursor.toWebSafeString() );
		}
		
	}


	@Override
	public TransactionItem newTransactionItem() {
		return new TransactionItemEntity();
	}
	
	@Override
	public TransactionItem getTransactionItem( Long id ) {
		return getEntity( TransactionItemEntity.class, id );
	}

	@Override
	public TransactionItem getTransactionItem( String id ) {
		return getEntity( TransactionItemEntity.class, KeyFactory.stringToKey( id ) );
	}

	@Override
	public List<TransactionItem> getTransactionItemList( Long trId ) {
		Key key = KeyFactory.createKey( TransactionEntity.class.getSimpleName(), trId );
		String encodedTrId = KeyFactory.keyToString( key );
		return getTransactionItemList( encodedTrId );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionItem> getTransactionItemList( String encodedTrId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( TransactionItemEntity.class ) )
						.addFilter( "transactionId", encodedTrId )
						.build();
		
		List<TransactionItem> triList = (List<TransactionItem>) query.execute( encodedTrId );
		return (List<TransactionItem>) pm.detachCopyAll( triList );
	}

	
	public List<TransactionItemType> getTransactionItemTypeList() {
		TransactionItemTypeDB transactionItemTypeDBValues[] = TransactionItemTypeDB.values();
		List<TransactionItemType> transactionItemTypeList = new ArrayList<>( transactionItemTypeDBValues.length );
		for( TransactionItemTypeDB transactionItemTypeDBValue : transactionItemTypeDBValues )
			transactionItemTypeList.add( transactionItemTypeDBValue.toTransactionItemTypeJDO() );
		return transactionItemTypeList;
	}

	
	
	@Override
	public TransactionItemTypeEntity getTransactionItemType( String transactionItemTypeId ) {
		// TODO: Update this implementation once TransactionItemTypeDB is migrated to DataStore
		
		long decryptedTransactionItemTypeId = KeyFactory.stringToKey( transactionItemTypeId ).getId();
		for( TransactionItemTypeDB transactionItemTypeDB : TransactionItemTypeDB.values() )
			if( transactionItemTypeDB.getId() == decryptedTransactionItemTypeId )
				return transactionItemTypeDB.toTransactionItemTypeJDO();
		
		return null;
	}

	@Override
	public TransactionQuery newTransactionQuery() {
		return new TransactionQueryGAEImpl( this );
	}

	@Override
	public TransactionItemQuery newTransactionItemQuery() {
		return new TransactionItemQueryGAEImpl( this );
	}

	@Override
	public TransactionItemTypeQuery newTransactionItemTypeQuery() {
		return new TransactionItemTypeQueryGAEImpl( this );
	}
	
	@Override
	public TransactionReportQuery newTransactionReportQuery() {
		return new TransactionReportQueryGAEImpl( this );
	}
	
	@Override
	public Transaction persistTransaction( Transaction transaction ) {
		return createOrUpdateEntity( transaction );
	}
	
	@Override
	public TransactionItem persistTransactionItem( TransactionItem transactionItemJDO ) {
		return createOrUpdateEntity( transactionItemJDO );
	}
	
	@Override
	public List<TransactionItem> persistTransactionItemList( List<TransactionItem> transactionItemList ) {
		return (List<TransactionItem>) createOrUpdateEntityList( transactionItemList );
	}

	@Override
	public TransactionReportEntity persistTransactionReport( TransactionReportEntity transactionReportJDO ) {
		return createOrUpdateEntity( transactionReportJDO );
	}
	
	@Override
	public List<TransactionReportEntity> persistTransactionReportList( List<TransactionReportEntity> transactionReportJDOList ) {
		return (List<TransactionReportEntity>) createOrUpdateEntityList( transactionReportJDOList );
	}

}
