'use strict';

angular.module('girosApp')
    .factory('GiroFunc', function ($resource) {
        return $resource('api/giroFuncs/:id', {}, {
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
