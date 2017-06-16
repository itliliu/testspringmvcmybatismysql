define([ 'angular',
	'app/mock/data/configuration',
	'app/mock/data/project',
	'app/mock/data/report',
	'app/mock/data/user',
	'app/mock/data/client'
], function() {
	var argData = _.last(arguments, arguments.length-1);
	argData.unshift({});
	var data = angular.extend.apply(null , argData);
	return data;
});
