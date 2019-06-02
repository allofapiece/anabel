(function ($, window) {
    $('.tag:not(a)').on('click', function (event) {
        let link = $(this).data('href');
        $(location).attr('href', link);
    })
})(jQuery, window);
