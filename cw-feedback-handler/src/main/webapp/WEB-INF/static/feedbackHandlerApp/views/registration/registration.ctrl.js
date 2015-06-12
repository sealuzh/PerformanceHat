'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */
angular.module('FeedbackApp')
	.controller('RegistrationCtrl', function($scope, $state, $mdDialog, VIEWS_DIR, Monitoring) {

        $scope.applicationId = '';
		$scope.accessToken = 'Undefined';

        $scope.register = function(applicationId, ev){

    	  	Monitoring.register(applicationId)
	        .then(function(data){
                $mdDialog.show({
                    controller: function DialogController($scope, $mdDialog) {
                        $scope.title = 'Registration successful';

                        $scope.getToken = function(){
                            return data.accessToken;
                        };

                        $scope.cancel = function() {
                            $mdDialog.cancel();
                        };

                        $scope.goTo = function(state) {
                            $mdDialog.hide(state);
                        };
                    },
                    templateUrl:  VIEWS_DIR + 'registration/dialog.html',
                    targetEvent: ev
                })
                .then(function(state) {
                    $state.go(state);
                }, function() {
                    // cancel
                    $scope.applicationId = '';
                });
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
