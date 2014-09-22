package tracker.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Transaction extends Serializable {

	String getId();
	
	Date getTransactionDate();
	
	void setTransactionDate( Date transactionDate );
	
	String getDescription();
	
	void setDescription( String description );
	
	Date getCreationDate();

	void setCreationDate( Date creationDate );
	
	String getCreatedBy();
	
	void setCreatedBy( String createdBy );
	
}
