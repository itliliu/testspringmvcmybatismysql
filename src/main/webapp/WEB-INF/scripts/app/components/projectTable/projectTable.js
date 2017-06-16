define(['underscore', 'angular','app/components/module', 'bootStrap','dateTimePicker'],
	function(_, angular, app) {
		'use strict';
		app.controller('projectTableController', ['$scope', '$attrs', function($scope/*, $attrs*/) {
			$scope.remove = function(item , index) {
				if($scope.data.list) {
					$scope.data.list.splice(index,1);
					$scope.priceCalculator();
					if(!$scope.data.list.length) {
						$scope.addNew();
					};
				} else {
					$scope.data.splice(index,1);
					if(!$scope.data.length) {
						$scope.addNew();
					};
				}
			};
			$scope.addNew = function() {
				if($scope.data.list) {
					$scope.data.list.push($.extend({},$scope.emptyItem));
				} else {
					$scope.data.push($.extend({},$scope.emptyItem));
				};
			};
			$scope.priceCalculator = function() {
				var total = 0, data = $scope.data.list, curPrice = '';
	            for(var i = 0; i < data.length; i++ ) {
	            	curPrice = data[i].price? parseInt(data[i].price, 10): 0;
	            	if(!isNaN(curPrice) && typeof curPrice === 'number') {
	                	total = total + curPrice;
	            	} else {
	            		total = total + 0;
	            	}
	            }
	            $scope.data.total = total;
			};
			if(angular.isUndefined($scope.data)) {
				$scope.data = [];
				$scope.addNew();
			};
		}])
		.directive('projectTablePricing', ['$rootScope', '$compile', '$timeout',
			function(/*$rootScope, $compile, $timeout*/){
			return {
				restrict: 'AE',
				templateUrl:'scripts/app/components/projectTable/pricing.html',
				scope : {
				 	data : '=?',
				 	min : '@',
				 	canEdit : '=?'
				},
				controller: 'projectTableController',
 				controllerAs: 'projectTable',
				compile : function(/*$element, $attrs,  $interpolate, $timeout*/) {
					return  function($scope/*,element,attr, controller*/){
						$scope.emptyItem = {
							name : '',
							price : ''
						};
			        };
				}
			};
		}])
		.directive('projectTableTimeline',[function(){
			return {
				restrict: 'AE',
				templateUrl:'scripts/app/components/projectTable/timeline.html',
				scope : {
				 	data : '=?',
				 	min : '@',
				 	canEdit : '=?'
				},
				controller: 'projectTableController',
 				controllerAs: 'projectTable',
				compile : function() {
					return  function($scope){
						$scope.emptyItem = {
							name : '',
							date : ''
						};
			        };
				}
			};
		}])
		.directive('projectTableContact', ['$rootScope','$compile','$timeout',
			function(/*$rootScope, $compile, $timeout*/) {
			return {
				restrict: 'AE',
				templateUrl:'scripts/app/components/projectTable/contact.html',
				scope : {
				 	data : '=?',
				 	min : '@',
				 	canEdit : '=?'
				},
				controller: 'projectTableController',
 				controllerAs: 'projectTable',
				compile : function( ) {
					return  function($scope){
						$scope.emptyItem = {
							name  : '',
							title : '',
							email : '',
							tel   : ''
						};
			        };
				}
			};
		}])
		.directive('projectTableReport',
			['$rootScope', '$compile', '$timeout', function(/*$rootScope, $compile, $timeout*/){
			return {
				restrict: 'AE',
				templateUrl:'scripts/app/components/projectTable/report.html',
				scope : {
				 	data : '=?',
				 	min : '@',
				 	canEdit : '=?',
				 	getReportTemplate:'&',
				 	addRequest:'&',
				 	deleteRequest:'&',
				 	formatList:'=',
				 	reportTypeList:'='
				},
				controller: 'projectTableController',
 				controllerAs: 'projectTable',
				compile : function( ) {
					return  function($scope){
						$scope.emptyItem = {
							name  : '',
							reportType:'',
							reporttype : '',
							format: '',
							formattype : '',
							selectedTemplate : '',
							powerBITempName : '',
							isNew:true
						};
			        };
				}
			};
		}]);

	}
);
