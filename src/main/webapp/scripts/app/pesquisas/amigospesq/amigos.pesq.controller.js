'use strict';

angular.module('girosApp')
    .controller('AmigosPesqController', function ($scope, Amigos,Principal, $http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $http({method: 'GET', url: 'scripts/app/paises/paises.json'}).success(function(data)
        {
            $scope.paises = data; // response data
        });

        $scope.loadAll = function() {
            Amigos.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.amigoss.push(result[i]);
                }
            });
        };
    });
