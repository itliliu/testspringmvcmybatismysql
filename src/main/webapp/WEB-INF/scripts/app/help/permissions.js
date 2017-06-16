/**  Help - permissions.js */
define(['angular', 'app/help/module'], function (angular, module) {
    module.factory('permissions', ['$rootScope', 'srvCookie',
    function ($rootScope, $srvCookie) {
        var permissionList;
        /**
        *   permissions  permission list
        */
        var setPermissions = function(permissions) {
            permissionList = permissions;
            $rootScope.$broadcast('permissionsChanged');
        };
        /**
        *   permission   page-action(e.g. projectList-view)
        */
        var hasPermission = function(permission) {
            if(!permissionList) {
                permissionList = $srvCookie.get('userInfo')? $srvCookie.get('userInfo').permission: [];
            }
            permission = permission.trim().split('-');
            if(permissionList) {
                var parent = permission[0], child = permission[1];
                    hasPermission = parseInt(permissionList[parent][child], 10);
                if(hasPermission) {
                    return true;
                }
                return false;
            }
        };
        return {
            setPermissions: setPermissions,
            hasPermission: hasPermission
        };

    }]);
});
