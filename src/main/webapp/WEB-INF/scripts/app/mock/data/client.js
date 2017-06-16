define([], function() {
	return {
		'client/get.*' : {
			isOpen : false,
			data :[
				{
					'ClientID' : 1,
					'ClientName' : '@name()',
					'CreateData' : '@date()',
					'ParentID'   : 0,
					'Children|1-5' :[
						{
							'ClientID|+1' : 10,
							'ParentID'    : 1,
							'ClientName' : '@name()',
							'CreateData' : '@date()'
						}
					]
				},
				{
					'ClientID' : 2,
					'ClientName' : '@name()',
					'CreateData' : '@date()',
					'ParentID'   : 0,
					'Children|1-5' :[
						{
							'ClientID|+1' : 20,
							'ParentID'    : 2,
							'ClientName' : '@name()',
							'CreateData' : '@date()'
						}
					]
				},
				{
					'ClientID' : 3,
					'ClientName' : '@name()',
					'CreateData' : '@date()',
					'ParentID'   : 0,
					'Children|1-5' :[
						{
							'ClientID|+1' : 30,
							'ParentID'    : 3,
							'ClientName' : '@name()',
							'CreateData' : '@date()'
						}
					]
				},
				{
					'ClientID' : 4,
					'ClientName' : '@name()',
					'CreateData' : '@date()',
					'ParentID'   : 0,
					'Children|1-5' :[
						{
							'ClientID|+1' : 40,
							'ParentID'    : 4,
							'ClientName' : '@name()',
							'CreateData' : '@date()'
						}
					]
				},
				{
					'ClientID' : 5,
					'ClientName' : '@name()',
					'CreateData' : '@date()',
					'ParentID'   : 0,
					'Children|1-5' :[
						{
							'ClientID|+1' : 50,
							'ParentID'    : 5,
							'ClientName' : '@name()',
							'CreateData' : '@date()'
						}
					]
				}
			]
		},
		'client/all' : [
			{
				'ClientID' : 1,
				'ClientName' : 'Microsoft'
			},
			{
				'ClientID' : 2,
				'ClientName' : 'Apple'
			},
			{
				'ClientID' : 3,
				'ClientName' : 'forde'
			},
			{
				'ClientID' : 4,
				'ClientName' : 'ZTE'
			},
			{
				'ClientID' : 5,
				'ClientName' : 'Tesla'
			},
			{
				'ClientID' : 10,
				'ClientName' : 'KFC'
			}
		],
		'roles/list' : [
			{
				'RoleID' : 1,
				'RoleName' : 'Account Team'
			},
			{
				'RoleID' : 2,
				'RoleName' : 'Strategist'
			},
			{
				'RoleID' : 3,
				'RoleName' : 'Service Center'
			},
			{
				'RoleID' : 4,
				'RoleName' : 'Admin'
			}
		],
		'client/delete/\\d+' : {
			'test' : true
		},
		'client/create' : {
			isOpen : true,
			data :{
				'ClientID|+1' : 8888,
				'ClientName' : '@name()',
				'CreateData' : '@date()',
				'ParentID'   : 2
			}
		},
		'client/save/\\d+' : {
			'test' : true
		}
	};
});
