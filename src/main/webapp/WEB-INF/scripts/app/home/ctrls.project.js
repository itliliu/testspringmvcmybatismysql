define(['angular', 'app/home/module','bootStrap','angularUIRoute',
    'app/requests/project'],
function (angular, app) {
	app.controller('projectCtrl', ['$scope', 'srvProject', '$location', '$state',
        'customAlert','$rootScope', 'srvCookie',
    function ($scope, srvProject, $location, $state, $customAlert, $rootScope, $srvCookie) {
        var userInfo = $srvCookie.get('userInfo'),
            curClient = $srvCookie.get('curClient'),
            status = $location.search().status? parseInt($location.search().status, 10): -1;
        $scope.userid = userInfo? userInfo.id: '';
        $scope.status = status;
        $scope.client = curClient? curClient.client: userInfo.clientName;
        $scope.clientID = curClient? curClient.clientID: userInfo.defaultClientID;
        $scope.showList = false;
        $scope.showPrompt = 'Please add your project brief.';
        $scope.keyword = '';
        $scope.isAll = 'active';
        $scope.isShowClient = 'hidden';
        $scope.isShowChildClient = 'hidden';
        $scope.chilidClient = '';
        $scope.isDeactivated = 0;

        initializeProject();
        $scope.toggleAddForm = function() {
            var clientID = parseInt($scope.clientID, 10),
                isDeactivated = parseInt($scope.isDeactivated, 10);
            if(isDeactivated === 1) {
               $customAlert.warning('The client cannot create project. Please select a client in the menu list again.');
            } else if( clientID !== -1) {
               $state.go('home.project.form.brief',{'id':0,'status':-1});
            } else {
               $customAlert.warning('Please select a client in the menu list.');
            }
        };
        $scope.switchStatus = function($event) {
            var obj = angular.element($event.currentTarget),
                type = obj.html();

            $('.switch-btn').removeClass('active');
            obj.addClass('active');
            $scope.switchStatusText(type);
            showList();
        };
        $scope.searchBrief = function() {
           updateProjectListForClient();
        };
        $scope.blurSearchBrief = function($event) {
            var keycode = window.event?$event.keyCode:$event.which;
            if(keycode === 13){
                updateProjectListForClient();
            };
        };
        function showList() {
             var params = {};
             params.status = $scope.status;
             if($scope.keyword) {
                params.keyword = $scope.keyword;
             }
             $state.go('home.project.list',params);
        };
        function showCurStatus(statusType) {
            $scope.isInitial = '';
            $scope.isProposal = '';
            $scope.isApprove = '';
            $scope.isRequest = '';
            $scope.isReporting = '';
            $scope.isComplete = '';
            $scope.isAll = '';

            switch(statusType) {
                case 0:
                    $scope.isInitial = 'active';
                    break;
                case 1:
                    $scope.isProposal = 'active';
                    break;
                case 2:
                    $scope.isApprove = 'active';
                    break;
                case 3:
                    $scope.isRequest = 'active';
                    break;
                case 4:
                    $scope.isReporting = 'active';
                    break;
                case 5:
                    $scope.isComplete = 'active';
                    break;
                default:
                    $scope.isAll = 'active';
                    break;
            };
        };
        function initializeProject() {
            showCurStatus($scope.status);;
        };
        $scope.switchStatusText = function(tabType) {
            var statusValue = -1, text = '',
                type = tabType.toLowerCase();
            //switch data for type
            //-1 all, 0 - initial, 1 - proposal, 2- approve, 3 - request, 4 - reporting, 5- complete
            switch(type) {
                case 'initial':
                        statusValue = 0;
                        text = 'No initial data.';
                        break;
                case 'proposal':
                        statusValue = 1;
                        text = 'No proposal data.';
                        break;
                case 'client approve':
                        statusValue = 2;
                        text = 'No client approve data.';
                        break;
                case 'request':
                        statusValue = 3;
                        text = 'No request data.';
                        break;
                case 'reporting':
                        statusValue = 4;
                        text = 'No reporting data.';
                        break;
                 case 'complete':
                        statusValue = 5;
                        text = 'No complete data.';
                        break;
                default:
                        statusValue = -1;
                        text = 'Please add your project brief.';
                        break;
            }
            $scope.status = statusValue;
            $scope.showPrompt = text;
        };
        function switchStatus(statusValue) {
            var current;
            switch(statusValue) {
                case 0:
                    current = 'initial';
                    break;
                case 1:
                    current = 'proposal';
                    break;
                case 2:
                    current = 'approve';
                    break;
                case 3:
                    current = 'request';
                    break;
                case 4:
                    current = 'report';
                    break;
                case 5:
                    current = 'complete';
                    break;
                default:
                    current = 'all';
                    break;
            }
            $('.switch-btn').removeClass('active');
            $('.switch-btn.'+current).addClass('active');
        };
        function updateProjectListForClient() {
             var params = {};
             params.status = $scope.status;
             if($scope.keyword) {
                params.keyword = $scope.keyword;
             }
             params.userid = $scope.userid;
             params.clientID = $scope.clientID;
            $rootScope.$emit('updateProjectList',params);
        };
        $rootScope.$on('updateProjectClient',function(event, data) {
            if(data.clientID && data.client) {
                $scope.clientID = data.clientID;
                $scope.client = data.client;
                $scope.isDeactivated = data.isDeactivated;
            }
            $scope.status = data.status? parseInt(data.status, 10): -1;
            switchStatus($scope.status);
            showList();
            if(data.type === 'switchClient') {
                updateProjectListForClient();
            };
        });
	}]);
});
