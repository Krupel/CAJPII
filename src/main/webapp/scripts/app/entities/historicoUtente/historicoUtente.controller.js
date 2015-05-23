'use strict';

angular.module('girosApp')
    .controller('HistoricoUtenteController', function ($scope, HistoricoUtente, Utente, Tipologia, ParseLinks) {
        $scope.historicoUtentes = [];
        $scope.utentes = Utente.query();
        $scope.tipologias = Tipologia.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            HistoricoUtente.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.historicoUtentes.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.historicoUtentes = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            HistoricoUtente.update($scope.historicoUtente,
                function () {
                    $scope.reset();
                    $('#saveHistoricoUtenteModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            HistoricoUtente.get({id: id}, function(result) {
                $scope.historicoUtente = result;
                $('#saveHistoricoUtenteModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            HistoricoUtente.get({id: id}, function(result) {
                $scope.historicoUtente = result;
                $('#deleteHistoricoUtenteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HistoricoUtente.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteHistoricoUtenteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.historicoUtente = {data: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
