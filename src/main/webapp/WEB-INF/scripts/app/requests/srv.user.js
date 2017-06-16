define(['angular', 'app/help/module', 'underscore', 'app/config/urlConfig', 'app/help/httpService'],
function (angular, module) {
    module.factory('srvUser', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var urls = urlConfig.user;
        function list(data) {
            return  $services.get(urls.list, data);
        };
        function create(data) {
            return  $services.post(urls.create, data);
        };
        function update(data) {
            return  $services.put(urls.update, data);
        };
        function deletef(data) {
            return  $services.delete(urls.delete, data);
        };
        return {
            list   : list,
            create : create,
            update : update,
            delete : deletef
        };
    }]);
});
