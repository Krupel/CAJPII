'use strict';

angular.module('girosApp')
    .controller('GiroController', function ($scope, Amigos,Principal,$http,GiroCab,GiroLin,Tecnico,Utente,ParseLinks,GiroFunc) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.giroCab = {data: new Date(), observacoes: ""};

        $scope.tecnicos = [];
        $scope.utentes =[];
        $scope.page = 1;
        $scope.loadAllTecnico = function() {
            var tecnico = {id:0,nome:"",bi:"",telefone:"",email:"",checked:false};
            Tecnico.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    tecnico = {id:0,nome:"",bi:"",telefone:"",email:"",checked:false};
                    tecnico.id = result[i].id;
                    tecnico.nome = result[i].nome;
                    tecnico.bi = result[i].bi;
                    tecnico.email = result[i].email;
                    tecnico.checked = false;
                    $scope.tecnicos.push(tecnico);
                }
            });
        };
;

        $scope.saveGiro = function(){
            GiroCab.update($scope.giroCab,
                function (data) {
                    for(var i = 0;i<$scope.tecnicos.length;i++){
                        if($scope.tecnicos[i].checked){
                            $scope.giroFunc ={giroCab:{id:angular.toJson(data.id)},tecnico:{id:$scope.tecnicos[i].id}};
                            GiroFunc.update($scope.giroFunc,
                                function(data){

                                });
                        }
                    }
                });
        };

        $scope.loadAllTecnico();
    });
