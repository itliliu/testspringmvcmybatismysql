define(['angular', 'app/account/module', 'bootStrap','angularUIRoute'],
function (angular, app) {
	app.controller('unauthorizedCtrl', ['$scope', '$location','$state', '$interval',
    function ($scope, $location, $state, $interval) {
        $scope.status = $location.search().status? $location.search().status: 404;
        getErrorText();
        $scope.pageName = 'home';
        $scope.jumpTohome = function() {
            $state.go('home.project.list');
        };
        $scope.seconds = 5;
        var timer = function() {
            $scope.seconds--;
            if($scope.seconds === 0) {
                $interval.cancel($scope.countdown);
                $scope.jumpTohome();
            };
        };
        $scope.countdown = $interval(timer,1000);
        function getErrorText() {
            var status = parseInt($scope.status, 10);
            switch(status) {
                case 401:
                case 403:
                    $scope.text = 'Access denied. Please contact your '+
                    'administrator if need permission to access this page.';
                    break;
                case 404:
                    $scope.text = 'This is not the web page you are looking for.';
                    break;
                default:
                    $scope.text = 'The page is wrong. Please contact your administrator.';
                    break;
            }
        };
	}]);
});
