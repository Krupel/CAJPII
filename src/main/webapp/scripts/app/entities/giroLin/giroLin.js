'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('giroLin', {
                parent: 'entity',
                url: '/giroLin',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroLin.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giroLin/giroLins.html',
                        controller: 'GiroLinController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giroLin');
                        return $translate.refresh();
                    }]
                }
            })
            .state('giroLinDetail', {
                parent: 'entity',
                url: '/giroLin/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroLin.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giroLin/giroLin-detail.html',
                        controller: 'GiroLinDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giroLin');
                        return $translate.refresh();
                    }]
                }
            });
    });
