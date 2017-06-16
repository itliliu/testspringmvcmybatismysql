define(['app/directives/module'],function(module){
	module.directive('customCollapse',function(){
		return {
			restrict:'AE',
			scope:{
				targetId:'@'
			},
			link:function($scope,$ele){
				$ele.on('click',function(){
					var $targetEle = $('#'+$scope.targetId);
					if($targetEle.is(':hidden')){
						$targetEle.show();
					}else{
						$targetEle.hide();
					}
				}); 
			}
		};
	});
});
