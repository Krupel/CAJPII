'use strict';

angular.module('girosApp')
    .factory('Amigos', function ($resource) {
        return $resource('api/amigoss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dataNascimento = new Date(data.dataNascimento);
                    var dataRegistoFrom = data.dataRegisto.split("-");
                    data.dataRegisto = new Date(new Date(dataRegistoFrom[0], dataRegistoFrom[1] - 1, dataRegistoFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
