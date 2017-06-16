/**  Home - ctrls.preview.js */
define(['angular', 'app/home/module', 'bootStrap', 'dateTimePicker', 'angularUIRoute',
    'app/requests/project'],
function (angular, app) {
	app.controller('previewCtrl', ['$scope', '$stateParams', '$location',
                   'customAlert', 'srvCookie', '$sce', '$rootScope', 'srvProject',
    function ($scope, $stateParams, $location, $customAlert, $srvCookie, $sce, $rootScope, srv) {
    	$scope.fileID = $location.search().fileID;
        $scope.curHost = window.location.host;
        $scope.type = $location.search().type? $location.search().type: '';
    	$scope.demoLink = 'test_preview.doc';
        $rootScope.$emit('updatePathway',{type:'project', curFile:'Preview '+ $scope.type});

        if($scope.curHost === '172.16.129.183') {
            $scope.curHost = '172.16.129.183';
        } else {
            $scope.curHost = '52.168.145.0';
        }
        getFilePathForID();
        $scope.trustSrc = function(url) {
            return $sce.trustAsResourceUrl(url);
        };
        function getFilePathForID() {
            var params = {};
            params.type = $scope.type;
            params.id = $scope.fileID;
            srv.getFilePath(params).then(function(result){
                if(result.code === 'ACK' && result.data !== '') {
                    var previewFile = result.data.replace(/\\/g,'/');
                     $scope.previewUrl = 'http://'+ $scope.curHost +'/docview'+ encodeURI(previewFile);
                } else {
                    $scope.previewUrl = '';
                    $customAlert.warning(result.message);
                }
            },function(result){
                $scope.previewUrl = '';
                $customAlert.alert(result.message + ' Please contact the admin.');
            });
        };
    }]);
});
