'use strict';

angular.module('girosApp')
    .controller('GiroLinDetailController', function ($scope, $stateParams, GiroLin, Utente, GiroCab) {
        $scope.giroLin = {};
        $scope.load = function (id) {
            GiroLin.get({id: id}, function(result) {
              $scope.giroLin = result;
            });
        };
        $scope.load($stateParams.id);
    });
