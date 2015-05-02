'use strict';

angular.module('girosApp')
    .controller('GirosCabController', function ($scope, GirosCab, ParseLinks) {
        $scope.girosCabs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            GirosCab.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.girosCabs.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.girosCabs = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            GirosCab.update($scope.girosCab,
                function () {
                    $scope.reset();
                    $('#saveGirosCabModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GirosCab.get({id: id}, function(result) {
                $scope.girosCab = result;
                $('#saveGirosCabModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GirosCab.get({id: id}, function(result) {
                $scope.girosCab = result;
                $('#deleteGirosCabConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GirosCab.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGirosCabConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.girosCab = {data: null, observacoes: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
