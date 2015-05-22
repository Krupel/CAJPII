'use strict';

angular.module('girosApp')
    .controller('TipologiaController', function ($scope, Tipologia, ParseLinks) {
        $scope.tipologias = [];
        $scope.page = 1;
        $scope.colIDVisible = true;
        $scope.colDescricaoVisible = true;


        $scope.loadAll = function() {
            Tipologia.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tipologias.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.tipologias = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Tipologia.update($scope.tipologia,
                function () {
                    $scope.reset();
                    $('#saveTipologiaModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Tipologia.get({id: id}, function(result) {
                $scope.tipologia = result;
                $('#saveTipologiaModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Tipologia.get({id: id}, function(result) {
                $scope.tipologia = result;
                $('#deleteTipologiaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Tipologia.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteTipologiaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.tipologia = {descricao: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
