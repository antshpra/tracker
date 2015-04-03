package tracker.pagecontent.transactions.api.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutTransactionRequest extends GenericRequest {

	@Validate( regEx = REGEX_NON_EMPTY_STRING )
	private String id;

	@Validate( required = true, minLong = 0 )
	private Long trDate;

	@Validate( required = true, regEx = REGEX_NON_EMPTY_STRING )
	private String description;
	
	@Validate( required = true )
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
		return triList == null ? new ArrayList<PutTransactionItemRequest>( 0 ) : triList;
	}

}
