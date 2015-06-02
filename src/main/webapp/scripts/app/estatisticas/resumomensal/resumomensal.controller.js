'use strict';

angular.module('girosApp')
    .controller('ResumoMensalController', function ($scope, Amigos,Principal,$http,GiroLin,ParseLinks) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.resumo=[];
        $scope.weeks =[];

        var today = new Date();
        $scope.month = today.getMonth()+1;
        $scope.year = today.getFullYear();

        $scope.listResumo = [];


        $scope.rebuildResumo = function(){
            var tempResumo =
            {
                id:"",
                name:"",
                dtNasc:"",
                st:"",sc:"",gr:"",
                s1:"",s2:"",s3:"",s4:"",s5:"",
                sexo:"",
                nacionalidade:"",
                obs:[
                    {
                        obsl:""
                    }
                ]
            };
            var existe= false;

            $scope.listResumo = [];

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

                    tempResumo.sexo = $scope.resumo[i].utente.sexo;
                    tempResumo.nacionalidade =$scope.resumo[i].utente.nacionalidade;

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

                            $scope.listResumo[m].sexo = $scope.resumo[i].utente.sexo;
                            $scope.listResumo[m].nacionalidade =$scope.resumo[i].utente.nacionalidade;

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
                            sexo:"",
                            nacionalidade:"",
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

                        tempResumo.sexo = $scope.resumo[i].utente.sexo;
                        tempResumo.nacionalidade =$scope.resumo[i].utente.nacionalidade;

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
            $scope.estats();
        };

        $scope.daysInMonth = function(month,year) {
            return new Date(year, month, 0).getDate();
        }

        $scope.estats = function(){

            //$scope.listResumo -> lista resumo
            //$scope.resumo.length -> lista completa

            $scope.listNacionalidade = [];
            $scope.listIdades = [0, 0, 0, 0, 0];
            $scope.sTecto=0;
            $scope.sCasa=0;
            $scope.gRisco=0;
            $scope.sf=0;
            $scope.sm=0;

            var existe=false;
            var iidade;
            var nac =
            {
                nacionalidade:"",
                quant:0,
            };

            for (var i = 0; i < $scope.listResumo.length; i++) {
                if($scope.listResumo[i].st === "X")
                    $scope.sTecto=$scope.sTecto+1;
                if($scope.listResumo[i].sc === "X")
                    $scope.sCasa=$scope.sCasa+1;
                if($scope.listResumo[i].gr === "X")
                    $scope.gRisco=$scope.gRisco+1;

                if($scope.listResumo[i].sexo === "f")
                    $scope.sf=$scope.sf+1;
                else
                    $scope.sm=$scope.sm+1;


                //Json Nacionalidade
                if(i===0){
                    nac.nacionalidade = $scope.listResumo[i].nacionalidade;
                    nac.quant=1;
                    $scope.listNacionalidade.push(nac);
                }
                else{
                    existe=false;
                    for (var m = 0; m < $scope.listNacionalidade.length; m++) {
                        if($scope.listResumo[i].nacionalidade === $scope.listNacionalidade[m].nacionalidade){
                            existe=true;

                            $scope.listNacionalidade[m].quant = $scope.listNacionalidade[m].quant +1;
                        }
                    }
                    if(existe===false){
                        nac =
                        {
                            nacionalidade:"",
                            quant:"",
                        };
                        nac.nacionalidade = $scope.listResumo[i].nacionalidade;
                        nac.quant=1;
                        $scope.listNacionalidade.push(nac);
                    }
                }

                //Json Idade
                iidade = $scope.idade($scope.listResumo[i].dtNasc);
                if (iidade <20)
                    $scope.listIdades[0]=$scope.listIdades[0]+1;
                if (iidade >= 20 && iidade <=29)
                    $scope.listIdades[1]=$scope.listIdades[1]+1;
                if (iidade > 29 && iidade <=39)
                    $scope.listIdades[2]=$scope.listIdades[2]+1;
                if (iidade > 39 && iidade <=49)
                    $scope.listIdades[3]=$scope.listIdades[3]+1;
                if (iidade > 49)
                    $scope.listIdades[4]=$scope.listIdades[4]+1;
            }
        };

        $scope.idade = function(dtNasc){
            var today = new Date();
            var birthDate = new Date(dtNasc);
            var age = today.getFullYear() - birthDate.getFullYear();
            var m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            return age;
        };

        $scope.pesquisar = function(month,year){
            var numDays =$scope.daysInMonth(month,year);
            var dateini= new Date(year,month-1,1);
            var dateend= new Date(year,month-1,numDays);

            $http({
                method:'GET',
                url:'api/resumobydatas',
                params: {date_de:$scope.formatDate(dateini),date_ate:$scope.formatDate(dateend)}
            }).
                success(function(data){
                    $scope.resumo =[];
                    for (var i = 0; i < data.length; i++) {
                        $scope.resumo.push(angular.fromJson(data[i]));
                    }

                    $scope.fillWeeks(month,year);
                    $scope.rebuildResumo();
                });
        };

        $scope.formatDate = function(date) {

            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + date.getDate(),
                year = d.getFullYear();


            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return [year, month, day].join('-');
        };

        $scope.export = function(month,year){
            var blob = new Blob([document.getElementById('exportable').innerHTML], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
            });
            saveAs(blob, "ResumoMensal_"+month+year+".xls");
        };

    });


