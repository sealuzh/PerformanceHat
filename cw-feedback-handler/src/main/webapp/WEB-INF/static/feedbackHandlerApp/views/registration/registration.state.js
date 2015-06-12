'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */
angular.module('FeedbackApp')
    .config(function($stateProvider, VIEWS_DIR) {

        $stateProvider
            .state('tabs.registration', {
                url: '/registration',
                controller: 'RegistrationCtrl',
                templateUrl: VIEWS_DIR + 'registration/template.html',
                data: {
                    'selectedTab': 0
                }
            });
  });
