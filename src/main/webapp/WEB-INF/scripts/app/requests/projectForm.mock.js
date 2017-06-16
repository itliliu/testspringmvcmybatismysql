define(['angular', 'app/help/module', 'underscore'], function (angular, module) {
	module.factory('srvProjectFormMock', ['$q', '$timeout', function ($q, $timeout) {
		var getProjectList = function (formData) {
			var deferred = $q.defer();

			$timeout(function () {
				if(formData.uid){
					var data = {
									'code':'ACK',
									'data':{
												'pageTotal':1,
												'requestForm':[{
													'id':1,
													'parentId':1,
													'status':0,
													'eventName':'Event Name1',
													'overview': 'Event Description',
													'startDate':'2017-05-10'
												},{
													'id':2,
													'parentId':2,
													'status':1,
													'eventName':'Event Name2',
													'overview': 'Event Description',
													'startDate':'2017-05-10'
												},{
													'id':3,
													'parentId':3,
													'status':2,
													'eventName':'Event Name3',
													'overview': 'Event Description',
													'startDate':'2017-05-10'
												},{
													'id':4,
													'parentId':4,
													'status':3,
													'eventName':'Event Name4',
													'overview': 'Event Description',
													'startDate':'2017-05-10'
												},{
													'id':5,
													'parentId':5,
													'status':4,
													'eventName':'Event Name5',
													'overview': 'Event Description',
													'startDate':'2017-05-10'
												},{
													'id':6,
													'parentId':6,
													'status':5,
													'eventName':'Event Name6',
													'overview': 'Event Description',
													'startDate':'2017-05-10'
												}]
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
			getProjectList: getProjectList
		};
	}]);
});
