'use strict';

angular.module('girosApp')
    .controller('TecnicoController', function ($scope, Tecnico, ParseLinks) {
        $scope.tecnicos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Tecnico.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tecnicos.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.tecnicos = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Tecnico.update($scope.tecnico,
                function () {
                    $scope.reset();
                    $('#saveTecnicoModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Tecnico.get({id: id}, function(result) {
                $scope.tecnico = result;
                $('#saveTecnicoModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Tecnico.get({id: id}, function(result) {
                $scope.tecnico = result;
                $('#deleteTecnicoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Tecnico.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteTecnicoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.tecnico = {nome: null, bi: null, telefone: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
