<!-- PageContent :: Tracker :: Start -->

<template is="auto-binding" id="PageContent-Tracker">

	<div id="PageContent-TransactionList" class="container"></div>
	<script type="text/javascript" language="javascript" src="/pagecontent.transactionlist/pagecontent.transactionlist.nocache.js" async></script>


	<paper-fab icon="add" title="Add Transaction" class="bg-green" style="position:fixed; bottom:10px; right:10px;" on-tap="{{ displayAddTrDialog }}"></paper-fab>

	<paper-dialog backdrop autoCloseDisabled id="PageContent-Tracker-AddDialog" heading="Add New Transaction">
		<p>Not yet implemented !</p>
	</paper-dialog>

</template>


<script>

	var scope = document.querySelector( '#PageContent-Tracker' );
	var addDialog; // Initialized in initTracker()
	
	scope.displayAddTrDialog = function( e ) {
		addDialog.open();
	};

	function initTracker() {
		addDialog = document.querySelector( '#PageContent-Tracker-AddDialog' );
		if( addDialog == null ) {
			console.log( 'Tracker initialization failed. Retrying in 100ms ...' );
			window.setTimeout( initTracker, 100 );
		}
	}
	initTracker();
	
</script>

<!-- PageContent :: Tracker :: Start -->
