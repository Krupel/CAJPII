'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('amigos', {
                parent: 'entity',
                url: '/amigos',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.amigos.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/amigos/amigoss.html',
                        controller: 'AmigosController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('amigos');
                        return $translate.refresh();
                    }]
                }
            })
            .state('amigosDetail', {
                parent: 'entity',
                url: '/amigos/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.amigos.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/amigos/amigos-detail.html',
                        controller: 'AmigosDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('amigos');
                        return $translate.refresh();
                    }]
                }
            });
    });
