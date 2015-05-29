'use strict';

angular.module('girosApp')
    .factory('GirosCab', function ($resource) {
        return $resource('api/girosCabs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if(data.data!=null) {
                        var dataFrom = data.data.split("-");
                        data.data = new Date(new Date(dataFrom[0], dataFrom[1] - 1, dataFrom[2]));
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
