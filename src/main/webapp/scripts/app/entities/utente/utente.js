'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('utente', {
                parent: 'entity',
                url: '/utente',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.utente.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/utente/utentes.html',
                        controller: 'UtenteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('utente');
                        return $translate.refresh();
                    }]
                }
            })
            .state('utenteDetail', {
                parent: 'entity',
                url: '/utente/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.utente.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/utente/utente-detail.html',
                        controller: 'UtenteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('utente');
                        return $translate.refresh();
                    }]
                }
            });
    });
