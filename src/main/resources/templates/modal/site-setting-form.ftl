<#import "/spring.ftl" as spring>

<div class="modal" id="modal-add-site-setting" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><@spring.message "form.section.heading.create"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/admin/settings" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name"><@spring.message "form.section.name.label"/></label>
                        <input type="text" class="form-control" name="name" id="name">
                    </div>
                    <@spring.formSingleSelect path=

                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><@spring.message "form.section.cancel.label"/></button>
                    <button type="submit" class="btn btn-primary"><@spring.message "form.section.add.label"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
