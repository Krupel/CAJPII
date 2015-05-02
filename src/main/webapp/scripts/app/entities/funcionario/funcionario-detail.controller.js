'use strict';

angular.module('girosApp')
    .controller('FuncionarioDetailController', function ($scope, $stateParams, Funcionario) {
        $scope.funcionario = {};
        $scope.load = function (id) {
            Funcionario.get({id: id}, function(result) {
              $scope.funcionario = result;
            });
        };
        $scope.load($stateParams.id);
    });
