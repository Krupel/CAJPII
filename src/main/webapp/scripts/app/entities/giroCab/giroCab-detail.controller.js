'use strict';

angular.module('girosApp')
    .controller('GiroCabDetailController', function ($scope, $stateParams, GiroCab) {
        $scope.giroCab = {};
        $scope.load = function (id) {
            GiroCab.get({id: id}, function(result) {
              $scope.giroCab = result;
            });
        };
        $scope.load($stateParams.id);
    });
