'use strict';

angular.module('girosApp')
    .controller('AmigosDetailController', function ($scope, $stateParams, Amigos, Tipologia) {
        $scope.amigos = {};
        $scope.load = function (id) {
            Amigos.get({id: id}, function(result) {
              $scope.amigos = result;
            });
        };
        $scope.load($stateParams.id);
    });
