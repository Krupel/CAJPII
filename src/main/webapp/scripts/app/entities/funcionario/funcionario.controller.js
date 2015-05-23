'use strict';

angular.module('girosApp')
    .controller('FuncionarioController', function ($scope, Funcionario, ParseLinks) {
        $scope.funcionarios = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Funcionario.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.funcionarios.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.funcionarios = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Funcionario.update($scope.funcionario,
                function () {
                    $scope.reset();
                    $('#saveFuncionarioModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Funcionario.get({id: id}, function(result) {
                $scope.funcionario = result;
                $('#saveFuncionarioModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Funcionario.get({id: id}, function(result) {
                $scope.funcionario = result;
                $('#deleteFuncionarioConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Funcionario.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteFuncionarioConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.funcionario = {bi: null, nome: null, telefone: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
