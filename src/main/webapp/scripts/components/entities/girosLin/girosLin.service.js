'use strict';

angular.module('girosApp')
    .factory('GirosLin', function ($resource) {
        return $resource('api/girosLins/:id', {}, {
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
