<#if flushMessage?has_content>
    <div class="alert alert-${flushStatus} alert-dismissible fade show" role="alert">
        <div class="container">
            <div class="row">
                <div class="col-12 d-flex">
                    ${flushMessage}
                    <button type="button" class="close ml-auto" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</#if>
