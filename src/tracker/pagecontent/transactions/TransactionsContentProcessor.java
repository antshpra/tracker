package tracker.pagecontent.transactions;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.pagecontent.PageContentProcessor;

public class TransactionsContentProcessor extends PageContentProcessor<TransactionsContent> {
	
	@Override
	public Resource[] getDependencies( TransactionsContent transactionsContent, HttpServletRequest request ) {

		return new Resource[] {
				ClaymusResource.JQUERY_2,
				ClaymusResource.POLYMER,
				ClaymusResource.POLYMER_CORE_AJAX,
				ClaymusResource.POLYMER_PAPER_INPUT,
				ClaymusResource.POLYMER_PAPER_SPINNER,
				new Resource() {

					@Override
					public String getTag() {
						return "<link rel='import' href='/polymer/pagecontent-transactions.html'>";
					}

				},
		};

	}
	
}
