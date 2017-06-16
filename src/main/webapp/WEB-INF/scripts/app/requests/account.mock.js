define(['angular', 'app/help/module', 'underscore'], function (angular, module) {
	module.factory('srvAccount', ['$q', '$timeout', function ($q, $timeout) {
		var login = function (formData) {
			var deferred = $q.defer();

			$timeout(function () {
				if(formData.username){
					var data = {
								'code': 'ACK',
							    'data': {
								        'id': 3,
					                    'username': 'admin',
					                    'clientname': 'Microsoft' ,
					                    'defaultclientid': 3,
					                    'email':'admin@admin.com',
					                    'insertdate':'1491830172000',
					                    'lastmodifydate':'1491830172000',
					                    'password':null,
					                    'phonenumber':'18758261611',
					                    'roleid':1,
					                    'role':'admin',
					                    'status':1
							    },
							    'message':'succeed'
							};
					deferred.resolve(data);
				}else {
					deferred.reject(new Error());
				}
			}, 100);

			return deferred.promise;
		};

		return {
			login: login
		};
	}]);
});
