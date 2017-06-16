define(['angular', 'app/home/module', 'angularUIRoute', 'app/requests/menu'], function (angular, app) {
	app.controller('menuCtrl', ['$scope', 'srvMenu', '$location', 'srvCookie', 'customAlert', '$rootScope', 'permissions',
        function ($scope, srv, $location, $srvCookie, $customAlert, $rootScope, $permissions) {
            //Get user info
            var userInfo = $srvCookie.get('userInfo'),
                curClient = $srvCookie.get('curClient');
            if(!userInfo) {
                logout();
            }
            //show menu list on the left
            $scope.showMenuList = $srvCookie.get('showMenuList')? $srvCookie.get('showMenuList'): 'active';
            //$scope.showClientList = $srvCookie.get('showClientList')? $srvCookie.get('showClientList'): '';
            //$permissions.setPermissions(userInfo.permission);
            $scope.userid = userInfo? userInfo.id: '';
            $scope.username = userInfo? userInfo.username: '';
           // $scope.role = userInfo? userInfo.role: '';
           // $scope.ownerStatus = $scope.role === 'Admin' || $scope.role === 'ServiceCenter'? 'show': '';
            $scope.client = curClient? curClient.client: '';
            $scope.clientID = curClient? curClient.clientID: '';
            if(!curClient) {
                $srvCookie.put('curClient',{client:$scope.client, clientID:$scope.clientID});
            }
            $scope.clientList = [
                {
                    id:1,
                    name:'Test1',
                    isDeactivated:0,
                    status:'parent',
                    children:[
                        {
                            id:1,
                            parentID:1,
                            name:'User1',
                            isDeactivated:0
                        },
                        {
                            id:1,
                            parentID:1,
                            name:'User2',
                            isDeactivated:0
                        },
                        {
                            id:1,
                            parentID:1,
                            name:'User3',
                            isDeactivated:0
                        }
                    ]
                },
                {
                    id:2,
                    name:'Test2',
                    isDeactivated:0,
                    status:'parent',
                    children:[
                        {
                            id:1,
                            parentID:2,
                            name:'User1',
                            isDeactivated:0
                        },
                        {
                            id:1,
                            parentID:2,
                            name:'User2',
                            isDeactivated:0
                        },
                        {
                            id:1,
                            parentID:2,
                            name:'User3',
                            isDeactivated:0
                        }
                    ]
                },
                {
                    id:1,
                    name:'Test3',
                    isDeactivated:0,
                    status:''
                },
                {
                    id:1,
                    name:'Test4',
                    isDeactivated:0,
                    status:''
                },
                {
                    id:1,
                    name:'Test5',
                    isDeactivated:0,
                    status:''
                }
            ];
            $scope.childClientList = [
                {
                    id:1,
                    parentID:1,
                    name:'User1',
                    isDeactivated:0
                },
                {
                    id:1,
                    parentID:1,
                    name:'User2',
                    isDeactivated:0
                },
                {
                    id:1,
                    parentID:1,
                    name:'User3',
                    isDeactivated:0
                }
            ];

            //Initialized data
            //initializeMenuList();

            // $scope.toggleMenuBlock = function() {
            //     if($scope.showMenuList === 'active') {
            //         $scope.showMenuList = 'close-menu';
            //         $scope.showClientList = 'hidden';
            //     } else {
            //         $scope.showMenuList = 'active';
            //         $scope.showClientList = '';
            //     }
            //     $srvCookie.put('showMenuList', $scope.showMenuList);
            //     $srvCookie.put('showClientList', $scope.showClientList);
            // };
            // type: first, second, second_btn
            $scope.toggleMenuItem = function($event, type, firstMenu) {
                var obj = angular.element($event.currentTarget),
                    getClass = obj.attr('class'), menuStatus = {},
                    objTitle = obj.html().replace(/ /g, '_');

                if(type === 'second_btn') {
                    objTitle = 'second_more';
                }
                removeAllActive();
                // not menu btn
                if(getClass && getClass.indexOf('active') > -1) {
                    menuStatus = toggleMenuStatus(obj, objTitle, 'remove', firstMenu);
                } else {
                    menuStatus = toggleMenuStatus(obj, objTitle, 'add', firstMenu);
                }
                $srvCookie.put(objTitle, menuStatus);
            };

            $scope.showChildClient = function($event, item) {
                var obj =  angular.element($event.currentTarget)
                   curLi = obj.parent('li').offset().top,
                   clientListTop = $('#menu-list').offset().top,
                    childClientTop = curLi - clientListTop;
                $('#two-level-client-list').css('top',childClientTop + 'px');
                $('#two-level-client-list').removeClass('hidden');
                $scope.childClientList =item.children;
                // getChildClientList(id);
            };
            $scope.closeChildClient = function() {
                 // $('#two-level-client-list').addClass('hidden');
                 $scope.childClientList = [];
            };
            $scope.switchClientStatus = function($event,type) {
                var obj =  angular.element($event.currentTarget),
                    clientID = obj.attr('data-id'),
                    clientName = obj.html();

                $scope.client = clientName;
                $scope.clientID = clientID;
                var params = {};
                params.client = clientName;
                params.clientID = clientID;
               
                $('#client-menu-item').find('li').removeClass('active');
                if(type === 'child') {
                   params.parentID = obj.attr('data-parent');
                   $('#client-menu-item').find('li p[data-id='+params.parentID+']').addClass('active');
                } else {
                    obj.parent('li').addClass('active');
                }
                $srvCookie.put('curClient',params);
            };
            /*$scope.switchClient = function($event) {
                var obj =  angular.element($event.currentTarget),
                    clientID = obj.attr('data-id'),
                    clientName = obj.html(),
                    isDeactivated = obj.attr('data-isdeactivated');
                //setDefaultClient(clientID);
                $scope.client = clientName;
                $scope.clientID = clientID;
                var params = {};
                params.client = clientName;
                params.clientID = clientID;
                params.isDeactivated = isDeactivated;
                params.status = -1;
                $srvCookie.put('curClient',params);
                params.type = 'switchClient';
                $rootScope.$emit('updateProjectClient',params);
                initializeMenuList();
            };*/
            $scope.logout = function() {
                logout();
            };
            $scope.goToHome = function() {
                removeAllActive();
                $rootScope.$emit('toggleEditBox', '');
                var pathway = $scope.pathway;
                $scope.pathway = pathway.splice(1,1);
                $state.go('home.project.list');
            };
            $scope.changePathway = function(firstMenu, secondMenu, curFile) {
                var pathway = {};
                pathway.firstMenu = firstMenu;
                pathway.secondMenu = secondMenu;
                pathway.curFile = curFile;
                updateChangePathway(pathway);
            };
            $scope.switchPage = function($event, curLink) {
                var obj = angular.element($event.currentTarget),
                    status = {'link': curLink, 'isActive': 'active'};
                $srvCookie.put('list_Item_Status', status);
                $srvCookie.put('second_more', '');

                $('.item-list .switch-menu-btn').removeClass('active');
                $('.menu-btn').removeClass('active');
                obj.addClass('active');

                var params = curLink.indexOf('?') > -1?getQueryObjectForUrl(curLink): {};
                if(params && curLink.indexOf('project/form') > -1) {
                    $rootScope.$emit('updateProjectFormTab',params);
                };
                if(params && curLink.indexOf('project/list') > -1) {
                    $rootScope.$emit('updateProjectClient',params);
                };
            };
            $rootScope.$on('updateMenu',function(event, data) {
                if(data.type === 'project') {
                    getRecentList('project');
                    removeAllActive();
                } else if(data.type === 'client') {
                    getClientList();
                } else if(data.type === 'other') {
                    initializeMenuList();
                } else if(data.type === 'templateOwner') {
                    getRecentList('tempalteOwner');
                }
            });
            $rootScope.$on('updatePathway',function(event,data) {
                var pathwayParam = {};
                if(data.type === 'project') {
                    pathwayParam.firstMenu = $scope.menuList[0].firstTitle;
                } else if(data.type === 'templatePulic') {
                    pathwayParam.firstMenu = $scope.menuList[1].firstTitle;
                    pathwayParam.secondMenu = $scope.menuList[1].firstList[0].title;
                } else if(data.type === 'templateOwner') {
                    pathwayParam.firstMenu = $scope.menuList[1].firstTitle;
                    pathwayParam.secondMenu = $scope.menuList[1].firstList[1].title;
                }
                pathwayParam.curFile = data.curFile? data.curFile: '';
                updateChangePathway(pathwayParam);
            });
            //Private function
            function logout() {
                $srvCookie.removeAll();
                $srvCookie.put('logout','1');
                var host = window.location.host;
                if(host.indexOf('172.16.129.246') > -1 || host.indexOf('52.168.145.0') > -1) {
                    window.location.href = srv.logout();
                } else {
                    $location.path('/login').search('');
                }
            };
            function updateChangePathway(params) {
                $scope.pathway = [];
                $scope.pathway[0] = params.firstMenu;

                if(params.secondMenu) {
                   $scope.pathway[1] = params.secondMenu;
                   if(params.curFile && params.curFile !== 'more') {
                        $scope.pathway[2] = params.curFile;
                    }
                }

                if(!params.secondMenu && params.curFile && params.curFile !== 'more') {
                    $scope.pathway[1] = params.curFile;
                }
                $srvCookie.put('pathway', $scope.pathway);
            };
            function toggleMenuStatus(obj, objTitle, type, firstMenu) {
                var getClass = obj.attr('class'), data = {},
                    parentObj = obj.parent(), parentStatus,
                    nextObj = obj.next(), status;

                    if(type === 'add') {
                        status = 'active';
                        parentStatus = 'active-menu-child';
                    } else {
                        status = '';
                        parentStatus = '';
                    }

                    obj.toggleClass('active');
                    data = {'curTitle': objTitle, 'curStatus': status};
                    // not menu btn
                    if (getClass.indexOf('menu-btn') < 0) {
                        if(nextObj.attr('class').indexOf('menu-item-child') > -1){
                           parentObj.toggleClass('active-menu-child');
                           data = {'curTitle': objTitle, 'curStatus': status, 'parentStatus': parentStatus};
                        } else {
                           parentObj.toggleClass('active');
                           data = {'curTitle': objTitle, 'curStatus': status, 'parentStatus': status};
                        }
                    } else {
                        data.firstMenu = firstMenu;
                    }

                return data;
            };
            function removeAllActive() {
                $('.item-list .switch-menu-btn').removeClass('active');
                $('.menu-btn').removeClass('active');
            };
            function getQueryObjectForUrl(url) {
                var r = url.substr(url.indexOf('?')).substr(1).split('&'),
                    list = '';
                if(r.length > 0) {
                    for(var i = 0; i < r.length; i++) {
                        var d = r[i].split('=');
                        if(i === 0) {
                            list += '"'+d[0]+'":"'+d[1]+'"';
                        } else {
                            list += ',"'+d[0]+'":"'+d[1]+'"';
                        }
                    }
                    list = '{'+list+'}';
                    list = JSON.parse(list);
                }

                return list;
            };
            function initializeMenuList() {
                //Default menu structure
                $scope.menuList =   [
                                        {
                                            //first level menu
                                            'firstTitle': 'Project Console',
                                            //show: displayed in the menu list. '': not displayed
                                            'status': getMenuPermission('projectConsole'),
                                            //active: open the first level menu. '': close the menu
                                            'openFirstStatus': 'active',
                                            //active: first title status when open the list, '': the status when close the list
                                            'firstTitleStatus': 'active',
                                            //first level menu list
                                            'firstList': [
                                                           {
                                                                //Second level menu
                                                                'title': '',
                                                                //show: displayed. '': not displayed
                                                                'status': 'show',
                                                                //active: open the second menu. '': close the menu
                                                                'openSecondStatus': '',
                                                                //list: second just list and no second level directory.  menu: second level directory, btn: it just button.
                                                                'type': 'list',
                                                                //list data
                                                                'list': [
                                                                            {
                                                                                // active: the current data is selected. more_btn: jump to list page. '':no selected
                                                                                'isActive': 'more_btn',
                                                                                'title': 'more',
                                                                                'link': '#/project/list'
                                                                            }
                                                                        ]
                                                           }
                                                       ]
                                        },
                                        {
                                            'firstTitle': 'User Template Library',
                                            'status': getMenuPermission('userTemplateLibrary'),
                                            'openFirstStatus': '',
                                            'firstTitleStatus': '',
                                            'firstList': [
                                                           {
                                                                'title': 'Public',
                                                                'status': 'show',
                                                                'openSecondStatus': '',
                                                                'type': 'menu',
                                                                'list': [
                                                                        {
                                                                            'isActive': 'more_btn',
                                                                            'title': 'more',
                                                                            'link': '#/template_list?type=public'
                                                                        }
                                                                      ]
                                                           },
                                                           {
                                                                'title': 'Owner',
                                                                'status': $scope.ownerStatus,
                                                                'openSecondStatus': '',
                                                                'type': 'menu',
                                                                'list': [
                                                                        {
                                                                            'isActive': 'more_btn',
                                                                            'title': 'more',
                                                                            'link': '#/template_list?type=owner'
                                                                        }
                                                                      ]
                                                           }
                                                       ]
                                        },
                                        {
                                            'firstTitle': 'Configuration Console',
                                            'status': getMenuPermission('configurationConsole'),
                                            'openFirstStatus': '',
                                            'firstTitleStatus': '',
                                            'firstList': [
                                                           {
                                                                'title':'',
                                                                'status': 'show',
                                                                'openSecondStatus': '',
                                                                'type': 'list',
                                                                'list': [
                                                                        {
                                                                            'isActive': '',
                                                                            'title': 'Users Management',
                                                                            'link': '#/user'
                                                                        },
                                                                        {
                                                                            'isActive': '',
                                                                            'title': 'Clients Management',
                                                                            'link': '#/client'
                                                                        }
                                                                      ]
                                                           }
                                                       ]
                                        }
                                    ];

                $scope.pathway = [ $scope.menuList[0].firstTitle ];
                //Get list
                getClientList();
                getRecentList('project');
                getRecentList('tempaltePublic');
                if($scope.ownerStatus === 'show') {
                    getRecentList('tempalteOwner');
                }
                //update pathway
                var pathway = $srvCookie.get('pathway');
                if(pathway) {
                    $scope.pathway = pathway;
                }

                //initialize menu status
                var arr =  $scope.menuList;
                for(var i = 0; i < arr.length; i++) {
                    var title = arr[i].firstTitle.replace(/ /g, '_'),
                        curTitleStatus = $srvCookie.get(title),
                        firstList = arr[i].firstList,
                        moreStatus = $srvCookie.get('second_more');
                    if(moreStatus && moreStatus.firstMenu === arr[i].firstTitle) {
                        arr[i].firstList[firstList.length-1].openSecondStatus = moreStatus.curStatus;
                    }

                    arr[i].openFirstStatus = curTitleStatus.parentStatus;
                    arr[i].firstTitleStatus = curTitleStatus.curStatus;

                    for(var k = 0; k < firstList.length; k++) {
                        if( firstList[k].type === 'menu' ) {
                            var childTitle = firstList[k].title.replace(/ /g, '_'),
                                childTitleStatus = $srvCookie.get(childTitle);
                            firstList[k].openSecondStatus = childTitleStatus.curStatus;
                        }
                    }

                    arr[i].firstList = firstList;
                }

                $scope.menuList = arr;
            };
            function getClientList() {
                var params = {};
                params.userid = $scope.userid;
                srv.getClientList(params).then(function(result){
                    if(result.code === 'ACK') {
                        $scope.clientList = result.data;
                    } else {
                      // $customAlert.warning(result.message);
                    }
                },function(result){
                    // $customAlert.alert(result.message + ' Please contact the admin.');
                });
            };
            function getChildClientList(parentid) {
                var params = {};
                params.userid = $scope.userid;
                params.parentid = parentid;
                $('.child-menu').removeClass('hidden');
                // srv.getChildClient(params).then(function(result){
                //     if(result.code === 'ACK') {
                //         $scope.childClientList = result.data.childClient;
                //         $('.child-menu').removeClass('hidden');
                //     } else {
                //         $customAlert.warning(result.message);
                //     }
                // },function(result){
                //     $customAlert.alert(result.message + ' Please contact the admin.');
                // });
            };
            // function setDefaultClient(id) {
            //     var params = {};
            //     params.userid = $scope.userid;
            //     params.clientid = id;
            //     srv.setDefaultClient(params).then(function(result){
            //         if(result.code === 'ACK') {
            //             return true;
            //         } else {
            //          //   $customAlert.warning(result.message);
            //         }
            //     },function(result){
            //         // $customAlert.alert(result.message + ' Please contact the admin.');
            //     });
            // };
            function getInterfaceForType(type) {
                var params = {}, promise;
                    params.userid = $scope.userid;
                    params.clientId = $scope.clientID;

                if(type === 'tempaltePublic') {
                    params.type = 'public';
                } else if(type === 'tempalteOwner') {
                    params.type = 'owner';
                };
                if(type === 'project') {
                    promise = srv.getRecentProjectList(params);
                }  else if(type === 'tempaltePublic' || type === 'tempalteOwner') {
                    promise = srv.getRecentUserLibrary(params);
                } else {
                    promise = '';
                }
                return promise;
            };
            function assemblyRecentList(data,type) {
                var recentList = [], itemStatus, moreStatus, moreLink, templateStatus;
                if(type === 'tempaltePublic') {
                    templateStatus = 'public';
                } else if(type === 'tempalteOwner') {
                    templateStatus = 'owner';
                };
                if($srvCookie.get('list_Item_Status')) {
                    itemStatus = $srvCookie.get('list_Item_Status');
                } else {
                    itemStatus = {'link':'', 'isActive':''};
                }
                for(var i = 0; i < data.length; i++) {
                    recentList[i] = {};
                    recentList[i].isActive = '';
                    recentList[i].title = data[i].name + ' - ' + data[i].client;
                    if(type === 'project') {
                        recentList[i].link = 'home.project.form.brief({id:' + data[i].id +
                                             ',status:' + data[i].status + '})';
                        recentList[i].isProject = true;
                        moreLink = '#/project/list?status=-1';
                    } else if(type === 'tempaltePublic' || type === 'tempalteOwner') {
                        recentList[i].link = '#/template_form?id='+data[i].id;
                        moreLink = '#/template_list?type='+templateStatus;
                    }
                    if(itemStatus.link === recentList[i].link) {
                        recentList[i].isActive = itemStatus.isActive;
                    }
                }
                if(moreLink) {
                    if(itemStatus.link === moreLink) {
                        moreStatus = 'active';
                    } else {
                        moreStatus = '';
                    }
                    recentList.push({
                        isActive: 'more_btn',
                        title: 'more',
                        pathName: '',
                        link: moreLink,
                        moreStatus: moreStatus
                    });
                }
                return recentList;
            }
           // get project list
            function getRecentList(type) {
                getInterfaceForType(type).then(function(result){
                    if(result.code === 'NACK'){
                       // $customAlert.warning(result.message);
                    }
                    if(result.code === 'ACK' && result.data && result.data.length > 0 ) {
                        var list = result.data, recentList;
                        recentList = assemblyRecentList(list,type);
                        //get item status
                        if(type === 'project') {
                            $scope.menuList[0].firstList[0].list = recentList;
                        } else if(type === 'tempaltePublic') {
                            $scope.menuList[1].firstList[0].list = recentList;
                        } else if(type === 'tempalteOwner') {
                            $scope.menuList[1].firstList[1].list = recentList;
                        }
                    }
                },function(result){
                   // $customAlert.alert(result.message + ' Please contact the admin.');
                });
            };
            function getMenuPermission(menuItem) {
                var status = $permissions.hasPermission('menu-'+menuItem);
                if(status) {
                    return 'show';
                } else {
                    return '';
                };
            };
	}]);
});
