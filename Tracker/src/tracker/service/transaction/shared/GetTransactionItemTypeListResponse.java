package tracker.service.transaction.shared;

import java.util.List;

import antshpra.gwt.rpc.shared.Response;

public class GetTransactionItemTypeListResponse extends Response {

	private static final long serialVersionUID = 2961346955590909871L;

	private List<TransactionItemTypeData> transactionItemTypeDataList;

	
	public List<TransactionItemTypeData> getTransactionItemTypeDataList() {
		return this.transactionItemTypeDataList;
	}

	public void setTransactionItemTypeDataList( List<TransactionItemTypeData> transactionItemTypeDataList ) {
		this.transactionItemTypeDataList = transactionItemTypeDataList;
	}
	
}
