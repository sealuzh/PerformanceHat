<%-- Skeleton for all angular pages. --%>
<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@attribute name="head" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@attribute name="app" required="true" %>
<%@attribute name="rootController" required="true" %>
<%@attribute name="scripts" fragment="true"%>

<html lang="en" ng-app="${app}">
  
  <head>
  	<title>${title}</title>
  
  	<meta name="viewport" content="initial-scale=1" />
  
    <link rel="stylesheet" href="/static/bower/angular-material/angular-material.min.css">
    <link rel="stylesheet" href="/static/bower/semantic-ui/dist/semantic.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=RobotoDraft:300,400,500,700,400italic">
    
    <jsp:invoke fragment="head" />
  </head>
  
  <body layout="column" ng-controller="${rootController}">
  
  	<jsp:doBody/>
    
    <!-- jQuery and AngularJS -->
	<script src="/static/bower/jquery/dist/jquery.js"></script>
	<script src="/static/bower/angular/angular.js"></script>
	<script src="/static/bower/angular-ui-router/release/angular-ui-router.js"></script>
	<script src="/static/bower/angular-cookies/angular-cookies.min.js"></script>
	<script src="/static/bower/angular-resource/angular-resource.min.js"></script>

	<!-- Angular Material Dependencies -->
    <script src="/static/bower/angular-animate/angular-animate.min.js"></script>
    <script src="/static/bower/angular-aria/angular-aria.min.js"></script>    
	<script src="/static/bower/angular-material/angular-material.js"></script>

	<!-- Semantic UI -->
	<script src="/static/bower/semantic-ui/dist/semantic.min.js"></script>	
	
	<jsp:invoke fragment="scripts" />
  </body>

</html>