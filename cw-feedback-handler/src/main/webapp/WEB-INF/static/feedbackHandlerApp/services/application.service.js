'use strict';

angular.module('FeedbackApp')
  .factory('Application', function ($resource) {
    return function(customHeaders) {

        if(!customHeaders){
            customHeaders = {};
        }

        return $resource('monitoring/:id', {
                id: '@id'
            },
            {
                getAll: {
                    url: 'monitoring/applications',
                    method: 'GET',
                    headers: customHeaders,
                    isArray: true
                },

                update: {
                    url: 'monitoring/update',
                    method: 'POST'
                }
            });
    };
  });
