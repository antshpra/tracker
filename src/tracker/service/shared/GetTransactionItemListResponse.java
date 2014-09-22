package tracker.service.shared;

import java.util.List;

import tracker.service.shared.data.TransactionItemData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionItemListResponse implements IsSerializable {

	private List<TransactionItemData> transactionItemDataList;

	
	public List<TransactionItemData> getTransactionItemDataList() {
		return this.transactionItemDataList;
	}

	public void setTransactionItemDataList( List<TransactionItemData> transactionItemDataList ) {
		this.transactionItemDataList = transactionItemDataList;
	}
	
}
