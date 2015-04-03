package tracker.pagecontent.transactions.api.shared;

import java.util.Date;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutTransactionItemRequest extends GenericRequest {

	@Validate( regEx = REGEX_NON_EMPTY_STRING )
	private String id;

	@Validate( required = true, regEx = REGEX_NON_EMPTY_STRING )
	private String triTypeId;

	@Validate( minLong = 0 )
	private Long trDate;
	
	@Validate( required = true )
	private Long amount;
	
	@Validate( regEx = REGEX_NON_EMPTY_STRING )
	private String note;

	@Validate( required = true )
	private Integer order;
	
	
	public String getId() {
		return id;
	}

	public String getTransactionItemTypeId() {
		return triTypeId;
	}
	
	public Date getTransactionDate() {
		return trDate == null ? null : new Date( trDate );
	}
	
	public Long getAmount() {
		return amount;
	}
	
	public String getNote() {
		return note;
	}
	
	public Integer getOrder() {
		return order;
	}

}
