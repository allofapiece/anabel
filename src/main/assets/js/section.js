(function ($, window) {
    $(document).ready(function() {
        /*$('#sections-menu').coreNavigation({
            layout: "sidebar",
            responsideSlide: true, // true or false
            dropdownEvent: "accordion",
            mode: 'fixed'
        });*/
    });

    $(document).on('click', '.section-manage .section-delete', function () {
        let $button  = $(this),
            $section = $button.closest('.section-manage'),
            id       = $section.data('id');

        $.get('/admin/sections/delete/' + id, function (data) {
            if (data === 'success') {
                $section.remove();
            }
        })
    });
})(jQuery, window);
