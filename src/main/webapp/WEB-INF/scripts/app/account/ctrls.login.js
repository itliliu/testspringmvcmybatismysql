/**  Account - ctrls.login.js */
define(['angular', 'app/account/module', 'angularUIRoute', 'app/requests/account'], function (angular, app) {
    app.controller('accountLoginCtrl', ['$scope', '$state', 'srvAccount',
        '$timeout', 'srvCookie', '$location', 'customAlert',
        function ($scope, $state, srv, $timeout, $srvCookie, $location, $customAlert) {
            //use it for dev
            $scope.isDev =  true;
            $scope.loginForDev = function() {
                var params = {username: $scope.username, password: $scope.password};
                srv.loginForLocal(params).then(function (result) {
                    if(result.code === 'NACK') {
                        $customAlert.warning(result.message);
                    } else {
                        $srvCookie.put('userInfo', result.data);
                        if(result.data.status === 1) {
                            $state.go('home.project.list');
                        } else {
                            $customAlert.warning('Please contact your administrator to obtain permissions.');
                        }
                    }
                }, function (result) {
                    $customAlert.alert(result.message);
                });
            };
            /*var host = window.location.host;
            if($srvCookie.get('logout')) {
                $srvCookie.remove('permission');
            };
            $scope.isDev =  false;
            if(host.indexOf('172.16.129.246') > -1 || host.indexOf('52.168.145.0') > -1) {
                login();
            } else {
                //use it for dev
                $scope.isDev =  true;
                $scope.loginForDev = function() {
                    loginForDev();
                };
            }
            function login() {
                $scope.data = $srvCookie.get('permission');
                $srvCookie.put('permission','');
                if(!$scope.data) {
                    requestLogin();
                } else {
                    $srvCookie.put('userInfo', $scope.data);
                    if($scope.data.status === 1) {
                        $state.go('home.project.list');
                    } else {
                        $customAlert.warning('Please contact your administrator to obtain permissions.');
                    }
                }
            };
            function requestLogin() {
                srv.login().then(function (result) {
                    if(result.code === 'NACK') {
                        $customAlert.warning(result.message);
                    }
                });
            };*/
        }]);
});
