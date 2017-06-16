define(['angular','app/directives/module','underscore'],function(angular,app){
	app.directive('multiSelect',['$rootScope',function($rootScope){
		return {
			restrict:'AE',
			scope:{
				filterOptions:'=',
				callBack:'&'
			},
			link:function(scope,ele){
				var $ele = $(ele);
				scope.startIndex = -1;
				scope.endIndex = -1;

				$ele.on('click','li',function(event){
					event.preventDefault();
					event.stopPropagation();
					if(isInvalidData()){
						return;
					}
					var i = $(event.currentTarget).index();
					if(event.ctrlKey || event.metaKey){
						ctrlSelect(i);
					}else if(event.shiftKey){
						shiftSelect(i);
					}else{
						selectOne(i);
					}
					scope.callBack();
				});

				$ele.on('copy selectstart',function(event){
					event.preventDefault();
				});

				function apply(fun){
					if (scope.$$phase || $rootScope.$$phase) {
                        fun();
                    } else {
                        scope.$apply(fun);
                    }
				}
				function isInvalidData(){
					if(!scope.filterOptions
						|| scope.filterOptions&&!scope.filterOptions.length){
						return true;
					}
					return false;
				}
				/*operations for data option*/
				function resetOptions(){
					angular.forEach(scope.filterOptions,function(item){
						item.selected = false;
					});
				}
				function selectOne(i){
					if(_.isUndefined(i)){
						i = 0;
					}
					resetOptions();
					apply(function(){
						scope.filterOptions[i].selected = true;
						scope.startIndex = i;
						scope.endIndex = -1;
					});
				}
				function ctrlSelect(i){
					apply(function(){
						var item = scope.filterOptions[i];
						item.selected = !item.selected;
						if(item.selected){
							scope.startIndex = i;
							scope.endIndex = -1;
						}
					});
				}
				function shiftSelect(i){
					if(scope.endIndex>=0){
						groupSelect(scope.startIndex,scope.endIndex,false);
					}
					groupSelect(scope.startIndex,i,true);
					scope.endIndex = i;
				}
				function groupSelect(startIndex,endIndex,flag){
					if(startIndex>endIndex){
						apply(function(){
							for(var j = endIndex;j<startIndex;j++){
								scope.filterOptions[j].selected = flag;
							}
						});
					}else{
						apply(function(){
							for(var k = endIndex;k>startIndex;k--){
								scope.filterOptions[k].selected = flag;
							}
						});
					}
				}
			}
		};
	}]);
});
