define(['angular', 'app/help/module', 'underscore','app/config/urlConfig','app/help/httpService'],
function (angular, module) {
    module.factory('srvProject', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var getProjectList = function (params) {
            var url = urlConfig.project.getProjectList;
            return $services.get(url,params);
        };
        var updateProjectOperatorDate = function (params) {
            var url = urlConfig.project.updateProjectOperatorDate;
            return $services.get(url,params);
        };
        var getFilePath = function(params) {
            var url = urlConfig.common.getFilePath;
            return $services.get(url,params);
        };
        return {
            getProjectList: getProjectList,
            updateProjectOperatorDate: updateProjectOperatorDate,
            getFilePath: getFilePath
        };
    }]);
});
