define(['angular', 'gs', 'app/home/app', 'angularUIRoute'], function (angular, gs, app) {
	if (!gs.device.isWeb) {
		return;
	}
	app.config(['$stateProvider', '$urlRouterProvider',
			function ($stateProvider, $urlRouterProvider) {
				$stateProvider.state('home', {
					abstract: true,
					url: '',
					views: {
						'main@': {
						    templateUrl: 'tmpls/home/base.html',
						    controller: 'menuCtrl'
						}
					}
				}).state('home.project', {
				    url: '/project',
					abstract: true,
				    templateUrl: 'tmpls/home/project.html',
				    controller: 'projectCtrl'
				}).state('home.project.list', {
				    url: '/list',
				    templateUrl: 'tmpls/home/projectList.html'
				}).state('home.project.newBrief', {
                    url: '/newBrief',
                    templateUrl: 'tmpls/home/creatBrief.html',
					controller:'newBriefCtrl'
                }).state('home.project.briefStatus', {
                    url: '/briefStatus',
                    templateUrl: 'tmpls/home/checkBrief.html',
					controller: 'projectBriefCtrl'
                }).state('home.project.modifyBrief', {
                    url: '/modifyBrief',
                    templateUrl: 'tmpls/home/modifyBrief.html',
					controller: 'projectmBriefCtrl'
                }).state('home.preview', {
				    url: '/preview',
				    templateUrl: 'tmpls/home/preview.html',
				    controller: 'previewCtrl',
				    permission: false
				});
				//default route
				$urlRouterProvider.otherwise('project/list');
			}
	]);
});
