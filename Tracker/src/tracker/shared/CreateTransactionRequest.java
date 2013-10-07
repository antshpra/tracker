/**
 * 
 */
package tracker.shared;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

@SuppressWarnings( "serial" )
public class CreateTransactionRequest extends Request {

	private Date transactionDate;

	@RequiredField
	private String description;

	private List<CreateTransactionItemRequest> createTransactionItemRequestList;
	
	
	public Date getTransactionDate() { return  this.transactionDate; }
	
	public String getDescription() { return this.description; }

	public List<CreateTransactionItemRequest> getCreateTransactionItemRequestList() { return this.createTransactionItemRequestList; };
	
	
	public void setTransactionDate( Date transactionDate ) {
		assertNonNull( transactionDate );
		this.transactionDate = transactionDate;
	}
	
	public void setDescription( String description ) {
		assertNonNull( description );
		assertNonEmpty( description );
		this.description = description.trim();
	}
	
	public void addCreateTransactionItemRequest( CreateTransactionItemRequest createTransactionItemRequest ) {
		if( this.createTransactionItemRequestList == null )
			this.createTransactionItemRequestList = new LinkedList<CreateTransactionItemRequest>();
		this.createTransactionItemRequestList.add( createTransactionItemRequest );
	}
	
}
