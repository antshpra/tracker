package tracker.service.shared;

import java.util.List;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionListResponse implements IsSerializable {

	private List<TransactionData> transactionDataList;

	
	public List<TransactionData> getTransactionDataList() {
		return this.transactionDataList;
	}

	public void setTransactionDataList( List<TransactionData> transactionDataList ) {
		this.transactionDataList = transactionDataList;
	}
	
}
