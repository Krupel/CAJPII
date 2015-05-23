'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tecnico', {
                parent: 'entity',
                url: '/tecnico',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.tecnico.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tecnico/tecnicos.html',
                        controller: 'TecnicoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tecnico');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tecnicoDetail', {
                parent: 'entity',
                url: '/tecnico/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.tecnico.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tecnico/tecnico-detail.html',
                        controller: 'TecnicoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tecnico');
                        return $translate.refresh();
                    }]
                }
            });
    });
