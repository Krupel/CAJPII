'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('girosCab', {
                parent: 'entity',
                url: '/girosCab',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.girosCab.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/girosCab/girosCabs.html',
                        controller: 'GirosCabController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('girosCab');
                        return $translate.refresh();
                    }]
                }
            })
            .state('girosCabDetail', {
                parent: 'entity',
                url: '/girosCab/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.girosCab.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/girosCab/girosCab-detail.html',
                        controller: 'GirosCabDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('girosCab');
                        return $translate.refresh();
                    }]
                }
            });
    });
