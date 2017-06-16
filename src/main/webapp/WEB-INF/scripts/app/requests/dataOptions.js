define([], function() {
	var dataOption = {
		reportType : [
			{'name' : 'Citation', 'value' : 'Citation'},
			{'name' : 'EOD', 'value' : 'EOD'},
			{'name' : 'EOM', 'value' : 'EOM'}
		],
		reportTypeDefault : {'name' : 'Chooose Type', 'value' : ''},
		reportFormat : [
			{'name' : 'Word', 'value' : 'Word'},
			{'name' : 'PPT', 'value' : 'PPT'}
		],
		reportFormatDefault : {'name' : 'Chooose Format', 'value' : ''}
	};
	return dataOption;
});
