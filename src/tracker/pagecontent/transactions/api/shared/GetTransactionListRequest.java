package tracker.pagecontent.transactions.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetTransactionListRequest extends GenericRequest {

	private Boolean trDateOrder;

	private Boolean creationDateOrder;

	private String cursor;
	
	private Integer resultCount;
	
	
	public Boolean getTrDateOrder() {
		return trDateOrder;
	}

	public void setTrDateOrder( Boolean trDateOrder ) {
		this.trDateOrder = trDateOrder;
	}

	public Boolean getCreationDateOrder() {
		return creationDateOrder;
	}

	public void setCreationDateOrder( Boolean creationDateOrder ) {
		this.creationDateOrder = creationDateOrder;
	}

	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}

}
