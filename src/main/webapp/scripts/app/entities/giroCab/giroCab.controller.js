'use strict';

angular.module('girosApp')
    .controller('GiroCabController', function ($scope, GiroCab, ParseLinks) {
        $scope.giroCabs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            GiroCab.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.giroCabs.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.giroCabs = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            GiroCab.update($scope.giroCab,
                function () {
                    $scope.reset();
                    $('#saveGiroCabModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GiroCab.get({id: id}, function(result) {
                $scope.giroCab = result;
                $('#saveGiroCabModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GiroCab.get({id: id}, function(result) {
                $scope.giroCab = result;
                $('#deleteGiroCabConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GiroCab.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGiroCabConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.giroCab = {data: null, observacoes: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
