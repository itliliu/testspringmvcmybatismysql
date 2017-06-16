/**  Help - httpServices.js */
define(['angular','app/help/module'],function(angular,module){
	module.factory('services',['authHttp','$q',function($http,$q){

		//get
        var get = function(url,params){
            var deferred = $q.defer();
            var promise = deferred.promise;
             $http.get(url,{params:params}).success(function(data){
                if(data){
                    deferred.resolve(data);
                    return true;
                }
                deferred.reject(new Error());
            }).error(function(error){
                deferred.reject(error);
            });
            return promise;
        };

        //post create
        var post = function(url,params){
            var deferred = $q.defer();
            var promise = deferred.promise;
             $http.post(url,params).success(function(data, status, headers, config){
                var host = window.location.host;
                if((host.indexOf('172.16.129.246') > -1 || host.indexOf('52.168.145.0') > -1)
                 && headers('Location')
                 && config.url.indexOf('login.json') > -1) {
                   window.location.href = headers('Location');
                }
                if(data){
                    deferred.resolve(data);
                    return true;
                }
                deferred.reject(new Error());
            }).error(function(error){
                deferred.reject(error);
            });
            return promise;
        };

        //put update
        var put = function(url,params){
            var deferred = $q.defer();
            var promise = deferred.promise;
             $http.put(url,params).success(function(data){
                if(data){
                    deferred.resolve(data);
                    return true;
                }
                deferred.reject(new Error());
            }).error(function(error){
                deferred.reject(error);
            });
            return promise;
        };
        //delete
        var deletef = function(url, params){
            var deferred = $q.defer();
            var promise = deferred.promise;
             $http.delete(url,params).success(function(data){
                if(data){
                    deferred.resolve(data);
                    return true;
                }
                deferred.reject(new Error());
            }).error(function(error){
                deferred.reject(error);
            });
            return promise;
        };
        return {
    		get   : get,
    		post  : post,
            put   : put,
            delete: deletef
    	};
	}]);
});
