define(['angular', 'app/help/module', 'underscore'], function (angular, module) {
	module.factory('srvMenu', ['$q', '$timeout', function ($q, $timeout) {
		var getRecentList = function (formData) {
			var deferred = $q.defer();

			$timeout(function () {
				if(formData.userName){
					deferred.resolve('ok');
				}else {
					deferred.reject(new Error());
				}
			}, 100);

			return deferred.promise;
		};
		var getClientList = function (formData) {
			var deferred = $q.defer();

			$timeout(function () {
				if(formData.uid){
					var data = {
									'code':'ACK',
									'data': [
				                                {
				                                    'id':1,
				                                    'name':'Apple',
				                                    'default':0,
				                                    'status': ''
				                                },
				                                {
				                                    'id':2,
				                                    'name':'Commercial Vehicle Group',
				                                    'default':0,
				                                    'status': 'parent'
				                                },
				                                {
				                                    'id':3,
				                                    'name':'Mircosoft',
				                                    'default':1,
				                                    'status': 'parent'
				                                },
				                                {
				                                    'id':11,
				                                    'name':'Word1',
				                                    'default':0,
				                                    'status': ''
				                                }
				                            ],
				                    'message': 'succeed'
								};
					deferred.resolve(data);
				}else {
					deferred.reject(new Error());
				}
			}, 100);

			return deferred.promise;
		};
		var getChildClient = function(formData) {
			var deferred = $q.defer();

			$timeout(function () {
				if(formData.uid){
					var data = {
									'code':'ACK',
									'data': [
				                                {
	                                    			'id':4,
				                                    'name':'Child service1',
				                                    'default':0
	                                    		},{
	                                    			'id':5,
				                                    'name':'Child service2',
				                                    'default':0
	                                    		},{
	                                    			'id':6,
				                                    'name':'Child service3',
				                                    'default':0
	                                    		}
	                                    	],
				                    'message': 'succeed'
								};
					deferred.resolve(data);
				}else {
					deferred.reject(new Error());
				}
			}, 100);

			return deferred.promise;
		};
		return {
			getRecentList: getRecentList,
			getClientList: getClientList,
			getChildClient: getChildClient
		};
	}]);
});
