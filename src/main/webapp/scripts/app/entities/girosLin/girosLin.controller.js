'use strict';

angular.module('girosApp')
    .controller('GirosLinController', function ($scope, GirosLin, GirosCab, Amigos, ParseLinks) {
        $scope.girosLins = [];
        $scope.giroscabs = GirosCab.query();
        $scope.amigoss = Amigos.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            GirosLin.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.girosLins.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.girosLins = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            GirosLin.update($scope.girosLin,
                function () {
                    $scope.reset();
                    $('#saveGirosLinModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GirosLin.get({id: id}, function(result) {
                $scope.girosLin = result;
                $('#saveGirosLinModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GirosLin.get({id: id}, function(result) {
                $scope.girosLin = result;
                $('#deleteGirosLinConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GirosLin.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGirosLinConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.girosLin = {observacoes: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
