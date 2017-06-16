define(['angular', 'app/help/module', 'underscore', 'app/config/urlConfig', 'app/help/httpService'],
function (angular, module) {
    module.factory('srvClient', ['authHttp', '$q', 'urlConfig', 'services',
    function ($http, $q, urlConfig, $services) {
        var urls = urlConfig.client;
        function list(data) {
            return  $services.get(urls.list, data);
        };
        function all(data) {
            return  $services.get(urls.all, data);
        };
        function create(data) {
            return  $services.post(urls.create, data);
        };
        function update(data) {
            return  $services.put(urls.update, data);
        };
        function deactivate(data) {
            return  $services.put(urls.deactivate, data);
        };
        return {
            list   : list,
            all    : all,
            create : create,
            update : update,
            deactivate : deactivate
        };
    }]);
});
