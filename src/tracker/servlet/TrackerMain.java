package tracker.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.websitewidget.navigation.Navigation;
import com.claymus.module.websitewidget.navigation.NavigationFactory;
import com.claymus.module.websitewidget.user.UserInfoFactory;
import com.claymus.servlet.ClaymusMain;

@SuppressWarnings("serial")
public class TrackerMain extends ClaymusMain {
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		Navigation navigation = NavigationFactory.newNavigation();
		navigation.setLinks( new String[][] {
				{ "Home", "/" }
		} );
	
		websiteWidgetList.add( UserInfoFactory.newUserInfo() );
		websiteWidgetList.add( navigation );

		return websiteWidgetList;
	}

}
