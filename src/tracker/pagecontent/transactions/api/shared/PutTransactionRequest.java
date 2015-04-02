package tracker.pagecontent.transactions.api.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutTransactionRequest extends GenericRequest {

	private String id;

	private Long trDate;
	
	private String description;
	
	private List<PutTransactionItemRequest> triList;


	public String getId() {
		return id;
	}
	
	public Date getTransactionDate() {
		return new Date( trDate );
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<PutTransactionItemRequest> getPutTransactionItemRequestList() {
		return triList == null ? new ArrayList<PutTransactionItemRequest>(0) : triList;
	}

}
