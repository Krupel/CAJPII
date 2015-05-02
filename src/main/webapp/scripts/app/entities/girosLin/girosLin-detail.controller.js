'use strict';

angular.module('girosApp')
    .controller('GirosLinDetailController', function ($scope, $stateParams, GirosLin, GirosCab, Amigos) {
        $scope.girosLin = {};
        $scope.load = function (id) {
            GirosLin.get({id: id}, function(result) {
              $scope.girosLin = result;
            });
        };
        $scope.load($stateParams.id);
    });
