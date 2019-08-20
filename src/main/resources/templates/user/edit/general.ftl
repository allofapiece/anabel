<#import "/spring.ftl" as spring />
<#import "../../macro/bootstrap-form.ftl" as bf />

<@spring.bind "userEditGeneralDto" />
<form id="general-form" action="/user/edit/general" method="post" class="mb-0">
    <@bf.textInput "userEditGeneralDto" "displayName" "form.user.display-name.label"/>
    <@bf.textInput "userEditGeneralDto" "firstName" "form.user.first-name.label"/>
    <@bf.textInput "userEditGeneralDto" "lastName" "form.user.last-name.label"/>
    <@bf.textInput "userEditGeneralDto" "about" "form.user.about.label"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <@bf.submitButton "form.submit.label"/>
</form>
