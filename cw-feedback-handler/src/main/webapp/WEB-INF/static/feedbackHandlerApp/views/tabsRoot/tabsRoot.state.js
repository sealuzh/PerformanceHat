'use strict';

/**
 * Created by Emanuel Stoeckli on 06.06.15.
 */
angular.module('FeedbackApp')
    .config(function($stateProvider, $urlRouterProvider, VIEWS_DIR) {

        $urlRouterProvider.otherwise(function ($injector) {
            var $state = $injector.get('$state');
            $state.go('tabs.registration');
        });

        $stateProvider
            .state('tabs', {
                abstract: true,
                controller: 'RootCtrl',
                templateUrl: VIEWS_DIR + 'tabsRoot/tabs.html'
            });

    });
