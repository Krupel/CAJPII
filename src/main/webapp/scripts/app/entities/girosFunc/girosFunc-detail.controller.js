'use strict';

angular.module('girosApp')
    .controller('GirosFuncDetailController', function ($scope, $stateParams, GirosFunc, GirosCab, Funcionario) {
        $scope.girosFunc = {};
        $scope.load = function (id) {
            GirosFunc.get({id: id}, function(result) {
              $scope.girosFunc = result;
            });
        };
        $scope.load($stateParams.id);
    });
