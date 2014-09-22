package tracker.commons.server;

import java.util.List;
import java.util.Map;

import tracker.commons.shared.TransactionReportType;
import tracker.data.access.gae.TransactionEntity;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionReportJDO;
import tracker.service.shared.data.TransactionData;
import tracker.service.shared.data.TransactionItemData;
import tracker.service.shared.data.TransactionItemTypeData;
import tracker.service.transactionreport.shared.data.TransactionReportData;

public class JDOToDataConverter {
	
	public static TransactionData convert(
			TransactionEntity transaction,
			List<TransactionItemJDO> transactionItemList,
			Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		TransactionData transactionData = new TransactionData();
		transactionData.setId( transaction.getId() );
		transactionData.setTransactionDate( transaction.getTransactionDate() );
		transactionData.setDescription( transaction.getDescription() );
		transactionData.setCreationDate( transaction.getCreationDate() );
		transactionData.setCreatedBy( transaction.getCreatedBy() );
		
		if( transactionItemList != null ) {
			for( TransactionItemJDO transactionItem : transactionItemList ) {
				TransactionItemData transactionItemData = convert(
						transactionItem,
						transactionData,
						transactionItemTypeIdToTransactionItemTypeDataMap );
				transactionData.addTransactionItemData( transactionItemData );
			}
		}
		
		return transactionData;
	}

	public static TransactionItemData convert(
			TransactionItemJDO transactionItem,
			TransactionData transactionData,
			Map<String, TransactionItemTypeData> transactionItemTypeIdToTransactionItemTypeDataMap ) {
		
		TransactionItemData transactionItemData = new TransactionItemData();
		transactionItemData.setId( transactionItem.getId() );
		transactionItemData.setTransactionId( transactionItem.getTransactionId() );
		transactionItemData.setTransactionItemType(
				transactionItemTypeIdToTransactionItemTypeDataMap.get(
						transactionItem.getTransactionItemTypeId() ) );
		transactionItemData.setTransactionDate( transactionItem.getTransactionDate() );
		transactionItemData.setAmount( transactionItem.getAmount() );
		if( transactionItem.getOrder() != null )
			transactionItemData.setNote(
					transactionItem.getOrder() + ". "
					+ ( transactionItem.getNote() == null ? "" : transactionItem.getNote() ) );
		else
			transactionItemData.setNote( transactionItem.getNote() );
		transactionItemData.setCreationDate( transactionItem.getCreationDate() );
		transactionItemData.setCreatedBy( transactionItem.getCreatedBy() );
		transactionItemData.setTransactionData( transactionData );
		
		return transactionItemData;
	}
	
	public static TransactionReportData convert(
			TransactionReportJDO transactionReportJDO,
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
