'use strict';

angular.module('girosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('funcionario', {
                parent: 'entity',
                url: '/funcionario',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.funcionario.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/funcionario/funcionarios.html',
                        controller: 'FuncionarioController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('funcionario');
                        return $translate.refresh();
                    }]
                }
            })
            .state('funcionarioDetail', {
                parent: 'entity',
                url: '/funcionario/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'girosApp.funcionario.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/funcionario/funcionario-detail.html',
                        controller: 'FuncionarioDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('funcionario');
                        return $translate.refresh();
                    }]
                }
            });
    });
