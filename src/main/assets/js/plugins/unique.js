(function ($, window) {
    const defaultSettings = {
        'uqTrigger': 'blur',
        'uqErrContainer': '.uq-err-container',
        'uqErrWrap': 'li',
    };

    const requiredSettings = [
        'uqUrl',
        'uqTrigger',
        'uqErrContainer',
        'uqErrWrap',
    ];

    let _methods = {
        process: function (event) {
            let $this = $(this),
                settings = $this.data('uqSettings'),
                name = $this.attr('name');

            $this.closest('form').validate({
                rules: {
                    [name]: $.extend({
                        remote: settings.uqUrl
                    })
                },
                validClass: 'is-valid',
                errorClass: 'is-invalid',
                errorLabelContainer: settings.uqErrContainer,
                wrapper: settings.uqErrWrap,
                onkeyup: false,
                onblur: true,
                onfocusout: function (element) {
                    let $element = $(element),
                        settings = $element.data('uqSettings');

                    if (settings.uqInitValue !== $element.val()) {
                        $element.valid();
                    } else {
                        _methods.removeError($element, 'is-invalid');
                        _methods.removeValid($element, 'is-valid');
                        _methods.disableSubmit($element);
                        $element.closest('form').find(settings.uqErrContainer).html('');
                    }
                },
                onsubmit: false,
                messages: {
                    [name]: settings.uqErrorMessage
                        ? {
                            remote: settings.uqErrorMessage
                        }
                        : _methods.messages.apply(this, [_methods.lang.apply(this)]).error
                },
                highlight: _methods.highlight,
                unhighlight: _methods.unhighlight,
                success: _methods.success,
            })
        },
        messages: function (lang) {
            switch (lang) {
                case 'ru':
                    return {
                        error: {
                            remote: "Этот адрес уже занят."
                        },
                        success: {
                            remote: "Адрес свободен."
                        }
                    };

                case 'en':
                default:
                    return {
                        error: {
                            remote: "This address already taken."
                        },
                        success: {
                            remote: "Address is free."
                        }
                    };
            }
        },
        success: function (label, element) {
            let $label = $(label),
                $element = $(element),
                settings = $element.data('uqSettings');

            let message = settings.uqSuccessMessage
                ? settings.uqSuccessMessage
                : _methods.messages.apply(this, [_methods.lang.apply(this)]).success.remote;

            $label.text(message);
            _methods.removeError($element, 'is-invalid');
            _methods.showValid($element, 'is-valid');
            _methods.enableSubmit($element);
        },
        highlight: function (element, errorClass, validClass) {
            let $element = $(element);

            _methods.removeValid($element, validClass);
            _methods.showError($element, errorClass);
        },
        unhighlight: function (element, errorClass, validClass) {
            let $element = $(element);
        },
        removeValid: function ($element, validClass) {
            let settings = $element.data('uqSettings'),
                $container = $element.closest('form').find(settings.uqErrContainer);

            $element.removeClass(validClass);
            $container.removeClass('valid-feedback').removeClass('d-flex');
        },
        showValid: function ($element, validClass) {
            let settings = $element.data('uqSettings'),
                $container = $element.closest('form').find(settings.uqErrContainer);

            $element.addClass(validClass);
            $container.addClass('valid-feedback').addClass('d-flex');
        },
        removeError: function ($element, errorClass) {
            let settings = $element.data('uqSettings'),
                $container = $element.closest('form').find(settings.uqErrContainer);

            $element.removeClass(errorClass);
            $container.removeClass('invalid-feedback');
        },
        showError: function ($element, errorClass) {
            let settings = $element.data('uqSettings'),
                $container = $element.closest('form').find(settings.uqErrContainer);

            $element.addClass(errorClass);
            $container.addClass('invalid-feedback').show();
        },
        disableSubmit: function ($element) {
            let $submit = $element.closest('form').find('[type="submit"');
            $submit.attr('disabled', 'disabled');
        },
        enableSubmit: function ($element) {
            let $submit = $element.closest('form').find('[type="submit"');
            $submit.removeAttr('disabled');
        },
        lang: function () {
            let $this = $(this),
                settings = $this;

            if (!settings.lang) {
                settings.lang = $('body').attr('lang');
            }

            return settings.lang;
        }
    };

    let methods = {
        init: function (options) {
            return this.each(function () {
                let $this = $(this);

                if ($this.data('uq') === true) {
                    return;
                }

                let settings = $.extend(defaultSettings, {
                    uqUrl: $this.data('uqUrl'),
                    uqTrigger: $this.data('uqTrigger'),
                    uqSuccessMessage: $this.data('msg-remote-success'),
                    uqErrorMessage: $this.data('msg-remote'),
                    uqInitValue: $this.val()
                }, options);

                requiredSettings.forEach(function (setting) {
                    if (!settings[setting]) {
                        $.error(setting + ' is required setting.');
                    }
                });

                $this.data('uqSettings', settings);
                _methods.process.apply(this);
                _methods.disableSubmit($this);
                $this.data('uqInitialized', true);
            });
        },
        destroy: function () {
            return this.each(function () {
                var $this = $(this);

                $this.removeData('uqInitialized');
            });
        }
    };

    $.fn.uq = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method with name ' + method + ' does not exist in jQuery.following');
        }
    };

    $(document).ready(function () {
        $('[uq]').uq();
    });
})(jQuery, window);
