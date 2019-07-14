<#import "../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />
<#import "../macro/bootstrap-form.ftl" as bf>

<@n.page>
    <@spring.bind "userDto"/>

    <div class="offset-4 col-4">
        <h3><@spring.message "form.user.heading.create-account"/></h3>

        <form class="mt-4" action="/signup" method="post">
            <@bf.textInput "userDto" "displayName" "form.user.display-name.label"/>
            <@bf.textInput "userDto" "email" "form.user.email.label" "" "email"/>
            <@bf.textInput "userDto" "password" "form.user.password.label" "" "password"/>
            <@bf.textInput "userDto" "confirmedPassword" "form.user.confirm-password.label" "" "password"/>
            <p class="d-inline"><@spring.message "link.account-already-exist"/> <a
                        href="/login"><@spring.message "form.user.sign-in.label"/>.</a></p>
            <button type="submit"
                    class="btn btn-primary float-right"><@spring.message "form.user.sign-up.label"/></button>

            <div class="form-group mt-4">
                <div class="g-recaptcha" data-sitekey="6LdjSaAUAAAAAP8N6rg9BPuveC6gvwCf_7G6VH7w"></div>
                <#if captchaError??>
                    <div class="invalid-feedback d-block">
                        <@spring.message "${captchaError}"/>
                    </div>
                </#if>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </div>
</@n.page>
