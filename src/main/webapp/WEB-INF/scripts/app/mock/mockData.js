require(['underscore', 'mock', 'mock.angular', 'app/mock/allInOne'], function(_, Mock, ma, mockData) {

	var baseUrl = '.*/WEData/';
	_(mockData).each(function(value , key) {
		var result = {
			'code':'ACK',
			'message':'from mockup',
			'data' : value
		};
		if(angular.isDefined(value.isOpen)) {
			if(value.isOpen) {
				result.data = value.data;
			} else {
				return false;
			}
		}
		Mock.mock(baseUrl + key, result);
	});
});
