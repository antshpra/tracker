<!DOCTYPE html>
<html lang="en">
	<head>

		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	
		<!-- jQuery 2.1.1 -->
		<script src="//cdn-asia.pratilipi.com/third-party/jquery-2.1.1/jquery-2.1.1.min.js"></script>

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
