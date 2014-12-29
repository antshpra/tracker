<!-- PageContent :: Tracker :: Start -->

<template is="auto-binding" id="PageContent-Tracker">

	<div id="PageContent-TransactionList" class="container"></div>
	
	<paper-fab icon="add" title="Add Transaction" class="bg-green" style="position:absolute; bottom:10px; right:10px;" on-tap="{{ displayAddTrDialog }}"></paper-fab>
	
	<script type="text/javascript" language="javascript" src="/pagecontent.transactionlist/pagecontent.transactionlist.nocache.js" async></script>

</template>


<script>

	var scope = document.querySelector( '#PageContent-Tracker' );

	scope.displayAddTrDialog = function( e ) {
		alert( 'Not yet implemented !' ); // TODO
	};

</script>

<!-- PageContent :: Tracker :: Start -->
