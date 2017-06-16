define(['angular', 'app/help/module', 'underscore','app/config/urlConfig','app/help/httpService'],
function (angular, module) {
    module.factory('srvBrief', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var saveBrief = function(params) {
            var url = urlConfig.brief.save;
            return $services.post(url,params);
        };  
        return {
            saveBrief:saveBrief, 
        };
    }]);
});
