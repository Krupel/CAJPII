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

        $scope.colIDVisible = true;
        $scope.colNomeVisible = true;
        $scope.colDataNascimentoVisible = false;
        $scope.colBIVisible = false;
        $scope.colValidadeBIVisible = false;
        $scope.colSexoVisible = true;
        $scope.colNISSVisible = false;
        $scope.colDataRegistoVisible = false;
        $scope.colCaracteristicasVisible = false;
        $scope.colTipologiaVisible = true;

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

        $scope.searchTipologia = function (utentes) {

            if ($scope.pesqTipologia === undefined || $scope.pesqTipologia.length === 0) {
                return true;
            }

            var found = false;

            if (utentes.tipologiaAmigos.id === $scope.pesqTipologia) {
               found = true;
            }


            return found;
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
        };

        $http({method: 'GET', url: 'scripts/app/paises/paises.json'}).success(function(data)
        {
            $scope.paises = data; // response data
        });

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
            $scope.utente = {nome: null, datanascimento: new Date(), bi: null, validadebi: new Date(), sexo: null, niss: null, nacionalidade: null, dataregisto: new Date(), caracteristicas: null, activo: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
