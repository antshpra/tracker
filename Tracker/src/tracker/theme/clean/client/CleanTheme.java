package tracker.theme.clean.client;

import tracker.theme.client.Theme;
import tracker.theme.client.TransactionModuleStyle;

import com.google.gwt.resources.client.ClientBundle;

public interface CleanTheme extends Theme, ClientBundle {
	
	@Source( { "../../../../antshpra/gwt/theme/Clean.css", "TransactionModule.css" } )
	TransactionModuleStyle getTransactionModuleStyle();

}