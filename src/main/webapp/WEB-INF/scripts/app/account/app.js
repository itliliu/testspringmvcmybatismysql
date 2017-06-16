/* Account - app.js */
define(['angular', 'angularUIRoute', 'app/requests/module'], function (angular) {
	//define module we.account
    return angular.module('we.account', ['ui.router', 'we.requests']);
});
