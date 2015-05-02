'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('girosLin', {
                parent: 'entity',
                url: '/girosLin',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.girosLin.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/girosLin/girosLins.html',
                        controller: 'GirosLinController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('girosLin');
                        return $translate.refresh();
                    }]
                }
            })
            .state('girosLinDetail', {
                parent: 'entity',
                url: '/girosLin/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.girosLin.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/girosLin/girosLin-detail.html',
                        controller: 'GirosLinDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('girosLin');
                        return $translate.refresh();
                    }]
                }
            });
    });
