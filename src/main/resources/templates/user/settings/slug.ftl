<#import "../../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />
<#import "../../macro/bootstrap-form.ftl" as bf />

<@spring.bind "userSlugDto" />
<form id="slug-form" action="/user/settings/slug" method="post" class="mb-0">
    <div class="form-group">
        <@spring.bind "userSlugDto.slug"/>
        <label><@spring.message "form.user-slug.slug.label"/></label>
        <div class="input-group">
            <div class="input-group-prepend">
                <div class="input-group-text">https://www.anabel.com/</div>
            </div>
            <input type="text" id="${spring.status.expression?replace('[','')?replace(']','')}"
                   name="${spring.status.expression}"
                   placeholder="<@bf.placeholder "user" "slug" "text-input"/>"
                   value="${spring.stringStatusValue}"
                   class="form-control ${spring.status.errors.getFieldErrors("slug")?has_content?string('is-invalid', '')}"
                   uq="true"
                   data-uq-url="/user/settings/slug/verify"
                   data-rule-rangelength="4,32"
                   data-rule-pattern="[a-zA-Z0-9-]*"
                   data-rule-required="true"
                   data-msg-pattern="<@spring.message "form.user-slug.slug.error.format"/>"
                   data-msg-remote="<@spring.message "form.user-slug.slug.error.taken"/>"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>
    <div class="d-flex flex-wrap">
        <@bf.submitButton "form.user-slug.submit.label"/>
        <div class="invalid-feedback uq-err-container">
            <#if spring.status.errorMessages?has_content>
                <#list spring.status.errorMessages as error>
                    <li>
                        <label id="slug-error" class="is-invalid" for="slug>">${error}</label>
                    </li>
                </#list>
            </#if>
        </div>
    </div>
</form>
