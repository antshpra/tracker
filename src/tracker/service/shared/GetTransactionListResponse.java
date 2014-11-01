package tracker.service.shared;

import java.util.List;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionListResponse implements IsSerializable {

	private List<TransactionData> transactionDataList;

	private String cursor;
	

	public GetTransactionListResponse(
			List<TransactionData> transactionDataList, String cursor ) {

		this.transactionDataList = transactionDataList;
		this.cursor = cursor;
	}
	
	
	public List<TransactionData> getTransactionDataList() {
		return this.transactionDataList;
	}

	public String getCursor() {
		return this.cursor;
	}

}