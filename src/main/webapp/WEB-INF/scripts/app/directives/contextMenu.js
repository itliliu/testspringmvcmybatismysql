define(['angular','app/directives/module'],function(angular,app){
	app.directive('contextMenu',['$rootScope','$compile',
		function($rootScope,$compile){
		return{
			restrict:'AE',
			link:function(scope,ele){
				var $ele = $(ele),
					appScope = scope.grid.appScope;

				$ele.on('copy selectstart',function(event){
					event.preventDefault();
				});
				$ele.on('contextmenu',function(event){
					event.preventDefault();
					var x = event.pageX,
						y = event.pageY,
						offsetx = 15,
						offsety = 5;
					appScope.setAllSelectedStatus(false);
					appScope.manualApply(function(){
						scope.currentRow = scope.row;
						scope.currentRow.entity.selected = true;
						initContextMenu({x:x+offsetx,y:y+offsety});
					});
				});
				$ele.on('click',function(event){
					event.preventDefault();
					appScope.setAllSelectedStatus(false);
					removeContextmenu();
				});
				scope.detele = function(){
					removeData(scope.currentRow.entity);
					removeContextmenu();
				};
				function initContextMenu(pos){
					removeContextmenu();
					var menuTemplate = '<div id="contextmenu-section" class="menu-box">'+
										'<ul>'+
											'<li ng-click="detele()">Delete</li>'+
										'</ul>'+
									'</div>';
					var compileCallback = $compile(menuTemplate);
					var $dom = compileCallback(scope);
					if(pos){
						$dom.css({left:pos.x,top:pos.y});
					}
					$dom.appendTo('body');
				};
				function removeContextmenu(){
					$('#contextmenu-section').remove();
				}
				function indexof(item){
					var that = appScope.gridOptions.data;
					for(var i=0; i<that.length; i++){
						if(that[i].contentId === item.contentId){
							return i;
						}
					}
				}
				function removeData(item){
					var index = indexof(item);
					appScope.gridOptions.data.splice(index,1);
					appScope.updateDeletedIds(item);
				}
			}
		};
	}]);
});
