'use strict';

angular.module('girosApp')
    .controller('UtenteController', function ($scope, Utente, Tipologia, ParseLinks) {
        $scope.utentes = [];
        $scope.tipologias = Tipologia.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Utente.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.utentes.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.utentes = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Utente.update($scope.utente,
                function () {
                    $scope.reset();
                    $('#saveUtenteModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Utente.get({id: id}, function(result) {
                $scope.utente = result;
                $('#saveUtenteModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Utente.get({id: id}, function(result) {
                $scope.utente = result;
                $('#deleteUtenteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Utente.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteUtenteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.utente = {nome: null, datanascimento: null, bi: null, validadebi: null, sexo: null, niss: null, nacionalidade: null, dataregisto: null, caracteristicas: null, activo: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
