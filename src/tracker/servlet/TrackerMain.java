package tracker.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.ClaymusHelper;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.header.HeaderWidget;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.servlet.ClaymusMain;

@SuppressWarnings("serial")
public class TrackerMain extends ClaymusMain {
	
	@Override
	protected String getTemplateName() {
		return "tracker/servlet/TrackerTemplate.ftl";
	}

	@Override
	protected List<PageContent> getPageContentList( HttpServletRequest request )
			throws IOException {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/tracker/page/home/HomePage.ftl" ) );

		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList( HttpServletRequest request ) {
		
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
	
	private HtmlContent generateHtmlContentFromFile( String fileName )
			throws IOException {
		
		File file = new File( fileName );
		List<String> lines = FileUtils.readLines( file, "UTF-8" );
		String html = "";
		for( String line : lines )
			html = html + line;
		HtmlContent htmlContent = HtmlContentFactory.newHtmlContent();
		htmlContent.setHtml( html );
		return htmlContent;
	}

}
