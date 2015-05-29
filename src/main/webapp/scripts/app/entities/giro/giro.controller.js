'use strict';

angular.module('girosApp')
    .controller('GiroController', function ($scope, Amigos,Principal,$http,GiroCab,GiroLin,Tecnico,Utente,ParseLinks) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.tecnicos = [];
        $scope.utentes =[];
        $scope.page = 1;
        $scope.loadAllTecnico = function() {
            Tecnico.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tecnicos.push(result[i]);
                }
            });
        };

        $scope.loadAllUtentes = function() {
            Utente.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.utentes.push(result[i]);

                }
            });

        };

        $scope.saveGiro = function(){
            GiroCab.update($scope.giroCab,
                function (data) {
                  alert(angular.toJson(data));
                });
        }

        $scope.loadAllTecnico();
        $scope.loadAllUtentes();
    });
