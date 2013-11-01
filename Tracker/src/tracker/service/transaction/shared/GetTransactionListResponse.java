package tracker.service.transaction.shared;

import java.util.List;

import antshpra.gwt.rpc.shared.Response;

public class GetTransactionListResponse extends Response {

	private static final long serialVersionUID = 2346548850329557113L;

	private List<TransactionData> transactionDataList;

	
	public List<TransactionData> getTransactionDataList() {
		return this.transactionDataList;
	}

	public void setTransactionDataList( List<TransactionData> transactionDataList ) {
		this.transactionDataList = transactionDataList;
	}
	
}
