'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('historicoUtente', {
                parent: 'entity',
                url: '/historicoUtente',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.historicoUtente.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/historicoUtente/historicoUtentes.html',
                        controller: 'HistoricoUtenteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('historicoUtente');
                        return $translate.refresh();
                    }]
                }
            })
            .state('historicoUtenteDetail', {
                parent: 'entity',
                url: '/historicoUtente/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.historicoUtente.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/historicoUtente/historicoUtente-detail.html',
                        controller: 'HistoricoUtenteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('historicoUtente');
                        return $translate.refresh();
                    }]
                }
            });
    });
