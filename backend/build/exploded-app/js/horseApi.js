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
                {
                    $state.go('home');
                    document.getElementById("logout").style.visibility = "visible";
                    document.getElementById("login").style.visibility = "hidden";
                }
            }, function() {
                $state.go('login');
                document.getElementById("logout").style.visibility = "hidden";
                document.getElementById("login").style.visibility = "hidden";
            }
        );

        $rootScope.logout = function() {
            GAuth.logout().then(
            function () {
                $state.go('intro');
                document.getElementById("logout").style.visibility = "hidden";
                document.getElementById("login").style.visibility = "visible";
            });
        };

        $rootScope.intro = function() {
                GAuth.logout().then(
                   function () {
                       $state.go('intro');
                       document.getElementById("logout").style.visibility = "hidden";
                       document.getElementById("login").style.visibility = "visible";
                   });
            };

        $rootScope.features = function() {
                        GAuth.logout().then(
                           function () {
                               $state.go('features');
                               document.getElementById("logout").style.visibility = "hidden";
                               document.getElementById("login").style.visibility = "visible";
                           });
                    };

        $rootScope.login = function() {
            $state.go('login');
            document.getElementById("login").style.visibility = "hidden";
        };

        $rootScope.howItWorks = function() {
                    GAuth.logout().then(
                       function () {
                           $state.go('howItWorks');
                           document.getElementById("logout").style.visibility = "hidden";
                           document.getElementById("login").style.visibility = "visible";
                       });
                };

        $rootScope.screenshots = function() {
                    GAuth.logout().then(
                       function () {
                           $state.go('screenshots');
                           document.getElementById("logout").style.visibility = "hidden";
                           document.getElementById("login").style.visibility = "visible";
                       });
                };

        $rootScope.video = function() {
                    GAuth.logout().then(
                       function () {
                           $state.go('video');
                           document.getElementById("logout").style.visibility = "hidden";
                           document.getElementById("login").style.visibility = "visible";
                       });
                };

        $rootScope.reviews = function() {
                    GAuth.logout().then(
                       function () {
                           $state.go('reviews');
                           document.getElementById("logout").style.visibility = "hidden";
                           document.getElementById("login").style.visibility = "visible";
                       });
                };
    }
]);