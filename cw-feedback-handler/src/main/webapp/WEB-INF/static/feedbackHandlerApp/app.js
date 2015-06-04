var app = angular.module('FeedbackApp', [
	'ui.router',
	'ngMaterial',
	'ngCookies',
	'ngResource'
])

.constant('APP_DIR', 'static/feedbackHandlerApp/')
.constant('VIEWS_DIR', 'static/feedbackHandlerApp/views/')

.controller('RootCtrl', ['$scope', '$location', function($scope, $location){

	$scope.selectedIndex = 0;

    $scope.$watch('selectedIndex', function(current, old) {
        switch (current) {
            case 0:
                $location.url("/registration");
                break;
            case 1:
                $location.url("/administration");
                break;
            case 2:
                $location.url("/environment");
                break;    
        }
    });
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