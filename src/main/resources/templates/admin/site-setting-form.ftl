<#import "../layout/nolanding.ftl" as n>
<#import "../macro/bootstrap-form.ftl" as bf>
<#import "/spring.ftl" as spring />

<@n.page>
    <@spring.bind "siteSettingDto"/>
    <div class="col-5">
        <h3>Site Setting</h3>
        <form action="/admin/settings/form" method="post">
            <@bf.textInput "siteSettingDto" "key" "form.site-setting.key.label"/>
            <@bf.textInput "siteSettingDto" "value" "form.site-setting.value.label"/>
            <@bf.singleSelect "siteSettingDto" "type" "form.site-setting.type.label" siteSettingTypeOptions/>
            <@bf.singleSelect "siteSettingDto" "status" "form.site-setting.status.label" siteSettingStatusOptions/>
            <@bf.identifier "siteSettingDto" "id"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <@bf.submitButton "Save"/>
        </form>
    </div>
</@n.page>
