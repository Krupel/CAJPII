'use strict';

angular.module('girosApp')
    .controller('AmigosController', function ($scope, Amigos, Tipologia, ParseLinks) {
        $scope.amigoss = [];
        $scope.tipologias = Tipologia.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Amigos.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.amigoss.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.amigoss = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Amigos.update($scope.amigos,
                function () {
                    $scope.reset();
                    $('#saveAmigosModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Amigos.get({id: id}, function(result) {
                $scope.amigos = result;
                $('#saveAmigosModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Amigos.get({id: id}, function(result) {
                $scope.amigos = result;
                $('#deleteAmigosConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Amigos.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteAmigosConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.amigos = {nome: null, dataNascimento: null, bi: null, niss: null, nacionalidade: null, autorizacaoResidencia: null, dataRegisto: null, caracteristicas: null, observacoes: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
