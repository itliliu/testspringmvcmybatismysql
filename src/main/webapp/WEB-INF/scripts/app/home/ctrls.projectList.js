define(['angular', 'app/home/module', 'bootStrap', 'angularUIRoute', 'app/requests/project'],
function (angular, app) {
	app.controller('projectListCtrl', ['$scope', 'srvProject', '$location', '$state',
        'customAlert', '$rootScope', 'srvCookie',
    function ($scope, srv, $location, $state, $customAlert, $rootScope, $srvCookie) {
        var userInfo = $srvCookie.get('userInfo'),
            curClient = $srvCookie.get('curClient');
        $scope.userid = userInfo? userInfo.id: '';
        $scope.role = userInfo? userInfo.role: '';
        $scope.client = curClient? curClient.client: userInfo.clientName;
        $scope.clientID = curClient? curClient.clientID: userInfo.defaultClientID;

        var pageStatus = $location.search().status,
            pageIndex = $location.search().page;

        $scope.pageIndex = pageIndex > 0? parseInt(pageIndex, 10): 1;
        $scope.pageStatus = pageStatus? parseInt(pageStatus, 10):-1;

        $scope.pageSize = 10;
        $scope.projectList = [];
        $scope.keyword = '';

        showProjectList();
        $rootScope.$emit('updatePathway',{type:'project', curFile: ''});
        $scope.pageTotal = 1;
        $scope.editBrief = function(status,briefid,parentID) {
            var params = {}, briefParentID = parentID? parentID: briefid;
                params.id = briefid;
                params.status = status;
                params.pageStatus = $scope.pageStatus;
            updateProjectOperatorDate(briefParentID);
            $state.go('home.project.form.brief',params);
        };
        $scope.prevStatus = '';
        $scope.nextStatus = '';
        $scope.prevOrNextPage = function(type) {
            var total = $scope.pageTotal,
                curPage = $scope.pageIndex;

            if(type === 'prev') {
                if(curPage > 1){
                    $scope.pageIndex = curPage - 1;
                } else {
                    $scope.pageIndex = 1;
                }
            } else if(type === 'next') {
                if(curPage < total) {
                    $scope.pageIndex = curPage + 1;
                } else {
                    $scope.pageIndex = total;
                }
            }
            getListForPage();
        };
        $scope.goToPage = function() {
            var total = $scope.pageTotal,
                curPage = $scope.pageIndex;

            if(curPage < 1) {
                $scope.pageIndex = 1;
            } else if(curPage > total) {
                $scope.pageIndex = total;
            }
            if(total > 1) {
                getListForPage();
            };
        };
        function showProjectList() {
            var params = {};
            params.userid = $scope.userid;
            params.clientID = $scope.clientID;
            params.status = $scope.pageStatus;
            params.pageIndex = $scope.pageIndex;
            params.pageSize = $scope.pageSize;
            params.keyword = $scope.keyword;

            srv.getProjectList(params).then(function(result){
                if(result.code === 'ACK') {
                    var data = result.data,
                        list = data.projectConsole;
                        $scope.pageTotal = data.pageNumber;
                    switchPageBtnStatus();
                    if(list && list.length > 0 ) {
                        $scope.showList = true;
                        $scope.projectList = settingList(list);
                    } else {
                        $scope.projectList =  [];
                        $scope.showList = false;
                        var type = $('.switch-btn.active').html();
                        $scope.switchStatusText(type);
                    }
                } else {
                    $customAlert.warning(result.message);
                }
            },function(result){
                $customAlert.alert(result.message + ' Please contact the admin.');
            });
        };
        function getClassNameForStatus(status) {
            var className;
            switch(status) {
                    case 0:
                        className = 'initial';
                        break;
                    case 1:
                        className = 'proposal';
                        break;
                    case 2:
                        className = 'approve';
                        break;
                    case 3:
                        className = 'request';
                        break;
                    case 4:
                        className = 'report';
                        break;
                    case 5:
                        className = 'complete';
                        break;
                    default:
                        className = 'initial';
                        break;
            }
            return className;
        };
        function isEditPermission(status) {
            var editStatus;
            switch(status) {
                    case 0:
                    case 2:
                    case 4:
                        if($scope.role === 'AccountTeam') {
                            editStatus = true;
                        } else {
                            editStatus = false;
                        }
                        break;
                    case 1:
                        if($scope.role === 'Strategist') {
                            editStatus = true;
                        } else {
                            editStatus = false;
                        }
                        break;
                    case 3:
                        if($scope.role === 'ServiceCenter') {
                            editStatus = true;
                        } else {
                            editStatus = false;
                        }
                        break;
                    case 5:
                    default:
                        editStatus = false;
                        break;
            }
            if($scope.role === 'Admin' && status !== 5) {
                editStatus = true;
            }
            return editStatus;
        };
        function settingList(list) {
	        for(var i = 0; i < list.length; i++) {
	            list[i].className = getClassNameForStatus(list[i].status);
                list[i].btnStatus = true;
                list[i].btnTitle = isEditPermission(list[i].status)? 'Edit':'View';
                if(list[i].userid !== $scope.userid && $scope.role === 'AccountTeam') {
                    list[i].btnTitle = 'View';
                }
	        }
            return list;
        };
        function getListForPage() {
            if($scope.pageIndex > 0 && $scope.pageIndex <= $scope.pageTotal) {
                showProjectList();
                switchPageBtnStatus();
            };
        };
        function switchPageBtnStatus() {
            if ($scope.pageTotal === 1) {
                $scope.nextStatus = 'disabled';
                $scope.prevStatus = 'disabled';
            } else if($scope.pageIndex === $scope.pageTotal) {
                $scope.prevStatus = '';
                $scope.nextStatus = 'disabled';
            } else if ($scope.pageIndex === 1) {
                $scope.prevStatus = 'disabled';
                $scope.nextStatus = '';
            } else  {
                $scope.prevStatus = '';
                $scope.nextStatus = '';
            };
        };
        function updateProjectOperatorDate(parentID) {
            var params = {};
            params.parentID = parentID;
            params.userid = $scope.userid;
            srv.updateProjectOperatorDate(params).then(function(result) {
                if(result.code === 'ACK') {
                    $rootScope.$emit('updateMenu',{type:'project'});
                } else {
                    $customAlert.warning(result.message);
                }
            },function(result){
                $customAlert.alert(result.message + ' Please contact the admin.');
            });
        };
        $rootScope.$on('updateProjectList',function(event, data) {
            if(data) {
                $scope.pageStatus = data.status;
                $scope.keyword = data.keyword;
                $scope.clientID = data.clientID;
            }
            $scope.pageIndex = 1;
            showProjectList();
        });
	}]);
});
