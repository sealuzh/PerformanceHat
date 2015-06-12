var app = angular.module('FeedbackApp', [
	'ui.router',
	'ngMaterial',
	'ngCookies',
	'ngResource',
    'ngClipboard'
])

.constant('APP_DIR', 'static/feedbackHandlerApp/')
.constant('VIEWS_DIR', 'static/feedbackHandlerApp/views/')

.config(['ngClipProvider', function(ngClipProvider) {
    ngClipProvider.setPath("static/bower/zeroclipboard/dist/ZeroClipboard.swf");
}])

.config(function ($mdThemingProvider) {
$mdThemingProvider
    .theme('default')
    .primaryPalette('indigo')
    .accentPalette('lime')
    .warnPalette('red')
    .backgroundPalette('brown')
})

;
