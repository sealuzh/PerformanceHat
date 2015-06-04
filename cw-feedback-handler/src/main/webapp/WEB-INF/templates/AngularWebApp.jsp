<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:AngularSkeleton title="${content}" app="FeedbackApp" rootController="RootCtrl">

	<jsp:attribute name="head">
		<link rel="stylesheet" href="/static/feedbackHandlerApp/app.css">
	</jsp:attribute>

	<jsp:attribute name="scripts">
  		<script src="/static/bower/arbor/lib/arbor.js"></script>
  		<script src="/static/bower/arbor/lib/arbor-tween.js"></script>
  		<script src="/static/bower/arbor/demos/_/graphics.js"></script>
  		<script src="/static/bower/arbor/demos/halfviz/src/renderer.js"></script>
	
		<script src="/static/feedbackHandlerApp/app.js"></script>
		
		<script src="/static/feedbackHandlerApp/services/monitoring.service.js"></script>
		<script src="/static/feedbackHandlerApp/services/application.service.js"></script>
		
		<script src="/static/feedbackHandlerApp/views/registration/registration.ctrl.js"></script>
		<script src="/static/feedbackHandlerApp/views/registration/registration.state.js"></script>
		<script src="/static/feedbackHandlerApp/views/administration/administration.ctrl.js"></script>
		<script src="/static/feedbackHandlerApp/views/administration/administration.state.js"></script>
		<script src="/static/feedbackHandlerApp/views/environment/environment.ctrl.js"></script>
		<script src="/static/feedbackHandlerApp/views/environment/environment.state.js"></script>
	</jsp:attribute>

	<jsp:body>
	    
	    <md-toolbar layout="row">
	      <div class="md-toolbar-tools">
	        <md-button ng-click="toggleSidenav('left')" hide-gt-sm class="md-icon-button">
	          <md-icon aria-label="Menu" md-svg-icon="https://s3-us-west-2.amazonaws.com/s.cdpn.io/68133/menu.svg"></md-icon>
	        </md-button>
	        <h1>Feedback Handler</h1>
	      </div>
	    </md-toolbar>

		<div id="full-page">
		    <md-tabs md-selected="selectedIndex" md-stretch-tabs md-border-bottom>
			      <md-tab label="Registration"></md-tab>	      
	      	      <md-tab label="Administration"></md-tab>
	      	      <md-tab label="Microservice Environment"></md-tab>
	    	</md-tabs>
	    
	    	<div id="tab-container" ui-view></div>
	    </div>
	    
	    <div ng-if="alert">
		    <br/>
		    <b layout="row" layout-align="center center" class="md-padding">
		      {{alert}}
		    </b>
	    </div>
	</jsp:body>

</t:AngularSkeleton>