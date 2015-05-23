'use strict';

angular.module('girosApp')
    .controller('GiroFuncDetailController', function ($scope, $stateParams, GiroFunc, Tecnico, GiroCab) {
        $scope.giroFunc = {};
        $scope.load = function (id) {
            GiroFunc.get({id: id}, function(result) {
              $scope.giroFunc = result;
            });
        };
        $scope.load($stateParams.id);
    });
