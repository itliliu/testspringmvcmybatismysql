/**  Account - router.web.js */
define(['angular', 'gs', 'app/account/app'], function (angular, gs, app) {
	if (!gs.device.isWeb) {
		return;
	}
	app.config(['$stateProvider',/* '$urlRouterProvider',*/
			function ($stateProvider/*, $urlRouterProvider*/) {
				$stateProvider.state('account', {
					abstract: true,
					url: '',
					views: {
						'main@': {
						    template: '<div ui-view></div>'
						}
					}
				}).state('account.login', {
				    url: '/login',
					templateUrl: 'tmpls/account/login.html',
					controller: 'accountLoginCtrl',
					permission: false
				}).state('account.unauthorized', {
				    url: '/unauthorized',
				    templateUrl: 'tmpls/account/unauthorized.html',
				    controller: 'unauthorizedCtrl',
				    permission: false
				});
			}
	]);
});
