'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */

angular.module('FeedbackApp')
    .controller('AdministrationCtrl', function($scope, $mdDialog, $timeout, $cookies, Monitoring, Application) {
  
        $scope.isLoggedIn = false;

        var loginSuccess = function(id, token){
            console.log('Success');
            $scope.isLoggedIn = true;

            $scope.applications = Application({
                'Application-ID': id,
                'Access-Token': token
            }).getAll();
        };

  	  var loginError = function(error){
	  	  console.log('error');
    		$scope.isLoggedIn = false;
  	  };
  	  
  	  Monitoring.autologin()
  	  	.then(loginSuccess($cookies.get('applicationId'), $cookies.get('accessToken')))
	    .catch(loginError);

      $scope.login = function(applicationId, accessToken, ev){
	      Monitoring.login(applicationId, accessToken)
	  	  	.then(loginSuccess(applicationId, accessToken))
	  		.catch(function(error){
	  			$mdDialog.show(
			      $mdDialog.alert()
			        .parent(angular.element(document.body))
			        .title('Login failed')
			        .content(error.message)
			        .ok('Got it!')
			        .targetEvent(ev)
			    );
         		loginError();
	  		});
      };

    $scope.error = '';
    $scope.success = '';

    $scope.update = function(app){
        Application({}).update(app).$promise.then(function(){
            console.log("Update successful.");
            $scope.success = 'Update successful.';
            $scope.error = '';

            $timeout(function(){
                $scope.success = '';
            }, 5000);

        }, function(error){
            console.log("Update error");
            $scope.error = error;
            $scope.success = '';

            $timeout(function(){
                $scope.error = '';
            }, 5000);
        });
    };
});
