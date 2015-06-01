'use strict';

angular.module('girosApp')
    .controller('GiroFuncController', function ($scope, GiroFunc, Tecnico, GiroCab, ParseLinks) {
        $scope.giroFuncs = [];
        $scope.tecnicos = [];
        $scope.girocabs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            GiroFunc.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.giroFuncs.push(result[i]);
                }
            });
        };

        $scope.loadAllGirosCabs = function() {
            GiroCab.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.girocabs.push(result[i]);
                }
            });
        };

        $scope.loadAllTecnicos = function() {
            Tecnico.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tecnicos.push(result[i]);
                }
            });
        };

        $scope.reset = function() {
            $scope.page = 1;
            $scope.giroFuncs = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        $scope.loadAllGirosCabs();
        $scope.loadAllTecnicos();

        $scope.create = function () {
            GiroFunc.update($scope.giroFunc,
                function () {
                    $scope.reset();
                    $('#saveGiroFuncModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GiroFunc.get({id: id}, function(result) {
                $scope.giroFunc = result;
                $('#saveGiroFuncModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GiroFunc.get({id: id}, function(result) {
                $scope.giroFunc = result;
                $('#deleteGiroFuncConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GiroFunc.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGiroFuncConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.giroFunc = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
