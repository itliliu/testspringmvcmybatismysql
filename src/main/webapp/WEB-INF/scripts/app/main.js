require(['require', 'angular', 'gs',
		'angularUIRoute',
        'angularAnimate',
        'ngInfiniteScroll',
        'app/help/srvCookie',
        'app/help/customAlert',
        'app/help/permissions',
        'app/help/authHttp',
		'app/account/ctrls',
        'app/home/ctrls',
        'app/directives/directives',
        'app/components/components',
        'angular-underscore',
        'mock.data'
], function (require, angular, gs) {
    var app = angular.module('we', ['ngAnimate',
                                    'infinite-scroll',
                                    'we.help.srvs',
                                    'we.account',
                                    'we.home',
                                    'we.directives',
                                    'we.components',
                                    //add underscore functions to filters and rename to _xxx
                                    'angular-underscore/filters']);

    if (gs.device.isPhone) {
        require(['css!style/phone/account'], startApp);
    } else {
        require(['css!style/web/account',
                 'css!style/web/menu',
                 'css!style/web/common',
                 'css!style/web/briefForm',
                 'css!style/web/requestForm',
                 'css!style/web/templateList',
                 'css!style/web/project',
                 'css!style/web/configuration'], startApp);
    }

    function startApp() {
        app.run(['$rootScope', '$state', '$stateParams', '$location', 'permissions',
        function ($rootScope, $state, $stateParams, $location, permissions) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.accountStatus = {
                    'isLogin':false,
                    'userID':''
                };
                $rootScope.$on('$stateChangeStart', function(scope, next) {
                    var permission = next.permission;
                    if( permission && typeof permission === 'string' && !permissions.hasPermission(permission)){
                        $location.path('/unauthorized');
                    }
                });
            }]
        );

        angular.bootstrap(document, ['we'], { debugInfoEnabled: true });
    }
    return app;
});
