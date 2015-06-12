<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:AngularSkeleton title="${content}" app="FeedbackApp">

	<jsp:attribute name="head">
		<link rel="stylesheet" href="/static/feedbackHandlerApp/app.css">
	</jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="/static/bower/zeroclipboard/dist/ZeroClipboard.min.js"></script>
        <script src="/static/bower/ng-clip/dest/ng-clip.min.js"></script>

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
        <script src="/static/feedbackHandlerApp/views/tabsRoot/tabsRoot.ctrl.js"></script>
        <script src="/static/feedbackHandlerApp/views/tabsRoot/tabsRoot.state.js"></script>
    </jsp:attribute>

	<jsp:body>
	    <md-toolbar layout="row">
            <div class="md-toolbar-tools">
                <i class="bar graph icon"></i>
	            <h1>Feedback Handler</h1>
                <span flex></span>
            </div>
	    </md-toolbar>

        <!-- tabsRoot/tabsRoot.ctrl.js RootCtrl -->
		<ui-view></ui-view>
    </jsp:body>

</t:AngularSkeleton>
