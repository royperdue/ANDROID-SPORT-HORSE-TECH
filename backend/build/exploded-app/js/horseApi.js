var horseApi = angular.module('horseApi', ['ui.router', 'angular-google-gapi', 'angular-google-api-horse.router'])
.run(['GAuth', 'GApi', 'GData', '$state', '$rootScope', '$window',
    function(GAuth, GApi, GData, $state, $rootScope, $window) {

        $rootScope.gdata = GData;

        var CLIENT = '378118509102-lplka736j8irn1scev0jbo2ui78t99t5.apps.googleusercontent.com';
        var BASE;
        if($window.location.hostname == 'localhost') {
            BASE = 'http://localhost:8080/_ah/api';
        } else {
            BASE = 'https://sporthorsetech.appspot.com/_ah/api';
        }

        GApi.load('horseApi', 'v1', BASE);
        GAuth.setClient(CLIENT);
        GAuth.setScope('https://www.googleapis.com/auth/userinfo.email');
        GAuth.checkAuth().then(
            function () {
                if($state.includes('login'))
                    $state.go('home');
            }, function() {
                $state.go('login');
            }
        );

        $rootScope.logout = function() {
            GAuth.logout().then(
            function () {
                $state.go('login');
            });
        };
    }
]);