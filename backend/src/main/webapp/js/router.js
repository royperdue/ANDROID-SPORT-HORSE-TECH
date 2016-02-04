var router = angular.module('angular-google-api-horse.router', []);

router.config(['$urlRouterProvider',
    function($urlRouterProvider) {
        $urlRouterProvider.otherwise("/login");
        document.getElementById("login").style.visibility = "hidden";
    }]);

router.config(['$stateProvider',
    function($stateProvider) {
        $stateProvider
            .state('login', {
                url :'/login',
                views :  {
                    '': {
                        templateUrl: 'partials/login.html',
                        controller: 'login.controller',
                    },
                },
            }).state('home', {
                url :'/',
                views :  {
                    '': {
                        controller: 'NewHorseCtrl',
                        templateUrl: 'partials/home.html',
                    },
                },
            }).state('intro', {
                  url :'/',
                  views :  {
                      '': {
                          controller: 'IntroCtrl',
                          templateUrl: 'partials/intro.html',
                      },
                  },
              }).state('features', {
                  url :'/',
                  views :  {
                      '': {
                          controller: 'FeaturesCtrl',
                          templateUrl: 'partials/features.html',
                      },
                  },
              }).state('howItWorks', {
                  url :'/',
                  views :  {
                      '': {
                          controller: 'HowItWorksCtrl',
                          templateUrl: 'partials/howItWorks.html',
                      },
                  },
              }).state('screenshots', {
                  url :'/',
                  views :  {
                      '': {
                          controller: 'ScreenshotsCtrl',
                          templateUrl: 'partials/screenshots.html',
                      },
                  },
              }).state('video', {
                  url :'/',
                  views :  {
                      '': {
                          controller: 'VideoCtrl',
                          templateUrl: 'partials/video.html',
                      },
                  },
              }).state('reviews', {
                  url :'/',
                  views :  {
                      '': {
                          controller: 'ReviewCtrl',
                          templateUrl: 'partials/reviews.html',
                      },
                  },
              })
}])