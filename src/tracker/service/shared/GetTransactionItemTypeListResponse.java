package tracker.service.shared;

import java.util.List;

import tracker.service.shared.data.TransactionItemTypeData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionItemTypeListResponse implements IsSerializable {

	private List<TransactionItemTypeData> transactionItemTypeDataList;

	
	public List<TransactionItemTypeData> getTransactionItemTypeDataList() {
		return this.transactionItemTypeDataList;
	}

	public void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		this.transactionItemTypeDataList = transactionItemTypeDataList;
	}
	
}
