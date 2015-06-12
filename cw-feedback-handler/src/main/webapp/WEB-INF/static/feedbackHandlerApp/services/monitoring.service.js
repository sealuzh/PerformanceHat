'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15
 */

angular.module('FeedbackApp')
  .factory('Monitoring', function Monitoring($http, $q, $cookies, $state) {

    /**
     * Logged in application
     *
     * @type {{}} {'applicationId': id, 'accessToken': token }
     */
    var currentApp = {};

	/**
      * Login with existing application
      *
	  * @param  {String}   id     - application id
	  * @param  {String}   token     - token
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
	            return cb(data);
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
        * @param  {String}   id     - new and unique application id
        * @param  {Function} callback - optional
        * @return {Promise}
        */
        register: function (id, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            currentApp.applicationId = id;

            $http({
                url: 'monitoring/register',
                method: "GET",
                params: {applicationId: id}
            }).
              success(function (data) {
                $cookies.put('applicationId', id);
                $cookies.put('accessToken', data.accessToken);
                currentApp.accessToken = data.accessToken;
                deferred.resolve(data);
                return cb(data);
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
        * @param  {String}   applicationId     - application id
        * @param  {String}   token     - token
        * @param  {Function} callback - optional
        * @return {Promise}
        */
        login: doLogin,

        /**
         * Auto login with existing cookies
         *
         * @returns {Promise} promise with application id and token.
         */
        autologin: function(){
            if(currentApp.hasOwnProperty('applicationId') && currentApp.hasOwnProperty('accessToken')){
                var deferred = $q.defer();
                deferred.resolve(currentApp);
                return deferred.promise;
            }
            else {
                return doLogin($cookies.get('applicationId'), $cookies.get('accessToken'));
            }
        },

        /**
         * Returns true if application id and token are available
         *
         * @returns {boolean}
         */
        isLoggedIn: function(){
            return (currentApp.hasOwnProperty('applicationId') && currentApp.hasOwnProperty('accessToken'));
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
        },

        /**
         * Logout from current application
         */
        logout: function(){
            currentApp = {};
            $cookies.remove('accessToken');
            $cookies.remove('applicationId');
            $state.go('registration');
        }
    };
  });
