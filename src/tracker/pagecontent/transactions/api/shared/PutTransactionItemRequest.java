package tracker.pagecontent.transactions.api.shared;

import java.util.Date;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutTransactionItemRequest extends GenericRequest {

	private String id;

	private String triTypeId;

	private Long trDate;
	
	private Long amount;
	
	private String note;

	private Integer order;
	
	
	public String getId() {
		return id;
	}

	public String getTransactionItemTypeId() {
		return triTypeId;
	}
	
	public Date getTransactionDate() {
		return new Date( trDate );
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
