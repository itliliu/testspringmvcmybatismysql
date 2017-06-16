define(['angular', 'app/help/module', 'underscore', 'app/config/urlConfig', 'app/help/httpService'],
function (angular, module) {
    'use strict';
    module.factory('srvRole', ['urlConfig', 'services',
    function (urlConfig, $services) {
        function all(data) {
            return  $services.get(urlConfig.common.roles, data);
        };
        return {
            all   : all
        };
    }]);
});
