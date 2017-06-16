/**  Help - customAlert.js */
define(['angular', 'app/help/module'], function (angular, module) {
    module.factory('customAlert', ['$http', '$q', function () {
        //text prompt
        function showMessage(msg,type) {
            $('#m-msg-section').remove();
            var temp = '<div id="m-msg-section">'+
                        '<div class="alert alert-danger" role="alert">'+
                           '<span id="msg-content"></span>'+
                        '</div></div>';
            $('body').append(temp);
            var $msgContainer = $('#m-msg-section'), $msgContent =  $('#msg-content'),
                fadeOutTimeout = null;

            $msgContent.text(msg);
            $msgContainer.children()
                        .attr('class', 'alert alert-' + type)
                        .parent()
                        .fadeIn();
            if (fadeOutTimeout) {
                clearTimeout(fadeOutTimeout);
            }

            fadeOutTimeout = setTimeout(function() {
                $msgContainer.fadeOut();
            }, 3000);
        }

        var success = function(msg) {
             showMessage(msg, 'success');
        };

        var info = function(msg) {
             showMessage(msg, 'info');
        };

        var warning = function(msg) {
             showMessage(msg, 'warning');
        };

        var alert = function(msg) {
             showMessage(msg, 'danger');
        };

        /**
        *   msg         text
        *   callback    Name of the callback function
        *   params      params of the callback function
        */
        var confirm = function(msg, callback, params) {
            var confirmHtml = '<div id="m-confirm-section" class="dialog-box confirm-promt">'+
                                '<div class="dialog">'+
                                    '<div class="dialog-title confirm-msg color-666"></div>'+
                                    '<div class="dialog-bottom clearfix">'+
                                        '<button id="btn_no" class="confirm-cancel">Cancel</button>'+
                                        '<button id="btn_ok" class="confirm-submit">OK</button>'+
                                    '</div>'+
                                '</div>'+
                            '</div>';

            $('body').append(confirmHtml);
            $('.confirm-msg').text(msg).fadeIn();

            //Ok button
            $('#btn_ok').click(function () {
                $('#m-confirm-section').remove();
                if ( typeof(callback) === 'function' ) {
                    callback(params);
                }
            });
            //Cancel button
            $('#btn_no').click(function () {
                $('#m-confirm-section').remove();
            });
        };

        /**
        *   msg         text
        *   callback    Name of the callback function
        *   url         redirect url
        */
        var ok = function(msg, callback, url) {
            var confirmHtml = '<div id="m-confirm-section" class="dialog-box confirm-promt">'+
                                '<div class="dialog">'+
                                    '<div class="dialog-title confirm-msg color-666"></div>'+
                                    '<div class="dialog-bottom clearfix">'+
                                        '<button id="btn_ok" class="confirm-submit float-none">OK</button>'+
                                    '</div>'+
                                '</div>'+
                            '</div>';

            $('body').append(confirmHtml);
            $('.confirm-msg').text(msg).fadeIn();

            //Ok button
            $('#btn_ok').click(function () {
                $('#m-confirm-section').remove();
                if ( typeof(callback) === 'function' ) {
                    callback(url);
                }
            });
        };

        var comment = function(msg, callback, params) {
            var confirmHtml = '<div id="m-confirm-section" class="dialog-box confirm-promt">'+
                                '<div class="dialog">'+
                                    '<div class="dialog-title confirm-msg color-666"></div>'+
                                    '<textarea></textarea>'+
                                    '<div class="dialog-bottom clearfix">'+
                                        '<button id="btn_no" class="confirm-cancel">Cancel</button>'+
                                        '<button id="btn_ok" class="confirm-submit">Save</button>'+
                                    '</div>'+
                                '</div>'+
                            '</div>';

            $('body').append(confirmHtml);
            $('.confirm-msg').text(msg).fadeIn().css({'padding': '0','min-height': '42px','line-height': '42px'});
            var confirmStyle = {'width': '90%','height': '120px','border-radius': '8px', 'padding': '5px'};
            $('#m-confirm-section').find('textarea').css(confirmStyle);

            //Ok button
            $('#btn_ok').click(function () {
                $('#m-confirm-section').remove();
                if ( typeof(callback) === 'function' ) {
                    callback(params);
                }
            });
            //Cancel button
            $('#btn_no').click(function () {
                $('#m-confirm-section').remove();
            });
        };

        return {
            success: success,
            info: info,
            warning: warning,
            alert: alert,
            confirm: confirm,
            ok:ok,
            comment:comment
        };

    }]);
});
