package tracker.service.shared;

import java.util.List;

import tracker.service.shared.data.TransactionData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionListResponse implements IsSerializable {

	private List<TransactionData> trDataList;

	private String cursor;
	
	
	@SuppressWarnings("unused")
	private GetTransactionListResponse() {}

	public GetTransactionListResponse( List<TransactionData> trDataList, String cursor ) {
		this.trDataList = trDataList;
		this.cursor = cursor;
	}
	
	
	public List<TransactionData> getTransactionDataList() {
		return this.trDataList;
	}

	public String getCursor() {
		return this.cursor;
	}

}