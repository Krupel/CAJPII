'use strict';

angular.module('girosApp')
    .factory('GiroLin', function ($resource) {
        return $resource('api/giroLins/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
