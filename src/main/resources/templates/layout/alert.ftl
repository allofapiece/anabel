<#--<#assign flushMessage = Session.flushMessage?then(Session.flushMessage, "")>
<#assign flushStatus = Session.flushStatus?then(Session.flushStatus, flushStatus!"info")>-->

<#if flushMessage?has_content>
    <div class="alert alert-${flushStatus} alert-dismissible fade show" role="alert">
        ${flushMessage}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</#if>