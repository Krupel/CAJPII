'use strict';

angular.module('girosApp')
    .controller('GirosCabDetailController', function ($scope, $stateParams, GirosCab) {
        $scope.girosCab = {};
        $scope.load = function (id) {
            GirosCab.get({id: id}, function(result) {
              $scope.girosCab = result;
            });
        };
        $scope.load($stateParams.id);
    });
