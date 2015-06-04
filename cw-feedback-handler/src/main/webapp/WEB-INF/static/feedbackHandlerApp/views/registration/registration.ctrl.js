'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */

angular.module('FeedbackApp')
  .controller('RegistrationCtrl', function($scope, $mdDialog, Monitoring) {

      $scope.accessToken = "Undefined";
  
	  $scope.register = function(applicationId, ev){
	  	Monitoring.register(applicationId)
	  		 
	        .then(function(data){
			    $mdDialog.show(
			      $mdDialog.alert()
			        .parent(angular.element(document.body))
			        .title('Registration successful')
			        .content('Please momorize your token: ' + data.accessToken)
			        .ok('Got it!')
			        .targetEvent(ev)
			    );
	        })
	  		
	  		.catch(function(error){
	  			$mdDialog.show(
			      $mdDialog.alert()
			        .parent(angular.element(document.body))
			        .title('Registration failed')
			        .content(error.message)
			        .ok('Got it!')
			        .targetEvent(ev)
			    );
	  		});
		};
  
	  $scope.alert = '';
  });
