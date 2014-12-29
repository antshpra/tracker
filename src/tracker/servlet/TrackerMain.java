package tracker.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import tracker.pagecontent.transactionlist.TransactionListContentHelper;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.servlet.ClaymusMain;
import com.claymus.websitewidget.header.HeaderWidget;
import com.claymus.websitewidget.header.HeaderWidgetHelper;

@SuppressWarnings("serial")
public class TrackerMain extends ClaymusMain {
	
	static {
		PageContentRegistry.register( TransactionListContentHelper.class );
	}
	
	@Override
	protected String getTemplateName( HttpServletRequest reqest ) {
		return "tracker/servlet/TrackerTemplate.ftl";
	}

	@Override
	protected List<PageContent> getPageContentList( HttpServletRequest request ) {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/" ) )
			pageContentList.add(
					TransactionListContentHelper.newTransactionListContent() );

		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList( HttpServletRequest request ) {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		
		HeaderWidget headerWidget = HeaderWidgetHelper.newHeaderWidget();
		headerWidget.setBrand( "Track It Up !" );
		headerWidget.setRightNavItems( new String[][] {
				{ "Log Out", claymusHelper.createLogoutURL() }
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
		HtmlContent htmlContent = HtmlContentHelper.newHtmlContent();
		htmlContent.setContent( html );
		return htmlContent;
	}

}
