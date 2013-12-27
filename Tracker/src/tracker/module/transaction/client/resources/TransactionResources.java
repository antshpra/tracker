package tracker.module.transaction.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface TransactionResources extends ClientBundle {
	
	public static final TransactionResources INSTANCE = GWT.create( TransactionResources.class );

	@Source( { "../../../../../antshpra/gwt/style/Style.css", "Transaction.css" } )
	TransactionStyle css();

}