'use strict';

angular.module('girosApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
