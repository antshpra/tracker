package tracker.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.ClaymusHelper;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.websitewidget.header.HeaderWidget;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.servlet.ClaymusMain;

@SuppressWarnings("serial")
public class TrackerMain extends ClaymusMain {
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		HeaderWidget headerWidget = HeaderWidgetFactory.newHeaderWidget();
		headerWidget.setBrand( "Track It Up !" );
		if( ClaymusHelper.isUserAdmin() )
			headerWidget.setRightNavItems( new String[][] {
					{ "Log Out", ClaymusHelper.createLogoutURL() }
			});
		else
			headerWidget.setRightNavItems( new String[][] {
					{ "Log In", ClaymusHelper.createLoginURL() }
			});
		headerWidget.setPosition( "HEADER" );
		websiteWidgetList.add( headerWidget );

		return websiteWidgetList;
	}

	@Override
	protected String getTemplateName() {
		return "tracker/servlet/TrackerTemplate.ftl";
	}

}
