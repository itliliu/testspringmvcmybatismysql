define(['angular', 'app/help/module', 'underscore','app/config/urlConfig',
       'app/help/httpService','app/help/customAlert'],
function (angular, module) {
    module.factory('srvProjectForm', ['authHttp', '$q', 'urlConfig', 'services', 'customAlert',
    function ($http, $q, urlConfig, $services, $customAlert) {
        var getBriefInfo = function (params) {
            var url = urlConfig.project.getBriefInfo;
             return $services.get(url,params);
        };
        var getProposalInfo = function (params) {
            var url = urlConfig.project.getProposalInfo;
             return $services.get(url,params);
        };
        var getRequestInfo = function (params,clientId) {
            var url = urlConfig.project.getReportInfo,
                deferred = $q.defer();
            $http.get(url,{params:params}).success(function(dbdata){
                if(dbdata.code === 'ACK'){
                    deferred.resolve(updateRequestTemplate(dbdata.data,clientId));
                    return true;
                } else {
                    $customAlert.warning(dbdata.message);
                }
                deferred.reject(new Error());
            }).error(function(error){
                deferred.reject(error);
            });
            return deferred.promise;
        };
        var getReportInfo = function (params) {
            var url = urlConfig.project.getReportInfo;
             return $services.get(url,params);
        };
        //brief
        var saveBrief = function (params) {
            var url = urlConfig.project.saveBrief;
             return $services.post(url,params);
        };
        var submitBrief = function (params) {
            var url = urlConfig.project.submitBrief;
             return $services.post(url,params);
        };
        var deleteBrief = function (params) {
            var url = urlConfig.project.deleteBrief;
             return $services.post(url,params);
        };
        //proposal
        var saveProposal = function (params) {
            var url = urlConfig.project.saveProposal;
             return $services.post(url,params);
        };
        var submitProposal = function (params) {
            var url = urlConfig.project.submitProposal;
             return $services.post(url,params);
        };
        var approveProposal = function (params) {
            var url = urlConfig.project.approveProposal;
             return $services.post(url,params);
        };
        var rejectProposal = function (params) {
            var url = urlConfig.project.rejectProposal;
             return $services.post(url,params);
        };
        var getReportType = function(params) {
            var url = urlConfig.template.selectType;
            return $services.get(url,params);
        };
        //request
        var saveRequest = function (params) {
            var url = urlConfig.project.saveRequest;
             return $services.post(url,params);
        };
        var commitRequest = function (params) {
            var url = urlConfig.project.commitRequest;
             return $services.post(url,params);
        };
        //report
        var saveReport = function (params) {
            var url = urlConfig.project.saveReport;
             return $services.post(url,params);
        };
        var saveReportFile = function(params){
            var url = urlConfig.project.saveReportFile;
            return $services.post(url,params);
        };
        var rejectReport = function (params) {
            var url = urlConfig.project.rejectReport;
             return $services.post(url,params);
        };
        var completeReport = function (params) {
            var url = urlConfig.project.completeReport;
             return $services.post(url,params);
        };
        var updateRequestTemplate = function(data,clientId){
            var deferred = $q.defer();
            var promise = [];
            for(var i=0; i<data.length; i++){
                var params = {
                    'clientId':clientId,
                    'reportType':data[i].reportTypeID,
                    'format':data[i].formatID
                };
                promise.push(getTemplateList(angular.extend(params)));
            }
            $q.all(promise).then(function(results){
                if(results){
                    for(var i=0; i<results.length; i++){
                        data[i].templateList = results[i].data;
                    }
                    deferred.resolve(data);
                }else{
                    deferred.reject(new Error());
                }
            },function(error){
                deferred.reject(error);
            });
            return deferred.promise;
        };
        var getTemplateList = function(params){
            var url = urlConfig.project.getTemplateList;
            return $services.get(url,params);
        };
        var uploadFile = function(params,endText){
            var url = urlConfig.common[endText];
            return $services.post(url,params);
        };
        var createBriefDoc = function(params) {
            var url = urlConfig.project.createBriefDoc;
            return $services.get(url,params);
        };
        return {
            getBriefInfo: getBriefInfo,
            getProposalInfo: getProposalInfo,
            getRequestInfo: getRequestInfo,
            getReportInfo: getReportInfo,
            saveBrief: saveBrief,
            submitBrief: submitBrief,
            deleteBrief: deleteBrief,
            saveProposal: saveProposal,
            submitProposal: submitProposal,
            approveProposal: approveProposal,
            rejectProposal: rejectProposal,
            saveRequest: saveRequest,
            commitRequest: commitRequest,
            saveReport: saveReport,
            saveReportFile:saveReportFile,
            rejectReport: rejectReport,
            completeReport: completeReport,
            getTemplateList:getTemplateList,
            uploadFile:uploadFile,
            getReportType: getReportType,
            createBriefDoc: createBriefDoc
        };
    }]);
});
