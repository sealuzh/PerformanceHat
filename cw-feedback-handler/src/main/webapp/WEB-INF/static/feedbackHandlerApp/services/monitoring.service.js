'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15
 */

angular.module('FeedbackApp')
  .factory('Monitoring', function Monitoring($http, $q, $cookies) {

    var currentApp = {};

	/**
      * Login with existing application
      *
	  * @param  {Object}   applicationId     - application id
	  * @param  {Object}   token     - token
	  * @param  {Function} callback - optional
	  * @return {Promise}
	  */
	var doLogin = function(id, token, callback){
		var cb = callback || angular.noop;
        var deferred = $q.defer();
	
		if(id && token && id !== "" && token !== ''){
	        $http({
	          	url: 'monitoring/login', 
			    method: "GET",
				headers: {
				   'Access-Token': token,
				   'Application-ID': id
				}
	        }).
	          success(function (data) {
	          	$cookies.put('applicationId', data.applicationId);
	            $cookies.put('accessToken', data.accessToken);
	            currentApp = data;
	            deferred.resolve(data);
	            return cb();
	          }).
	          error(function (err) {
	          	currentApp = {};
	          	$cookies.remove('accessToken');
	          	$cookies.remove('applicationId');
	            deferred.reject(err);
	            return cb(err);
	          }.bind(this));
        }
        else {
        	deferred.reject('Invalid input');
		}
		
        return deferred.promise;
    };

    return {

      /**
       * Register new feedback application
       *
       * @param  {Object}   applicationId     - new and unique application id
       * @param  {Function} callback - optional
       * @return {Promise}
       */
      register: function (id, callback) {
        var cb = callback || angular.noop;
        var deferred = $q.defer();
        
        currentApp.id = id;

        $http({
          	url: 'monitoring/register', 
		    method: "GET",
		    params: {applicationId: id}
        }).
          success(function (data) {
            $cookies.put('accessToken', data.accessToken);
            currentApp.accessToken = data.accessToken;
            deferred.resolve(data);
            return cb();
          }).
          error(function (err) {
          	currentApp = {};
            deferred.reject(err);
            return cb(err);
          }.bind(this));

        return deferred.promise;
      },
      
      /**
       * Login with existing application
       *
       * @param  {Object}   applicationId     - application id
       * @param  {Object}   token     - token
       * @param  {Function} callback - optional
       * @return {Promise}
       */
      login: doLogin,
      
      /**
       * Try auto login with existing cookies
       */
      autologin: function(){
      	return doLogin($cookies.get('applicationId'), $cookies.get('accessToken'));
      },

      /**
       * Returns current app with id and accessToken
       *
       * @return {Object} currentApp
       */
      getCurrentApp: function () {
        return currentApp;
      },
      
      /**
       * Get access token
       */
      getToken: function () {
        return $cookies.get('accessToken');
      }
    };
  });