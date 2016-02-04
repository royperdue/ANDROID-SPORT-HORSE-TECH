horseApi.controller('login.controller', function($scope,$window,$timeout,GApi,GAuth,GData,$state) {

    function clientList($scope, GAuth, GData, $state) {
        if(GData.isLogin()) {
            $state.go('home');
        }
    }

    $scope.doLogin = function() {
        GAuth.login().then(function(){
            $state.go('home');
        });
    };

});
