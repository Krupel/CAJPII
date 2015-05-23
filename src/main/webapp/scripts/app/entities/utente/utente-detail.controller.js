'use strict';

angular.module('girosApp')
    .controller('UtenteDetailController', function ($scope, $stateParams, Utente, Tipologia) {
        $scope.utente = {};
        $scope.load = function (id) {
            Utente.get({id: id}, function(result) {
              $scope.utente = result;
            });
        };
        $scope.load($stateParams.id);
    });
