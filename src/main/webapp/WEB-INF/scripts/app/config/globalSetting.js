/**  Config - globalSetting.js */
define(['mobiledetect'], function (mobiledetect) {
    var md = new mobiledetect(window.navigator.userAgent);
    return {
        device: {
            isPhone: md.phone() ? true : false,
            isTablet: md.tablet() ? true : false,
            isWeb: !md.phone() && !md.tablet() ? true : false
        }
    };
});
