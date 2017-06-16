var require = { //this sets it as global so it won't be loaded asynch
    baseUrl: 'scripts',
    waitSeconds: 60,
    //urlArgs: 'bust=@version@',
    map: {
        '*': {
            'css': 'lib/require-plugins/css' // or whatever the path to require-css is
        }
    },
    paths:
    {
        'style': '../css',

        'jquery': 'lib/jquery',
        'bootStrap': 'lib/bootstrap.min',
        'dateTimePicker': 'lib/bootstrap-datetimepicker',
        'jqueryUi':'lib/jquery-ui.min',
        'jqueryForm':'lib/jquery.form',

        'underscore': 'lib/underscore',
        'angular-underscore': 'lib/angular-underscore',
        'string.helper': 'app/help/string.helper',
        'json2': 'lib/json2',
        'chart': "lib/chart/Chart",

        'angular': 'lib/angular/angular',
        'angularTouch': 'lib/angular/angular-touch',
        'angularAnimate': 'lib/angular/angular-animate',
        'angularCookies': 'lib/angular/angular-cookies',
        'angularRoute': 'lib/angular/angular-route',
        'angularUIRoute': 'lib/angular/angular-ui-router',
        'angularLocalStorage': 'lib/angular/angular-local-storage',
        'angularSanitize': 'lib/angular/angular-sanitize',
        'angularChart': 'lib/angular/angular-chart',
        

        'urlConfig': 'app/config/urlConfig',
        'dataOptions': 'app/requests/dataOptions',
        'mobiledetect': 'lib/mobile-detect',
        'gs': 'app/config/globalSetting',
       
        'text' : 'lib/require-plugins/text',
        'mock': 'lib/mock/mock',
        'mock.angular': 'lib/mock/mock.angular',
        'mock.data': 'app/mock/mockData',

        'ngDialog': 'lib/ngDialog/ngDialog',
        'ui.select' : 'lib/ui-select',
        'uiBootstrap': 'lib/ui-bootstrap-tpls-2.5.0.min',
        
        'ngInfiniteScroll': 'lib/ng-infinite-scroll.min',
        'string.helper': 'app/help/string.helper',
        'sortable':'lib/sortable.min',
        'moment':'lib/moment.min',

        toDataUrl:"lib/todataurl"

    },
    shim:
    {
        'jquery':
        {
            exports: '$'
        },
        'jqueryUi':{
            deps:['jquery']
        },
        'jqueryForm':{
            deps:['jquery']
        },
        'mock': {
            exports: 'Mock'
        },
        'mock.angular' : {
            deps: ['mock', 'angular']
        },
        'underscore': {
            exports: '_'
        },
        'angular-underscore': {
            deps: ['angular', 'underscore']
        },
        'json2': {
            exports: 'JSON'
        },
        'angular': {
        	deps: ['bootStrap'],
            exports: 'angular'
        },
        'angularTouch': {
            deps: ['angular'],
            exports: 'angularTouch'
        },
       
        'uiBootstrap' : {
            deps: ['angular'],
        },
        'angularAnimate': {
            deps: ['angular'],
            exports: 'angularAnimate'
        },
        'angularCookies': {
            deps: ['angular'],
            exports: 'angularCookies'
        },
        'angularSanitize': {
            deps: ['angular'],
          
        },
        'angularRoute': {
        	deps: ['angular'],
            exports: 'angularRoute'
        },
        'angularUIRoute': {
        	deps: ['angular', 'angularRoute'],
            exports: 'angularUIRoute'
        },
        'ngDialog': {
            deps: ['angular'],
            exports: 'ngDialog'
        },
        'ui.select': {
            deps: ['angular']
        },
        'angularChart':{
            deps:['chart','angular'],
            exports: 'angularChart'
        },
        'bootStrap': {
            deps: ['jquery'],
            exports: 'bootStrap'
        },
        'dateTimePicker': {
            deps: ['jquery','bootStrap'],
            exports: 'dateTimePicker'
        },
        'ngInfiniteScroll': {
            deps: ['jquery','angular'],
            exports: 'ngInfiniteScroll'
        },
        'sortable':{
            deps:['jquery','jqueryUi','angular']
        }
    }
};