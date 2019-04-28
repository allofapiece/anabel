(function ($, window) {
    $(document).on('click', '.langs .lang', function () {
        let lang = $(this).data('lang');

        $.get('/lang', {lang: lang}, function (data, status) {
            if (status === "success") {
                window.location.href = window.location.href
            }
        });
    });
})(jQuery, window);
