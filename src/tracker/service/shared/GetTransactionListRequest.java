package tracker.service.shared;

import tracker.commons.shared.TransactionFilter;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTransactionListRequest implements IsSerializable {
	
	private TransactionFilter transactionFilter;
	
	private String cursor = null;

	private Integer resultCount = 10;

	
	public TransactionFilter getTransactionFilter() {
		return transactionFilter;
	}

	public void setTransactionFilter( TransactionFilter transactionFilter ) {
		this.transactionFilter = transactionFilter;
	}

	public String getCursor() {
		return cursor;
	}
	
	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}
	
	public void setResultCount( Integer resultCount ) {
		this.resultCount = resultCount;
	}

}
