define(['angular','app/directives/module','jqueryForm','app/help/customAlert'],function(angular,app){
	app.directive('uploadFile',['$rootScope', 'customAlert',function($rootScope, $customAlert){
		return {
			restrict:'AE',
			scope:{
				request:'=?',
				template:'=?'
			},
			link:function($scope,ele){
				$(ele).on('change','input[name=file]',function(){
					if(!this.files.length){
						return;
					}
					$(ele).ajaxSubmit({
						'success':function(dbData){
							if(dbData.code === 'NACK') {
								$customAlert.warning(dbData.message);
							}
							manualApply(function(){
								if($scope.request){
									$scope.request.reportFileUrl = dbData.data;
								}else if($scope.template){
									$scope.template.fileurl = dbData.data;
								}
							});
						},
						'error':function() {
							$customAlert.alert('Upload file failed.');
						}
					});
				});
				function manualApply(fun){
					if ($scope.$$phase || $rootScope.$$phase) {
                        fun();
                    } else {
                        $scope.$apply(fun);
                    }
				}
			}
		};
	}]);
});
