<#import "../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />

<@n.page>
    <div class="offset-4 col-4">
        <h3><@spring.message "form.user.heading.sign-in"/></h3>
        <form class="mt-4" action="/login" method="post">
            <div class="form-group">
                <#if authError??>
                    <div class="invalid-feedback d-block">
                        ${authError}
                    </div>
                </#if>
                <label for="email"><@spring.message "form.user.email.label"/></label>
                <input type="email" class="form-control" name="email" id="email"/>
            </div>
            <div class="form-group">
                <label for="password"><@spring.message "form.user.password.label"/></label>
                <input type="password" class="form-control" name="password" id="password">
            </div>
            <p class="d-inline"><@spring.message "link.account-not-exist-yet"/> <a
                        href="/signup"><@spring.message "form.user.sign-up.label"/>.</a></p>
            <button type="submit"
                    class="btn btn-primary float-right"><@spring.message "form.user.sign-in.label"/></button>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
</@n.page>
