define(['angular', 'app/help/module', 'underscore','app/config/urlConfig','app/help/httpService'],
function (angular, module) {
    module.factory('srvHome', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {

        var createRequest = function (params) {
            var url = urlConfig.home.createRequest;
             return $services.post(url,params);
        };

        var updateRequest = function (params) {
            var url = urlConfig.home.updateRequest;
             return $services.put(url,params);
        };

        var finalizeRequest = function (params) {
            var url = urlConfig.home.finalizeRequest;
             return $services.put(url,params);
        };

        var getBriefInfo = function(params) {
            var url = urlConfig.requestForm.getBriefInfo;
            return $services.get(url,params);
        };

        var changePublish = function (params) {
            var url = urlConfig.home.changePublish;
             return $services.put(url,params);
        };

        var deleteBrief = function (params) {
            var url = urlConfig.home.deleteBrief;
             return $services.put(url,params);
        };

        var getBriefList = function (params) {
            var url = urlConfig.home.getBriefList;
            return $services.get(url,params);
        };

        var getRequestTypeList = function (params) {
            var url = urlConfig.home.index;
             return $services.post(url,params);
        };

        var getTemplateList = function (params) {
            var url = urlConfig.home.index;
            return $services.post(url,params);
        };

        var searchDataForID = function(params) {
            var url = urlConfig.home.searchDataForId;
            return $services.get(url,params);
        };

        var saveFillForm = function(params) {
            var url = urlConfig.home.saveFillForm;
            return $services.put(url,params);
        };

        var submitFillForm = function(params) {
            var url = urlConfig.home.submitFillForm;
            return $services.put(url,params);
        };

        return {
            createRequest: createRequest,
            updateRequest: updateRequest,
            finalizeRequest: finalizeRequest,
            changePublish: changePublish,
            getBriefInfo: getBriefInfo,
            deleteBrief: deleteBrief,
            getBriefList: getBriefList,
            getRequestTypeList: getRequestTypeList,
			getTemplateList: getTemplateList,
            searchDataForID: searchDataForID,
            saveFillForm: saveFillForm,
            submitFillForm: submitFillForm
        };
    }]);
});
