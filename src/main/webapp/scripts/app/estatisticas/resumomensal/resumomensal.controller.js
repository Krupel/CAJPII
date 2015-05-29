'use strict';

angular.module('girosApp')
    .controller('ResumoMensalController', function ($scope, Amigos,Principal,$http,GiroLin) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.listResumo = [];

        $scope.rebuildResumo = function(dataRef){
            var tempResumo =
            {
                id:"",
                name:"",
                dtNasc:"",
                st:"",sc:"",gr:"",
                s1:"",s2:"",s3:"",s4:"",s5:"",
                obs:[
                    {
                        obsl:""
                    }
                ]
            };
            var existe= false;


            for (var i = 0; i < $scope.resumo.length; i++) {
                if(i===0){
                    tempResumo.id = $scope.resumo[i].utente.id;
                    tempResumo.name = $scope.resumo[i].utente.nome;
                    tempResumo.dtNasc = $scope.resumo[i].utente.datanascimento;
                    if($scope.resumo[i].utente.tipologiaAmigos.id === 1)
                      tempResumo.st = "X";
                    if($scope.resumo[i].utente.tipologiaAmigos.id === 2)
                        tempResumo.sc = "X";
                    if($scope.resumo[i].utente.tipologiaAmigos.id === 3)
                        tempResumo.gr = "X";

                    if(new Date($scope.resumo[i].giroCab.data) >= new Date('2015-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2015-05-29')){
                        tempResumo.s1 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= new Date('2015-05-29') && new Date($scope.resumo[i].giroCab.data) < new Date('2015-05-30')){
                        tempResumo.s2 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= new Date('2014-01-01') && new Date($scope.resumo[i].giroCab.data) < new Date('2014-05-29')){
                        tempResumo.s3 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= new Date('2016-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2016-05-29')){
                        tempResumo.s4 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= new Date('2016-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2017-05-29')){
                        tempResumo.s5 = "X";
                    }
                    $scope.listResumo.push(tempResumo);
                }else{
                    existe=false;
                    for (var m = 0; m < $scope.listResumo.length; m++) {

                        if($scope.listResumo[m].id===$scope.resumo[i].utente.id){
                            existe=true;

                            if($scope.resumo[i].utente.tipologiaAmigos.id === 1)
                                $scope.listResumo[m].st = "X";
                            if($scope.resumo[i].utente.tipologiaAmigos.id === 2)
                                $scope.listResumo[m].sc = "X";
                            if($scope.resumo[i].utente.tipologiaAmigos.id === 3)
                                $scope.listResumo[m].gr = "X";

                            if(new Date($scope.resumo[i].giroCab.data) >= new Date('2015-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2015-05-29')){
                                $scope.listResumo[m].s1 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= new Date('2015-05-29') && new Date($scope.resumo[i].giroCab.data) < new Date('2015-05-30')){
                                $scope.listResumo[m].s2 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= new Date('2014-01-01') && new Date($scope.resumo[i].giroCab.data) < new Date('2014-05-29')){
                                $scope.listResumo[m].s3 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= new Date('2016-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2016-05-29')){
                                $scope.listResumo[m].s4 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= new Date('2016-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2017-05-29')){
                                $scope.listResumo[m].s5 = "X";
                            }
                        }
                    }

                    if(existe === false){
                        tempResumo = {
                            id:"",
                            name:"",
                            dtNasc:"",
                            st:"",sc:"",gr:"",
                            s1:"",s2:"",s3:"",s4:"",s5:"",
                            obs:[
                                {
                                    obsl:""
                                }
                            ]
                        };
                        tempResumo.id = $scope.resumo[i].utente.id;
                        tempResumo.name = $scope.resumo[i].utente.nome;
                        tempResumo.dtNasc = $scope.resumo[i].utente.datanascimento;
                        if($scope.resumo[i].utente.tipologiaAmigos.id === 1)
                            tempResumo.st = "X";
                        if($scope.resumo[i].utente.tipologiaAmigos.id === 2)
                            tempResumo.sc = "X";
                        if($scope.resumo[i].utente.tipologiaAmigos.id === 3)
                            tempResumo.gr = "X";

                        if(new Date($scope.resumo[i].giroCab.data) >= new Date('2015-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2015-05-29')){
                            tempResumo.s1 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= new Date('2015-05-29') && new Date($scope.resumo[i].giroCab.data) < new Date('2015-05-30')){
                            tempResumo.s2 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= new Date('2014-01-01') && new Date($scope.resumo[i].giroCab.data) < new Date('2014-05-29')){
                            tempResumo.s3 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= new Date('2016-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2016-05-29')){
                            tempResumo.s4 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= new Date('2016-05-28') && new Date($scope.resumo[i].giroCab.data) < new Date('2017-05-29')){
                            tempResumo.s5 = "X";
                        }
                        $scope.listResumo.push(tempResumo);
                    }
                }
            }
        }

        $scope.pesquisar = function(){
            $http({
                method:'GET',
                url:'api/resumobydatas'
            }).
                success(function(data){
                    $scope.resumo =[];
                    for (var i = 0; i < data.length; i++) {
                        $scope.resumo.push(angular.fromJson(data[i]));
                    }

                    $scope.rebuildResumo();
                });

        };

        $scope.pesquisar();
    });
