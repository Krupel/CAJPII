'use strict';

angular.module('girosApp')
    .controller('GiroLinController', function ($scope, GiroLin, Utente, GiroCab, ParseLinks) {
        $scope.giroLins = [];
        $scope.utentes = Utente.query();
        $scope.girocabs = GiroCab.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            GiroLin.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.giroLins.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.giroLins = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            GiroLin.update($scope.giroLin,
                function () {
                    $scope.reset();
                    $('#saveGiroLinModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GiroLin.get({id: id}, function(result) {
                $scope.giroLin = result;
                $('#saveGiroLinModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GiroLin.get({id: id}, function(result) {
                $scope.giroLin = result;
                $('#deleteGiroLinConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GiroLin.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGiroLinConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.giroLin = {observacoes: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
