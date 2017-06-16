define(['angular', 'app/help/module', 'underscore'], function (angular, module) {
	module.factory('srvTemplate', ['$q', '$timeout', function ($q, $timeout) {
		var getTemplate = function (formData) {
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
		return {
			getTemplate: getTemplate
		};
	}]);
});
