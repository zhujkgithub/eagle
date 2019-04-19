(function($) {
    $.fn.bootstrapValidator.i18n.username = $.extend($.fn.bootstrapValidator.i18n.username || {}, {
        'default': '请输入有效用户名'
    });
    $.fn.bootstrapValidator.validators.username = {
        validate: function(validator, $field, options) {
           var _v =$field.val();
           if(_v.length < 6 || _v.length >20){
               return false;
           }
            return true;
        }
    };
}(window.jQuery));