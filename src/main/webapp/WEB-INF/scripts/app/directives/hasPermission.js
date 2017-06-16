define(['angular','app/directives/module'],function(angular,app){
	app.directive('hasPermission', ['permissions', function($permissions){
		return {
			restrict:'AE',
			link:function($scope, element, attrs){
				if(typeof attrs.hasPermission !== 'string') {
					throw 'hasPermission value must be a string';
				}
				var value = attrs.hasPermission.trim();
				function toggleVisibilityBasedOnPermission() {
					var hasPermission = $permissions.hasPermission(value);
					if(hasPermission) {
						element.show();
					} else {
						element.hide();
					};
				}
				toggleVisibilityBasedOnPermission();
				$scope.$on('permissionsChanged', toggleVisibilityBasedOnPermission);
			}
		};
	}]);
});
