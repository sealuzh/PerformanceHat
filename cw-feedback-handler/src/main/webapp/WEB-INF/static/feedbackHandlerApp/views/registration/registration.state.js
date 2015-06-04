'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */

angular.module('FeedbackApp')
  .config(function($stateProvider, $urlRouterProvider, VIEWS_DIR) {

    $urlRouterProvider.otherwise(function ($injector) {
      var $state = $injector.get('$state');
      $state.go('registration');
    });

    $stateProvider
      .state('registration', {
        url: '/registration',
        controller: 'RegistrationCtrl',
        templateUrl: VIEWS_DIR + 'registration/template.html'
      });

  });
