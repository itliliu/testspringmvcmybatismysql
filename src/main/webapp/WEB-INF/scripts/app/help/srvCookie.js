/**  Help - srvCookie.js */
define(['angular', 'app/help/module', 'angularCookies'], function (angular, module) {
    module.factory('srvCookie', ['$cookieStore', function ($cookieStore) {
    	var get = function(key) {
            if($cookieStore.get(key)){
                if(key === 'permission') {
                    return $cookieStore.get(key);
                } else {
                    return JSON.parse($cookieStore.get(key));
                }
            } else {
                if(key === 'userInfo') {
                    window.location.href = 'http://' + window.location.host + '/index.html#/login';
                } else {
                    return '';
                }
            }
    	};
        var put = function(key, value) {
            value = JSON.stringify(value);
            $cookieStore.put(key, value);
        };
        var removeAll = function() {
            var allData = $cookieStore.getAll();
            angular.forEach(allData,function(value, key){
               $cookieStore.remove(key);
            });
        };
        var remove = function(key) {
            $cookieStore.remove(key);
        };
    	return {
    		get: get,
            put: put,
            removeAll: removeAll,
            remove:remove
    	};
    }]);
});
