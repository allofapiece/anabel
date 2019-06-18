(function ($, window) {
    (function () {
        let settings;

        let methods = {
            init: function(options) {
                settings = $.extend( {
                    submitObjectSelector: '[type="submit"]',
                    loaderClass:          'loader-sk-fading-circle',
                    loaderItemClass:      'sk-circle',
                    loaderItemsCount:     12
                }, options);

                return this.each(function () {
                    var $this = $(this);

                    $this.on('submit', methods.submit);
                });
            },
            submit: function(event) {
                event.preventDefault();

                $(this).ajaxSubmit({
                    beforeSubmit: function (arr, $form, options) {
                        methods.beforeSubmit.apply(this, [arr, $form, options]);
                    },
                    error: function (jqXHR, textStatus, errorThrown, $form) {
                        methods.error.apply(this, [jqXHR, textStatus, errorThrown, $form]);
                    },
                    success: function (data, textStatus, jqXHR, $form) {
                        methods.success.apply(this, [data, textStatus, jqXHR, $form]);
                    },
                    complete: function (jqXHR, textStatus, $form) {
                        methods.complete.apply(this, [jqXHR, textStatus, $form]);
                    }
                });
            },
            beforeSubmit: function (arr, $form, options) {
                let $submitObject = $form.find(settings.submitObjectSelector);
                methods.toggleSubmitObject($submitObject, $form);
            },
            success: function (data, textStatus, jqXHR, $form) {
                if (data.message) {
                    $.alert.configure({message: data.message}).show();
                }

                if (data.hasErrors) {
                    $.each(data.errors, function (attribute, errors) {
                        $form.yiiActiveForm('updateAttribute', attribute, errors);
                    });

                    return;
                }

                $form.trigger('ajaxSuccess:ajaxForm', [data, textStatus, jqXHR, $form]);
                $form.customResetForm();
                $form.closest('.modal').modal('hide');
            },
            complete: function (jqXHR, textStatus, $form) {
                var $submitObject = $form.data('submitObject');

                $form.trigger('ajaxComplete:ajaxForm', [jqXHR, textStatus, $form]);
                $submitObject
                    .html($submitObject.data('originalHtml'))
                    .prop('disabled', false)
                    .removeClass('disabled');
            },
            error: function (jqXHR, textStatus, errorThrown, $form) {
                var message;

                if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
                    message = jqXHR.responseJSON.message;
                } else {
                    message = jqXHR.responseText.replace(/(^.*: )?/, '');
                }

                $.alert.configure({message: message}).show();

                $form.trigger('ajaxError:ajaxForm', [jqXHR, textStatus, errorThrown, $form]);
            },
            toggleSubmitObject: function ($submitObject, $form) {
                let method = $submitObject.hasClass('disabled') || $submitObject.prop('disabled')
                    ? 'enableSubmitObject'
                    : 'disableSubmitObject';

                methods[method].apply(this, [$submitObject, $form]);
            },
            disableSubmitObject: function($submitObject, $form) {
                let $loader;

                $submitObject
                    .addClass('disabled')
                    .prop('disabled', true);

                if ($form.data('loader') !== undefined) {
                    $loader = $form.data('loader');
                } else {
                    $loader = $submitObject.find('.loader');

                    if ($loader.length === 0) {
                        $loader = methods.createLoader.apply(this);
                        $submitObject.append($loader);
                    }

                    $form.data('loader', $loader);
                }

                $loader.css('display', 'flex');
            },
            enableSubmitObject: function($submitObject, $form) {
                $submitObject
                    .removeClass('disabled')
                    .prop('disabled', false);

                $form.data('loader').hide();
            },
            createLoader: function() {
                let $loader = $('<div></div>').addClass(settings.loaderClass);

                for (let i = 0; i < settings.loaderItemsCount; i++) {
                    let $loaderItem = $('<div></div>').addClass([
                        settings.loaderItemClass,
                        settings.loaderItemClass + '-' + (i + 1)
                    ]);

                    $loader.append($loaderItem);
                }

                return $('<div></div>')
                    .addClass('loader')
                    .append($loader);
            }
        };

        $.fn.simpleAjaxForm = function(method) {
            if (methods[method]) {
                return methods[method].apply(this, Array.prototype.slice.call( arguments, 1 ));
            } else if (typeof method === 'object' || !method) {
                return methods.init.apply(this, arguments);
            } else {
                $.error('Method with name ' + method + ' does not exist in jQuery.simpleAjaxForm');
            }
        };
    })();

    $(document).on('submit', 'form[ajax-form]', function (event) {
        event.preventDefault();
        $(this).simpleAjaxForm();
    });
})(jQuery, window);
