/**  Home - app.js */
define(['angular', 'angularUIRoute', 'app/requests/module',
	'uiBootstrap', 'ui.select', 'angularSanitize'], function (angular) {
		//define module we.home
    return angular.module('we.home', ['ui.router', 'we.requests', 'ui.bootstrap',
    								  'ui.select', 'ngSanitize']);
});
