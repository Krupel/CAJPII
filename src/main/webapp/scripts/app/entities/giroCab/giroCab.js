'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('giroCab', {
                parent: 'entity',
                url: '/giroCab',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroCab.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giroCab/giroCabs.html',
                        controller: 'GiroCabController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giroCab');
                        return $translate.refresh();
                    }]
                }
            })
            .state('giroCabDetail', {
                parent: 'entity',
                url: '/giroCab/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroCab.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giroCab/giroCab-detail.html',
                        controller: 'GiroCabDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giroCab');
                        return $translate.refresh();
                    }]
                }
            });
    });
