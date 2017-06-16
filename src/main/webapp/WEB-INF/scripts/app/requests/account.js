define(['angular', 'app/help/module', 'underscore', 'app/config/urlConfig', 'app/help/httpService'],
function (angular, module) {
    module.factory('srvAccount', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var login = function () {
            var url = urlConfig.account.login;
            return $services.post(url);
        };
        var loginForLocal = function (formData) {
            var url = urlConfig.account.loginForLocal;
            return $services.post(url,formData);
        };
        return {
            login: login,
            loginForLocal: loginForLocal
        };

    }]);
});
