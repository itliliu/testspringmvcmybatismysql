define([], function() {
	return {
		'user/get.*' : {
			isOpen : false,
			data :{
				'dataList|1-10' : [{
					'UserID|+1' : 100,
					'UserName'   : '@name()',
					'CreateData' : '@date()',
					'Email'      : '@email()',
					'ClientIDs'  : '@pick([1,2,3,4,5,6], 1, 3)',
					'Status|1'     : true,
					'RoleID|1'     : [1,2,3,4]
				}],
				'currentPage|1-10' : 5,
				'totalItems': 55
			}
		},
		'user/delete/\\d+' : {
			'test' : true
		},
		'user/create' : {
			'test' : true
		},
		'user/save/\\d+' : {
			'test' : true
		},
		'user/status/\\d+' : {
			'test' : true
		}
	};
});
