horseApi.controller('NewHorseCtrl', function($scope,$window,$timeout,GApi) {

    /** Fields variables **/
    $scope.results = null;
    $scope.updateFlag = false;
    $scope.showError = false;
    $scope.doFade = false;
    $scope.horse = {
        id : null,
        horseName : null,
        reg_date : null
    };

    /** Angular functions **/
    $scope.fakeError = function(msg) {
        //reset
        $scope.showError = false;
        $scope.doFade = false;
        $scope.showError = true;
        $scope.errorMessage = msg;
        $timeout(function() {
          $scope.doFade = true;
        }, 2500);
    };

    $scope.horses = function() {
      $scope.updateFlag = false;
      GApi.executeAuth('horseApi', 'horses.list').then(function(resp) {
              $scope.results = resp.items;
          }, function() {
            $scope.fakeError("You must be authenticated");
          });
    };

    $scope.submit = function(isValid) {
        // validate the form
        if (!isValid) {
            $scope.fakeError('Sorry! You must fill in all fields!');
            return;
        }

        // execute action
        if ($scope.updateFlag) {
            update();
        } else {
            save();
        }
    }

    $scope.remove = function(id) {
        var answer = $window.confirm("Do you have sure?");
        if (answer) {
            doRemove(id);
        }
    }

    $scope.prepareUpdate = function(id) {
        GApi.executeAuth('horseApi', 'horse.by.id', {id : id}).then(function(resp) {
            $scope.updateFlag = true;
            fillHorse(resp);
        }, function() {
            $scope.fakeError("You must be authenticated");
        });
    }

    /** Javascript functions **/

    function doRemove(id) {
        GApi.executeAuth('horseApi', 'horse.remove', {id : id}).then(function(resp) {
            $scope.horses();
        }, function() {
            $scope.fakeError("You must be authenticated");
        });
    }

     function save() {
        GApi.executeAuth('horseApi', 'horse.save', $scope.horse).then(function(resp) {
            $scope.horses();
            clear();
        }, function() {
            $scope.fakeError("You must be authenticated");
        });
     }

     function update() {
        GApi.executeAuth('horseApi', 'horse.update', $scope.horse).then(function(resp) {
            $scope.horses();
            clear();
        }, function() {
            $scope.fakeError("You must be authenticated");
        });
     }

    function fillHorse(horseLoaded) {
        $scope.horse.id = horseLoaded.id;
        $scope.horse.horseName = horseLoaded.horseName;
        $scope.horse.reg_date = horseLoaded.reg_date;
    }

    function clear() {
        $scope.horse.id = null;
        $scope.horse.horseName = null;
    }

    // load horses when open the page
    $scope.horses();
});
