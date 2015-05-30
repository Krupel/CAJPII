'use strict';

angular.module('girosApp')
    .controller('ResumoMensalController', function ($scope, Amigos,Principal,$http,GiroLin) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.listResumo = [];
        $scope.weeks =[];
        $scope.month = 0;
        $scope.year = 1900;

        $scope.rebuildResumo = function(){
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

                    if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[0].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[0].endDate){
                        tempResumo.s1 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[1].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[1].endDate){
                        tempResumo.s2 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[2].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[2].endDate){
                        tempResumo.s3 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[3].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[3].endDate){
                        tempResumo.s4 = "X";
                    }

                    if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[4].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[4].endDate){
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

                            if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[0].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[0].endDate){
                                $scope.listResumo[m].s1 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[1].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[1].endDate){
                                $scope.listResumo[m].s2 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[2].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[2].endDate){
                                $scope.listResumo[m].s3 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[3].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[3].endDate){
                                $scope.listResumo[m].s4 = "X";
                            }

                            if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[4].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[4].endDate){
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

                        if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[0].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[0].endDate){
                            tempResumo.s1 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[1].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[1].endDate){
                            tempResumo.s2 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[2].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[2].endDate){
                            tempResumo.s3 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[3].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[3].endDate){
                            tempResumo.s4 = "X";
                        }

                        if(new Date($scope.resumo[i].giroCab.data) >= $scope.weeks[4].firstDate && new Date($scope.resumo[i].giroCab.data) < $scope.weeks[4].endDate){
                            tempResumo.s5 = "X";
                        }
                        $scope.listResumo.push(tempResumo);
                    }
                }
            }
        };

        $scope.fillWeeks = function(month,year){
            $scope.weeks = [{id:0,firstDate:0,endDate:0},{id:1,firstDate:0,endDate:0},{id:2,firstDate:0,endDate:0},{id:3,firstDate:0,endDate:0},{id:4,firstDate:0,endDate:0}];
            var numDays =$scope.daysInMonth(month,year);

            var tempday=1;
            for(var i=0;i<$scope.weeks.length;i++){
                if($scope.weeks[i].id === i){
                    if(tempday<numDays){
                        $scope.weeks[i].firstDate =  new Date(year+"/"+month+"/"+tempday);
                        tempday = tempday+7;
                        if(tempday<numDays){
                            $scope.weeks[i].endDate = new Date(year+"/"+month+"/"+tempday);}
                        else{$scope.weeks[i].endDate = new Date(year+"/"+month+"/"+numDays);}
                    }
                }
            }

            $scope.rebuildResumo();
        };

        $scope.daysInMonth = function(month,year) {
            return new Date(year, month, 0).getDate();
        }

        $scope.pesquisar = function(month,year){
            $http({
                method:'GET',
                url:'api/resumobydatas',
                params: {date_de:$scope.formatDate(new Date("2015/01/01")),date_ate:$scope.formatDate(new Date("2016/01/01"))}
            }).
                success(function(data){
                    $scope.resumo =[];
                    for (var i = 0; i < data.length; i++) {
                        $scope.resumo.push(angular.fromJson(data[i]));
                    }

                    $scope.listResumo = [];
                    $scope.fillWeeks(month,year);
                });

        };

        $scope.formatDate = function(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return [year, month, day].join('-');
        }

    });


