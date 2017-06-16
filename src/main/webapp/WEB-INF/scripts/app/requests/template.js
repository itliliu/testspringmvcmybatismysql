define(['angular', 'app/help/module', 'underscore','app/config/urlConfig','app/help/httpService'],
function (angular, module) {
    module.factory('srvTemplate', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var getTemplateList = function (params) {
            var url = urlConfig.template.templateList;
             return $services.get(url,params);
        };
        var getClientList = function(params) {
            var url = urlConfig.menu.getClientList;
            return $services.get(url,params);
        };
        var getChildClient = function(params) {
            var url = urlConfig.menu.getChildClient;
            return $services.get(url,params);
        };
        var getClientInfo = function(params) {
            var url = urlConfig.template.getClientInfo;
            return $services.get(url,params);
        };
        var selectType = function(params) {
            var url = urlConfig.template.selectType;
            return $services.get(url,params);
        };
        var getTemplateDetailed = function(params) {
            var url = urlConfig.template.templateDetail;
            return $services.get(url,params);
        };
        var deleteTemplate = function(params) {
            var url = urlConfig.template.deleteTemplate;
            return $services.put(url,params);
        };
        var saveTemplate = function(params) {
            var url = urlConfig.template.saveTemplate;
            return $services.post(url,params);
        };
        var getPowerBI = function(params) {
            var url = urlConfig.template.getPowerbi;
            return $services.get(url,params);
        };
        return {
            getTemplateList: getTemplateList,
            getClientList: getClientList,
            getChildClient: getChildClient,
            selectType:selectType,
            getDetailed:getTemplateDetailed,
            deleteTemplate:deleteTemplate,
            getPowerBI:getPowerBI,
            saveTemplate:saveTemplate,
            getClientInfo: getClientInfo
        };
    }]);
});
