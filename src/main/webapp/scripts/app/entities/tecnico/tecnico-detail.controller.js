'use strict';

angular.module('girosApp')
    .controller('TecnicoDetailController', function ($scope, $stateParams, Tecnico) {
        $scope.tecnico = {};
        $scope.load = function (id) {
            Tecnico.get({id: id}, function(result) {
              $scope.tecnico = result;
            });
        };
        $scope.load($stateParams.id);
    });
