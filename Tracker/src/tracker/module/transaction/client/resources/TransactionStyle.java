package tracker.module.transaction.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface TransactionStyle extends CssResource {

	String transaction();

	String transactionItem();
	
	String highlighted();
	
	String detailPanel();
	
	String dateTime();
	
	String detail();
	
}
