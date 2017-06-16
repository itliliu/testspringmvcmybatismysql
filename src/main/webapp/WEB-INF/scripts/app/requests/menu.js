define(['angular', 'app/help/module', 'underscore','app/config/urlConfig','app/help/httpService'],
function (angular, module) {
    module.factory('srvMenu', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var getRecentProjectList = function(params) {
        	var url = urlConfig.menu.recentProjectList;
            return $services.get(url,params);
        };
        var getRecentUserLibrary = function(params) {
            var url = urlConfig.menu.recentUserLibrary;
            return $services.get(url,params);
        };
        var getClientList = function(params) {
            var url = urlConfig.menu.getClientList;
            return $services.get(url,params);
        };
        var getChildClient = function(params) {
            var url = urlConfig.menu.getChildClient;
            return $services.get(url,params);
        };
        var setDefaultClient = function(params) {
            var url = urlConfig.menu.setDefaultClient;
            return $services.put(url,params);
        };
        var logout = function() {
            var url = urlConfig.account.logout;
            return url;
        };
        return {
            getClientList: getClientList,
            getChildClient: getChildClient,
            setDefaultClient: setDefaultClient,
            getRecentProjectList: getRecentProjectList,
            getRecentUserLibrary: getRecentUserLibrary,
            logout: logout
        };
    }]);
});
