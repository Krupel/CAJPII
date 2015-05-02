'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tipologia', {
                parent: 'entity',
                url: '/tipologia',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.tipologia.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipologia/tipologias.html',
                        controller: 'TipologiaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tipologia');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tipologiaDetail', {
                parent: 'entity',
                url: '/tipologia/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.tipologia.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipologia/tipologia-detail.html',
                        controller: 'TipologiaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tipologia');
                        return $translate.refresh();
                    }]
                }
            });
    });
