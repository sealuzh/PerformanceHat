'use strict';

/**
 * Created by Emanuel Stoeckli on 06.06.15.
 */
angular.module('FeedbackApp')
    .controller('RootCtrl', ['$scope', '$location', 'Monitoring', function($scope, $location, Monitoring){

        $scope.selectedIndex = 0;

        $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
            $scope.selectedIndex = toState.data.selectedTab;
        });

        $scope.logout = function(){
            Monitoring.logout();
        };
    }])
;
