'use strict';

angular.module('girosApp')
    .controller('TipologiaDetailController', function ($scope, $stateParams, Tipologia) {
        $scope.tipologia = {};
        $scope.load = function (id) {
            Tipologia.get({id: id}, function(result) {
              $scope.tipologia = result;
            });
        };
        $scope.load($stateParams.id);
    });
