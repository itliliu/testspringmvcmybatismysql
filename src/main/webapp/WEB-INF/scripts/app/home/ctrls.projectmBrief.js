/**  Home - ctrls.projectForm.js */
define(['angular', 'app/home/module', 'bootStrap', 'dateTimePicker', 'angularUIRoute', 'app/requests/projectForm'],
function (angular, app) {
	app.controller('projectmBriefCtrl', ['$scope','srvProjectForm', '$rootScope', '$stateParams',
        '$location', 'customAlert', 'srvCookie',
    function ($scope, srv, $rootScope, $stateParams, $location, $customAlert, $srvCookie) {
        $scope.toggle=function (item) {
            item.isFavorite = !item.isFavorite;
        }
        $scope.selectAll=false;
        $scope.inSelectAll=false;

        $scope.briefList = [];
        $scope.inprocessList = [];
        var item = {
            id:'87682994',
            title:'Request Title',
            date:'08.06.2017',
            isSelected:false,
            isFavorite:false
        };
	    for(var i=0; i<10; i++){
            $scope.briefList.push($.extend({},item));
            $scope.inprocessList.push($.extend({},item));

        }

        $scope.received= function (m) {
            for(var i=0;i<$scope.briefList.length;i++){
                if(m===true){
                    $scope.briefList[i].state=true;
                }else {
                    $scope.briefList[i].state=false;
                }
            }
        };
        $scope.Progress= function (m) {
            for(var i=0;i<$scope.inprocessList.length;i++){
                if(m===true){
                    $scope.inprocessList[i].state=true;
                }else {
                    $scope.inprocessList[i].state=false;
                }
            }
        };

    }]);
});
