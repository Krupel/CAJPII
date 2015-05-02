'use strict';

angular.module('girosApp')
    .factory('GirosFunc', function ($resource) {
        return $resource('api/girosFuncs/:id', {}, {
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
