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
import com.claymus.module.websitewidget.header.HeaderWidget;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.servlet.ClaymusMain;

@SuppressWarnings("serial")
public class TrackerMain extends ClaymusMain {
	
	static {
		PAGE_CONTENT_REGISTRY.register( TransactionListContentHelper.class );
	}
	
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
		else if( requestUri.equals( "/transactions" ) )
			pageContentList.add(
					TransactionListContentHelper.newTransactionListContent() );

		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList( HttpServletRequest request ) throws IOException {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		
		HeaderWidget headerWidget = HeaderWidgetFactory.newHeaderWidget();
		headerWidget.setBrand( "Track It Up !" );
		headerWidget.setRightNavItems( new String[][] {
				{ "Transactions", "/transactions" },
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
