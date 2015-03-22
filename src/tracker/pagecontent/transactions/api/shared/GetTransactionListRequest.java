package tracker.pagecontent.transactions.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetTransactionListRequest extends GenericRequest {

	private String cursor;
	
	private Integer resultCount;
	
	
	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}

}
