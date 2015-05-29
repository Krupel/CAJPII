'use strict';

angular.module('girosApp')
    .factory('Utente', function ($resource) {
        return $resource('api/utentes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if(data.datanascimento!=null) {
                        var datanascimentoFrom = data.datanascimento.split("-");
                        data.datanascimento = new Date(new Date(datanascimentoFrom[0], datanascimentoFrom[1] - 1, datanascimentoFrom[2]));
                    }
                    if(data.validadebi!=null) {
                        var validadebiFrom = data.validadebi.split("-");
                        data.validadebi = new Date(new Date(validadebiFrom[0], validadebiFrom[1] - 1, validadebiFrom[2]));
                    }
                    if(data.dataregisto!=null) {
                        var dataregistoFrom = data.dataregisto.split("-");
                        data.dataregisto = new Date(new Date(dataregistoFrom[0], dataregistoFrom[1] - 1, dataregistoFrom[2]));
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
