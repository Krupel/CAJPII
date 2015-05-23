'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('giroFunc', {
                parent: 'entity',
                url: '/giroFunc',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroFunc.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giroFunc/giroFuncs.html',
                        controller: 'GiroFuncController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giroFunc');
                        return $translate.refresh();
                    }]
                }
            })
            .state('giroFuncDetail', {
                parent: 'entity',
                url: '/giroFunc/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroFunc.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giroFunc/giroFunc-detail.html',
                        controller: 'GiroFuncDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giroFunc');
                        return $translate.refresh();
                    }]
                }
            });
    });
