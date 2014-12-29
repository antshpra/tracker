package tracker.theme.clean.client;

import tracker.theme.client.Theme;
import tracker.theme.client.TransactionModuleStyle;
import tracker.theme.client.TransactionReportModuleStyle;

import com.google.gwt.resources.client.ClientBundle;

@Deprecated
public interface CleanTheme extends Theme, ClientBundle {
	
	@Source( { "../../../../antshpra/gwt/theme/Clean.css", "TransactionModule.css" } )
	TransactionModuleStyle getTransactionModuleStyle();

	@Source( { "../../../../antshpra/gwt/theme/Clean.css", "TransactionReportModule.css" } )
	TransactionReportModuleStyle getTransactionReportModuleStyle();

}