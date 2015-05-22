'use strict';

angular.module('girosApp')
    .controller('ResumoMensalController', function ($scope, Amigos,Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

    });
