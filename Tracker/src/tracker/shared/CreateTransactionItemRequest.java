/**
 * 
 */
package tracker.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings( "serial" )
public class CreateTransactionItemRequest implements Serializable {

	private String description;
	
	private Date transactionDate;

	
	public String getDescription() { return this.description; }
	
	public Date getTransactionDate() { return this.transactionDate; }
	
	
	public void setDescription( String description ) {
		if( description == null || description.trim().length() == 0 )
			throw new IllegalArgumentException( "Please provide some description." );
		this.description = description.trim();
	}
	
	public void setTransactionDate( Date transactionDate ) {
		if( this.transactionDate == null)
			throw new IllegalArgumentException( "Please selecat a date." );
		this.transactionDate = transactionDate;
	}
	
	
	public void validate() {
		if( this.description == null )
			throw new IllegalArgumentException( "Transaction description can not be null." );
		else if( this.description.trim().length() == 0 )
			throw new IllegalArgumentException( "Transaction description can not be an empty string." );
		
		if( this.transactionDate == null )
			throw new IllegalArgumentException( "A valid tranaction date must be provided." );
	}
	
}
