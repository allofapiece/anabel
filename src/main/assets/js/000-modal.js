(function ($, window) {
    "use strict";

    $.alert = $.extend($('#modal-alert'), {
        defaults: {
            $relatedTarget: $(),
            message: ''
        },
        configure: function (options) {
            $.extend(true, this, this.defaults);

            if ('object' === typeof options) {
                $.each(options, function (key, value) {
                    if ($.alert.hasOwnProperty(key)) {
                        $.alert[key] = value;
                    }
                });
            }

            this.find('.modal-message').text(this.message);

            return this;
        },
        show: function () {
            return this.modal('show');
        },
        hide: function () {
            return this.modal('hide');
        },
        toggle: function () {
            return this.modal('toggle');
        }
    });

    $.alert.on('show.bs.modal', function (event) {
        var $relatedTarget = $(event.relatedTarget);

        if ($relatedTarget.length) {
            $.alert.configure($.extend($relatedTarget.data(), {
                $relatedTarget: $relatedTarget
            }));
        }
    });

    $.confirm = $.extend($('#modal-confirm'), {
        defaults: {
            $relatedTarget: $(),
            message: '',
            url: '',
            isAjax: false,
            jqXHR: null,
            ajax: {},
            okHandler: null
        },
        configure: function (options) {
            $.extend(true, this, this.defaults);

            if ('object' === typeof options) {
                $.each(options, function (key, value) {
                    if ($.confirm.hasOwnProperty(key)) {
                        $.confirm[key] = value;
                    }
                });
            }

            this.find('.modal-message').text(this.message);
            this.find('.modal-url').attr('href', this.url);

            return this;
        },
        show: function () {
            return this.modal('show');
        },
        hide: function () {
            return this.modal('hide');
        },
        toggle: function () {
            return this.modal('toggle');
        }
    });

    $.confirm.on('show.bs.modal', function (event) {
        var $relatedTarget = $(event.relatedTarget);

        if ($relatedTarget.length) {
            $.confirm.configure($.extend($relatedTarget.data(), {
                $relatedTarget: $relatedTarget
            }));
        }
    });

    $.confirm.on('click', '.modal-url', function (event) {
        if (!$.confirm.isAjax && !$.confirm.okHandler) {
            return;
        }

        event.preventDefault();
        $.confirm.hide();

        if ('function' === typeof $.confirm.okHandler) {
            $.confirm.okHandler.call($.confirm[0], event);
            return;
        }

        if ($.confirm.isAjax) {
            if ($.confirm.jqXHR) {
                return;
            }

            var customEvent = $.Event('beforeSubmit:confirm');
            var options = $.extend(true, {
                url: $.confirm.url,
                error: function (jqXHR, textStatus, errorThrown) {
                    $.confirm.$relatedTarget.trigger('ajaxError:confirm', [jqXHR, textStatus, errorThrown]);
                },
                success: function (data, textStatus, jqXHR) {
                    $.confirm.$relatedTarget.trigger('ajaxSuccess:confirm', [data, textStatus, jqXHR]);
                },
                complete: function (jqXHR, textStatus) {
                    $.confirm.$relatedTarget.trigger('ajaxComplete:confirm', [jqXHR, textStatus]);
                }
            }, $.confirm.ajax);

            $.confirm.$relatedTarget.trigger(customEvent, [options]);

            if (customEvent.isDefaultPrevented()) {
                return false;
            }

            $.confirm.jqXHR = $.ajax(options);
        }
    });

    $(document).on('shown.bs.modal', '.modal', function (event) {
        var $relatedTarget = $(event.relatedTarget);
        var $body = $('body');
        var paddingRight = parseFloat($body.css('paddingRight'));

        $body.data('paddingRight', paddingRight);
        $(this).css({
            paddingRight: paddingRight || ''
        });

        // Load fluid data to form
        $(this).find('[fluid-field]').each(function () {
            var value = $relatedTarget.data($(this).attr('fluid-field'));

            if (undefined !== value) {
                $(this).data('originalValue', $(this).val()).val(value);
            }
        });
    });

    $(document).on('hidden.bs.modal', '.modal', function (event) {
        var $body = $('body');
        var modalOpen = !!$('.modal.show').length;

        $body.toggleClass('modal-open', modalOpen).css({
            paddingRight: modalOpen ? $body.data('paddingRight') : ''
        });

        // Reset fluid data in form fields
        $(this).find('[fluid-field]').each(function () {
            $(this).val($(this).data('originalValue'));
        });
    });
})(jQuery, window);
