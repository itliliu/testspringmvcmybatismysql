define(['angular','app/directives/module'],function(angular,app){
	app.directive('customAutofocus',function(){
		return {
			restrict:'AE',
			link:function($scope,ele){
				$scope.$watch('tab.isEdit',function(){
					$(ele).focus().select();
				});
			}
		};
	});
});
