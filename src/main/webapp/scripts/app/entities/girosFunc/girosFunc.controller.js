'use strict';

angular.module('girosApp')
    .controller('GirosFuncController', function ($scope, GirosFunc, GirosCab, Funcionario, ParseLinks) {
        $scope.girosFuncs = [];
        $scope.giroscabs = GirosCab.query();
        $scope.funcionarios = Funcionario.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            GirosFunc.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.girosFuncs.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.girosFuncs = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            GirosFunc.update($scope.girosFunc,
                function () {
                    $scope.reset();
                    $('#saveGirosFuncModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GirosFunc.get({id: id}, function(result) {
                $scope.girosFunc = result;
                $('#saveGirosFuncModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GirosFunc.get({id: id}, function(result) {
                $scope.girosFunc = result;
                $('#deleteGirosFuncConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GirosFunc.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGirosFuncConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.girosFunc = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
