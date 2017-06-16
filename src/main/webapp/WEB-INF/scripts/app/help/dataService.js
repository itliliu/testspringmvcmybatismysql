/**  Help - dataService.js */
define(['angular','app/help/module'],function(angular,module){
	module.factory('dataService',[function(){
        var data = 'for test';
        var set = function(val){
            data = val;
        };
    	var get = function(){
            return data;
        };

        return {
            set:set,
            get:get
        };
	}]);
});
