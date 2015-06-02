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

        $scope.paises = {
                            "paises": [
                                {
                                    "name": "Afeganistão",
                                    "code": "AF"
                                },
                                {
                                    "name": "África do Sul",
                                    "code": "ZA"
                                },
                                {
                                    "name": "Albânia",
                                    "code": "AL"
                                },
                                {
                                    "name": "Alemanha",
                                    "code": "DE"
                                },
                                {
                                    "name": "Andorra",
                                    "code": "AD"
                                },
                                {
                                    "name": "Angola",
                                    "code": "AO"
                                },
                                {
                                    "name": "Anguilla",
                                    "code": "AI"
                                },
                                {
                                    "name": "Antárctica",
                                    "code": "AQ"
                                },
                                {
                                    "name": "Antígua e Barbuda",
                                    "code": "AG"
                                },
                                {
                                    "name": "Antilhas Holandesas",
                                    "code": "AN"
                                },
                                {
                                    "name": "Arábia Saudita",
                                    "code": "SA"
                                },
                                {
                                    "name": "Argélia",
                                    "code": "DZ"
                                },
                                {
                                    "name": "Argentina",
                                    "code": "AR"
                                },
                                {
                                    "name": "Arménia",
                                    "code": "AM"
                                },
                                {
                                    "name": "Aruba",
                                    "code": "AW"
                                },
                                {
                                    "name": "Austrália",
                                    "code": "AU"
                                },
                                {
                                    "name": "Áustria",
                                    "code": "AT"
                                },
                                {
                                    "name": "Azerbeijão",
                                    "code": "AZ"
                                },
                                {
                                    "name": "Bahamas",
                                    "code": "BS"
                                },
                                {
                                    "name": "Bahrain",
                                    "code": "BH"
                                },
                                {
                                    "name": "Bangladesh",
                                    "code": "BD"
                                },
                                {
                                    "name": "Barbados",
                                    "code": "BB"
                                },
                                {
                                    "name": "Bélgica",
                                    "code": "BE"
                                },
                                {
                                    "name": "Belize",
                                    "code": "BZ"
                                },
                                {
                                    "name": "Benin",
                                    "code": "BJ"
                                },
                                {
                                    "name": "Bermuda",
                                    "code": "BM"
                                },
                                {
                                    "name": "Bielorrússia",
                                    "code": "BY"
                                },
                                {
                                    "name": "Birmânia",
                                    "code": "MM"
                                },
                                {
                                    "name": "Bolívia",
                                    "code": "BO"
                                },
                                {
                                    "name": "Bósnia e Herzegovina",
                                    "code": "BA"
                                },
                                {
                                    "name": "Botsuana",
                                    "code": "BW"
                                },
                                {
                                    "name": "Brasil",
                                    "code": "BR"
                                },
                                {
                                    "name": "Brunei Darussalã",
                                    "code": "BN"
                                },
                                {
                                    "name": "Bulgária",
                                    "code": "BG"
                                },
                                {
                                    "name": "Burkina Faso",
                                    "code": "BF"
                                },
                                {
                                    "name": "Burundi",
                                    "code": "BI"
                                },
                                {
                                    "name": "Butão",
                                    "code": "BT"
                                },
                                {
                                    "name": "Cabo Verde",
                                    "code": "CV"
                                },
                                {
                                    "name": "Camarões",
                                    "code": "CM"
                                },
                                {
                                    "name": "Camboja",
                                    "code": "KH"
                                },
                                {
                                    "name": "Canadá",
                                    "code": "CA"
                                },
                                {
                                    "name": "Cazaquistão",
                                    "code": "KZ"
                                },
                                {
                                    "name": "Chade",
                                    "code": "TD"
                                },
                                {
                                    "name": "Chile",
                                    "code": "CL"
                                },
                                {
                                    "name": "China",
                                    "code": "CN"
                                },
                                {
                                    "name": "Chipre",
                                    "code": "CY"
                                },
                                {
                                    "name": "Colômbia",
                                    "code": "CO"
                                },
                                {
                                    "name": "Comores",
                                    "code": "KM"
                                },
                                {
                                    "name": "Congo",
                                    "code": "CG"
                                },
                                {
                                    "name": "Coreia do Norte",
                                    "code": "KP"
                                },
                                {
                                    "name": "Coreia do Sul",
                                    "code": "KR"
                                },
                                {
                                    "name": "Costa Rica",
                                    "code": "CR"
                                },
                                {
                                    "name": "Costa do Marfim",
                                    "code": "CI"
                                },
                                {
                                    "name": "Croácia",
                                    "code": "HR"
                                },
                                {
                                    "name": "Cuba",
                                    "code": "CU"
                                },
                                {
                                    "name": "Dinamarca",
                                    "code": "DK"
                                },
                                {
                                    "name": "Djibuti",
                                    "code": "DJ"
                                },
                                {
                                    "name": "Dominica",
                                    "code": "DM"
                                },
                                {
                                    "name": "Egipto",
                                    "code": "EG"
                                },
                                {
                                    "name": "El Salvador",
                                    "code": "SV"
                                },
                                {
                                    "name": "Emirados Árabes Unidos",
                                    "code": "AE"
                                },
                                {
                                    "name": "Equador",
                                    "code": "EC"
                                },
                                {
                                    "name": "Eritreia",
                                    "code": "ER"
                                },
                                {
                                    "name": "Eslováquia",
                                    "code": "SK"
                                },
                                {
                                    "name": "Eslovénia",
                                    "code": "SI"
                                },
                                {
                                    "name": "Espanha",
                                    "code": "ES"
                                },
                                {
                                    "name": "Estados Unidos da América",
                                    "code": "US"
                                },
                                {
                                    "name": "Estónia",
                                    "code": "EE"
                                },
                                {
                                    "name": "Etiópia",
                                    "code": "ET"
                                },
                                {
                                    "name": "Filipinas",
                                    "code": "PH"
                                },
                                {
                                    "name": "Fiji",
                                    "code": "FJ"
                                },
                                {
                                    "name": "Finlândia",
                                    "code": "FI"
                                },
                                {
                                    "name": "França",
                                    "code": "FR"
                                },
                                {
                                    "name": "Gabão",
                                    "code": "GA"
                                },
                                {
                                    "name": "Gâmbia",
                                    "code": "GM"
                                },
                                {
                                    "name": "Gana",
                                    "code": "GH"
                                },
                                {
                                    "name": "Geórgia",
                                    "code": "GE"
                                },
                                {
                                    "name": "Gibraltar",
                                    "code": "GI"
                                },
                                {
                                    "name": "Granada",
                                    "code": "GD"
                                },
                                {
                                    "name": "Grécia",
                                    "code": "GR"
                                },
                                {
                                    "name": "Gronelândia",
                                    "code": "GL"
                                },
                                {
                                    "name": "Guadalupe",
                                    "code": "GP"
                                },
                                {
                                    "name": "Guame",
                                    "code": "GU"
                                },
                                {
                                    "name": "Guatemala",
                                    "code": "GT"
                                },
                                {
                                    "name": "Guernsey",
                                    "code": "GG"
                                },
                                {
                                    "name": "Guiana",
                                    "code": "GY"
                                },
                                {
                                    "name": "Guiana Francesa",
                                    "code": "GF"
                                },
                                {
                                    "name": "Guiné",
                                    "code": "GN"
                                },
                                {
                                    "name": "Guiné Equatorial",
                                    "code": "GQ"
                                },
                                {
                                    "name": "Guiné-Bissau",
                                    "code": "GW"
                                },
                                {
                                    "name": "Haiti",
                                    "code": "HT"
                                },
                                {
                                    "name": "Holanda",
                                    "code": "NL"
                                },
                                {
                                    "name": "Honduras",
                                    "code": "HN"
                                },
                                {
                                    "name": "Hong Kong",
                                    "code": "HK"
                                },
                                {
                                    "name": "Hungria",
                                    "code": "HU"
                                },
                                {
                                    "name": "Índia",
                                    "code": "IN"
                                },
                                {
                                    "name": "Indonésia",
                                    "code": "ID"
                                },
                                {
                                    "name": "Irão",
                                    "code": "IR"
                                },
                                {
                                    "name": "Iraque",
                                    "code": "IQ"
                                },
                                {
                                    "name": "Irlanda",
                                    "code": "IE"
                                },
                                {
                                    "name": "Ilha Bouvet",
                                    "code": "BV"
                                },
                                {
                                    "name": "Ilha Heard e Ilhas Mcdonald",
                                    "code": "HM"
                                },
                                {
                                    "name": "Ilha de Man",
                                    "code": "IM"
                                },
                                {
                                    "name": "Ilha do Natal",
                                    "code": "CX"
                                },
                                {
                                    "name": "Ilha Norfolk",
                                    "code": "NF"
                                },
                                {
                                    "name": "Ilhas Aland",
                                    "code": "AX"
                                },
                                {
                                    "name": "Ilhas Caimã",
                                    "code": "KY"
                                },
                                {
                                    "name": "Ilhas Cook",
                                    "code": "CK"
                                },
                                {
                                    "name": "Ilhas dos Cocos",
                                    "code": "CC"
                                },
                                {
                                    "name": "Ilhas Falkland (Malvinas)",
                                    "code": "FK"
                                },
                                {
                                    "name": "Ilhas Faroé",
                                    "code": "FO"
                                },
                                {
                                    "name": "Ilhas Geórgia do Sul e Sandwich do Sul",
                                    "code": "GS"
                                },
                                {
                                    "name": "Ilhas Marianas Setentrionais",
                                    "code": "MP"
                                },
                                {
                                    "name": "Ilhas Marshall",
                                    "code": "MH"
                                },
                                {
                                    "name": "Ilhas Menores dos Estados Unidos",
                                    "code": "UM"
                                },
                                {
                                    "name": "Ilhas Pitcairn",
                                    "code": "PN"
                                },
                                {
                                    "name": "Ilhas Salomão",
                                    "code": "SB"
                                },
                                {
                                    "name": "Ilhas Turcas e Caicos",
                                    "code": "TC"
                                },
                                {
                                    "name": "Ilhas Virgens Americanas",
                                    "code": "VI"
                                },
                                {
                                    "name": "Ilhas Virgens Britânicas",
                                    "code": "VG"
                                },
                                {
                                    "name": "Ilhas Wallis and Futuna",
                                    "code": "WF"
                                },
                                {
                                    "name": "Islândia",
                                    "code": "IS"
                                },
                                {
                                    "name": "Israel",
                                    "code": "IL"
                                },
                                {
                                    "name": "Itália",
                                    "code": "IT"
                                },
                                {
                                    "name": "Jamaica",
                                    "code": "JM"
                                },
                                {
                                    "name": "Japão",
                                    "code": "JP"
                                },
                                {
                                    "name": "Jersey",
                                    "code": "JE"
                                },
                                {
                                    "name": "Jordânia",
                                    "code": "JO"
                                },
                                {
                                    "name": "Kuwait",
                                    "code": "KW"
                                },
                                {
                                    "name": "Laos",
                                    "code": "LA"
                                },
                                {
                                    "name": "Letónia",
                                    "code": "LV"
                                },
                                {
                                    "name": "Lesoto",
                                    "code": "LS"
                                },
                                {
                                    "name": "Líbano",
                                    "code": "LB"
                                },
                                {
                                    "name": "Libéria",
                                    "code": "LR"
                                },
                                {
                                    "name": "Líbia",
                                    "code": "LY"
                                },
                                {
                                    "name": "Liechtenstein",
                                    "code": "LI"
                                },
                                {
                                    "name": "Lituânia",
                                    "code": "LT"
                                },
                                {
                                    "name": "Luxemburgo",
                                    "code": "LU"
                                },
                                {
                                    "name": "Macau",
                                    "code": "MO"
                                },
                                {
                                    "name": "Macedónia",
                                    "code": "MK"
                                },
                                {
                                    "name": "Madagáscar",
                                    "code": "MG"
                                },
                                {
                                    "name": "Maiote",
                                    "code": "YT"
                                },
                                {
                                    "name": "Malásia",
                                    "code": "MY"
                                },
                                {
                                    "name": "Maláui",
                                    "code": "MW"
                                },
                                {
                                    "name": "Maldivas",
                                    "code": "MV"
                                },
                                {
                                    "name": "Máli",
                                    "code": "ML"
                                },
                                {
                                    "name": "Malta",
                                    "code": "MT"
                                },
                                {
                                    "name": "Marrocos",
                                    "code": "MA"
                                },
                                {
                                    "name": "Martinica",
                                    "code": "MQ"
                                },
                                {
                                    "name": "Maurícia",
                                    "code": "MU"
                                },
                                {
                                    "name": "Mauritânia",
                                    "code": "MR"
                                },
                                {
                                    "name": "México",
                                    "code": "MX"
                                },
                                {
                                    "name": "Micronésia",
                                    "code": "FM"
                                },
                                {
                                    "name": "Moçambique",
                                    "code": "MZ"
                                },
                                {
                                    "name": "Moldávia",
                                    "code": "MD"
                                },
                                {
                                    "name": "Mónaco",
                                    "code": "MC"
                                },
                                {
                                    "name": "Mongólia",
                                    "code": "MN"
                                },
                                {
                                    "name": "Montserrat",
                                    "code": "MS"
                                },
                                {
                                    "name": "Namíbia",
                                    "code": "NA"
                                },
                                {
                                    "name": "Nauru",
                                    "code": "NR"
                                },
                                {
                                    "name": "Nepal",
                                    "code": "NP"
                                },
                                {
                                    "name": "Nicarágua",
                                    "code": "NI"
                                },
                                {
                                    "name": "Nigér",
                                    "code": "NE"
                                },
                                {
                                    "name": "Nigéria",
                                    "code": "NG"
                                },
                                {
                                    "name": "Niue",
                                    "code": "NU"
                                },
                                {
                                    "name": "Noruega",
                                    "code": "NO"
                                },
                                {
                                    "name": "Nova Caledónia",
                                    "code": "NC"
                                },
                                {
                                    "name": "Nova Zelândia",
                                    "code": "NZ"
                                },
                                {
                                    "name": "Omã",
                                    "code": "OM"
                                },
                                {
                                    "name": "Palau",
                                    "code": "PW"
                                },
                                {
                                    "name": "Palestina",
                                    "code": "PS"
                                },
                                {
                                    "name": "Panamá",
                                    "code": "PA"
                                },
                                {
                                    "name": "Papua Nova Guiné",
                                    "code": "PG"
                                },
                                {
                                    "name": "Paquistão",
                                    "code": "PK"
                                },
                                {
                                    "name": "Paraguai",
                                    "code": "PY"
                                },
                                {
                                    "name": "Peru",
                                    "code": "PE"
                                },
                                {
                                    "name": "Polinésia Francesa",
                                    "code": "PF"
                                },
                                {
                                    "name": "Polónia",
                                    "code": "PL"
                                },
                                {
                                    "name": "Porto Rico",
                                    "code": "PR"
                                },
                                {
                                    "name": "Portugal",
                                    "code": "PT"
                                },
                                {
                                    "name": "Qatar",
                                    "code": "QA"
                                },
                                {
                                    "name": "Quénia",
                                    "code": "KE"
                                },
                                {
                                    "name": "Quirguistão",
                                    "code": "KG"
                                },
                                {
                                    "name": "Quiribati",
                                    "code": "KI"
                                },
                                {
                                    "name": "Reino Unido",
                                    "code": "GB"
                                },
                                {
                                    "name": "República Centro-Africana",
                                    "code": "CF"
                                },
                                {
                                    "name": "República Checa",
                                    "code": "CZ"
                                },
                                {
                                    "name": "República Democrática do Congo",
                                    "code": "CD"
                                },
                                {
                                    "name": "República Dominicana",
                                    "code": "DO"
                                },
                                {
                                    "name": "Reunião",
                                    "code": "RE"
                                },
                                {
                                    "name": "Roménia",
                                    "code": "RO"
                                },
                                {
                                    "name": "Ruanda",
                                    "code": "RW"
                                },
                                {
                                    "name": "Rússia",
                                    "code": "RU"
                                },
                                {
                                    "name": "Sahara Ocidental",
                                    "code": "EH"
                                },
                                {
                                    "name": "Samoa",
                                    "code": "WS"
                                },
                                {
                                    "name": "Samoa Americana",
                                    "code": "AS"
                                },
                                {
                                    "name": "San Marino",
                                    "code": "SM"
                                },
                                {
                                    "name": "Santa Helena",
                                    "code": "SH"
                                },
                                {
                                    "name": "Santa Lúcia",
                                    "code": "LC"
                                },
                                {
                                    "name": "São Cristovão e Neves",
                                    "code": "KN"
                                },
                                {
                                    "name": "São Pedro e Miquelão",
                                    "code": "PM"
                                },
                                {
                                    "name": "São Vincente e Granadinas",
                                    "code": "VC"
                                },
                                {
                                    "name": "São Tomé and Príncipe",
                                    "code": "ST"
                                },
                                {
                                    "name": "Seicheles",
                                    "code": "SC"
                                },
                                {
                                    "name": "Senegal",
                                    "code": "SN"
                                },
                                {
                                    "name": "Serra Leoa",
                                    "code": "SL"
                                },
                                {
                                    "name": "Sérvia and Montenegro",
                                    "code": "CS"
                                },
                                {
                                    "name": "Singapura",
                                    "code": "SG"
                                },
                                {
                                    "name": "Síria",
                                    "code": "SY"
                                },
                                {
                                    "name": "Somália",
                                    "code": "SO"
                                },
                                {
                                    "name": "Sri Lanka",
                                    "code": "LK"
                                },
                                {
                                    "name": "Sudão",
                                    "code": "SD"
                                },
                                {
                                    "name": "Suriname",
                                    "code": "SR"
                                },
                                {
                                    "name": "Suazilândia",
                                    "code": "SZ"
                                },
                                {
                                    "name": "Suécia",
                                    "code": "SE"
                                },
                                {
                                    "name": "Suíça",
                                    "code": "CH"
                                },
                                {
                                    "name": "Svalbard e Jan Mayen",
                                    "code": "SJ"
                                },
                                {
                                    "name": "Tailândia",
                                    "code": "TH"
                                },
                                {
                                    "name": "Taiwan",
                                    "code": "TW"
                                },
                                {
                                    "name": "Tajiquistão",
                                    "code": "TJ"
                                },
                                {
                                    "name": "Tanzânia",
                                    "code": "TZ"
                                },
                                {
                                    "name": "Terras Austrais e Antárticas Francesas",
                                    "code": "TF"
                                },
                                {
                                    "name": "Território Britânico do Oceano Índico",
                                    "code": "IO"
                                },
                                {
                                    "name": "Timor-Leste",
                                    "code": "TL"
                                },
                                {
                                    "name": "Togo",
                                    "code": "TG"
                                },
                                {
                                    "name": "Tokelau",
                                    "code": "TK"
                                },
                                {
                                    "name": "Tonga",
                                    "code": "TO"
                                },
                                {
                                    "name": "Trinidade e Tobago",
                                    "code": "TT"
                                },
                                {
                                    "name": "Tunísia",
                                    "code": "TN"
                                },
                                {
                                    "name": "Turquemenistão",
                                    "code": "TM"
                                },
                                {
                                    "name": "Turquia",
                                    "code": "TR"
                                },
                                {
                                    "name": "Tuvalu",
                                    "code": "TV"
                                },
                                {
                                    "name": "Ucrânia",
                                    "code": "UA"
                                },
                                {
                                    "name": "Uganda",
                                    "code": "UG"
                                },
                                {
                                    "name": "Uruguai",
                                    "code": "UY"
                                },
                                {
                                    "name": "Uzbequistão",
                                    "code": "UZ"
                                },
                                {
                                    "name": "Vanuatu",
                                    "code": "VU"
                                },
                                {
                                    "name": "Vaticano",
                                    "code": "VA"
                                },
                                {
                                    "name": "Venezuela",
                                    "code": "VE"
                                },
                                {
                                    "name": "Vietname",
                                    "code": "VN"
                                },
                                {
                                    "name": "Yemen",
                                    "code": "YE"
                                },
                                {
                                    "name": "Zâmbia",
                                    "code": "ZM"
                                },
                                {
                                    "name": "Zimbabwe",
                                    "code": "ZW"
                                }
                            ]
                        };

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
            $scope.utente = {nome: null, datanascimento: null, bi: null, validadebi: null, sexo: null, niss: null, nacionalidade: null, dataregisto: null, caracteristicas: null, activo: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
