'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('giro', {
                parent: 'site',
                url: '/giro',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.giroCab.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giro/giro.html',
                        controller: 'GiroController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            });
    });
