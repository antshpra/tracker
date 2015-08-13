<!DOCTYPE html>
<html lang="en">
	<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
		<!-- jQuery 2.1.1 -->
		<script src="//jquery.lib.cdssolutions.in/jquery-2.1.1/jquery-2.1.1.min.js"></script>
		
		<#if request.getRequestURI() != "/">
			<!-- BootStrap 3.2.0 -->
			<script src="//bootstrap.lib.cdssolutions.in/bootstrap-3.2.0/js/bootstrap.min.js"></script>
			<link rel="stylesheet" href="//bootstrap.lib.cdssolutions.in/bootstrap-3.2.0/css/bootstrap.min.css">
		</#if>
		
		<!-- Claymus Resources -->
		<link type="text/css" rel="stylesheet" href="/theme.default/style.css">
		<link type="text/css" rel="stylesheet" href="/theme.default/style.polymer.css">
		<script type="text/javascript" language="javascript" src="/theme.default/script.js" defer></script>

		<!-- Tracker Resources -->
		<link rel="stylesheet" href="/theme.tracker/style.css">

		<!-- Modules' Dependencies -->
		<#list resourceTagList as resourceTag>
			${ resourceTag }
		</#list>


		<title>Track It Up !</title>

	</head>
	<body fullbleed layout vertical>
		<template unresolved is="auto-binding" id="Polymer">
			<core-scroll-header-panel flex id="Polymer-Window">

				<core-toolbar>
					<div flex>
						Track it up !
					</div>
				</core-toolbar>
		
				<#list pageContentHtmlList as pageContentHtml>
					${ pageContentHtml }
				</#list>
				
				<#if websiteWidgetHtmlListMap["FOOTER"] ??>
					<#list websiteWidgetHtmlListMap["FOOTER"] as websiteWidgetHtml>
						${ websiteWidgetHtml }
					</#list>
				</#if>

			</core-scroll-header-panel>
		</template>
	</body>
</html>
