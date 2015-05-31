'use strict';

angular.module('girosApp')
    .controller('UtenteController', function ($scope, Utente, Tipologia, ParseLinks,$http) {
        $scope.utentes = [];
        $scope.tipologias = [];
        $scope.page = 1;
        $scope.pesqName = "";
        $scope.pesqNacio = "";
        $scope.pesqCaract = "";
        $scope.pesqTipologia = "";

        $scope.loadAllTipologias = function() {
            Tipologia.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tipologias.push(result[i]);
                }
            });
        };

        $scope.loadAll = function() {
            Utente.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.utentes.push(result[i]);

                }
            });

        };

        $scope.pesquisar = function(){
            $http({
                method:'GET',
                url:'api/utentesfilternamenacio',
                params: {name:$scope.pesqName,nacio:$scope.pesqNacio}
            }).
                success(function(data){
                    $scope.utentes =[];
                    for (var i = 0; i < data.length; i++) {
                        var myvalue =angular.fromJson(data[i])
                        $scope.utentes.push(angular.fromJson(data[i]));

                    }
                });
        };

        $scope.limpar = function(){
            $scope.pesqName = ""
            $scope.pesqNacio = ""
            $scope.pesqTipologia = ""
            $scope.pesqCaract = ""
            //$scope.pesquisar()
        };

        $http({method: 'GET', url: 'scripts/app/paises/paises.json'}).success(function(data)
        {
            $scope.paises = data; // response data
        });

        $scope.reset = function() {
            $scope.page = 1;
            $scope.utentes = [];
            $scope.loadAll();
            $scope.loadAllTipologias();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
            $scope.loadAllTipologias();
        };

        $scope.loadAll();
        $scope.loadAllTipologias();

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
