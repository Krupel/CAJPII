'use strict';

angular.module('girosApp')
    .controller('HistoricoUtenteDetailController', function ($scope, $stateParams, HistoricoUtente, Utente, Tipologia) {
        $scope.historicoUtente = {};
        $scope.load = function (id) {
            HistoricoUtente.get({id: id}, function(result) {
              $scope.historicoUtente = result;
            });
        };
        $scope.load($stateParams.id);
    });
