'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('girosFunc', {
                parent: 'entity',
                url: '/girosFunc',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.girosFunc.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/girosFunc/girosFuncs.html',
                        controller: 'GirosFuncController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('girosFunc');
                        return $translate.refresh();
                    }]
                }
            })
            .state('girosFuncDetail', {
                parent: 'entity',
                url: '/girosFunc/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.girosFunc.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/girosFunc/girosFunc-detail.html',
                        controller: 'GirosFuncDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('girosFunc');
                        return $translate.refresh();
                    }]
                }
            });
    });
