/**  Help - auhHttp.js */
define(['angular', 'app/help/module', 'underscore'], function (angular, module) {
    module.factory('authHttp', ['$http', '$q', '$location',
    function ($http, $q, $location) {
        var authHttp = {};

        function extendConfig(config) {
            return angular.extend({}, config, { /*withCredentials: true,*/ useXDomain: true });
        }

        function warpPromise(setting) {
            var deferred = $q.defer();
            var promise = deferred.promise;
            waitToProcess(deferred, setting);

            promise.success = function (fn) {
                promise.then(function (response) {
                    fn(response.data, response.status, response.headers, response.config);
                });
                return promise;
            };

            promise.error = function (fn) {
                promise.then(null, function (response) {
                    fn(response.data, response.status, response.headers, response.config);
                });
                return promise;
            };

            return promise;
        }

        function waitToProcess(deferred, setting) {
            var p;
            if (_.indexOf(['post', 'put', 'patch'], setting.method) !== -1) {
                p = $http[setting.method](setting.url, setting.data, setting.config);
            } else {
                p = $http[setting.method](setting.url, setting.config);
            }
            p.then(function (data) {
                deferred.resolve(data);
            }, function (error) {
                if (error.status === 401 || error.status === 403) {
                    $location.path('/unauthorized').search({status:error.status});
                    $rootScope.accountStatus.isLogin = false;
                    $rootScope.accountStatus.userID = '';
                }
                deferred.reject(error);
            });
        }

        angular.forEach(['get', 'delete', 'head', 'jsonp'], function (method) {
            authHttp[method] = function (url, config) {
                config = extendConfig(config);
                return warpPromise({method: method, url: url, config: config});
            };
        });
        angular.forEach(['post', 'put', 'patch'], function (method) {
            authHttp[method] = function (url, data, config) {
                config = extendConfig(config);
                return warpPromise({method: method, url: url, config: config, data: data });
            };
        });

        return {
            get: authHttp['get'],
            delete: authHttp['delete'],
            head: authHttp['head'],
            jsonp: authHttp['jsonp'],
            post: authHttp['post'],
            put: authHttp['put'],
            patch: authHttp['patch']
        };
    }]);
});
