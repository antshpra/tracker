<!-- PageContent :: Transactions :: Start -->

<div id="PageContent-Transactions" class="container">
	<pagecontent-transactions
			apiUrl="/api/transaction"
			listApiUrl="/api/transaction/list"
			pageSize=10 ></pagecontent-transactions>
</div>


<script language="javascript">

	var pageContentTransactions = document.querySelector( 'pagecontent-transactions' );
	var pageContentTransactionsDiv = jQuery( '#PageContent-Transactions' );
	var pageContentTransactionsLoadErrorCount = 0;
	
	function pageContentTransactionsLoad() {
		if( pageContentTransactions.isFinished )
			return;
			
		var heightReq = jQuery( window ).scrollTop()
				- pageContentTransactionsDiv.position().top
				+ jQuery( window ).height()
				+ 3 * jQuery( window ).height();

		if( pageContentTransactionsDiv.outerHeight( true ) > heightReq )
			return;
			
		pageContentTransactions.loadTransactionList();
	}

	jQuery( '#Polymer' ).bind( 'template-bound', function( e ) {
			pageContentTransactionsLoad();
	});
		
	jQuery( '#Polymer-Window' ).bind( 'scroll', function( e ) {
			pageContentTransactionsLoad();
	});
	
	jQuery( pageContentTransactions ).bind( 'load-success', function( e ) {
			pageContentTransactionsLoad();
	});
	
	jQuery( pageContentTransactions ).bind( 'load-error', function( e ) {
			pageContentTransactionsLoadErrorCount++;
			if( pageContentTransactionsLoadErrorCount >= 10 )
				window.location.reload();
			else
				pageContentTransactionsLoad();
	});

</script>

<!-- PageContent :: Transactions :: End -->