define(['angular', 'app/components/module'], function(angular,app) {
	'use strict';

	app.directive('datetimepicker', [function() {
		return {
			restrict:'A',
			scope : {
			 	dateFormat : '@?',
			 	minView    : '@?'
			},
			compile: function (/*element, attrs, transclude*/) {
		        return  function(scope, element){
		      		element.datetimepicker({
			             format: scope.dateFormat,
			             minView: scope.minView,
			             autoclose: true,
			             todayBtn: true
			        });
		        };
		    }
		};
	}]);
});
