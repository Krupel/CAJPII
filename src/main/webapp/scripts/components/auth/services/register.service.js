'use strict';

angular.module('girosApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


