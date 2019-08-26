<#import "/spring.ftl" as spring />
<#import "../../macro/bootstrap-form.ftl" as bf />

<@spring.bind "userChangePasswordDto" />
<form id="change-password-form" action="/user/settings/password" method="post" class="mb-0">
    <@bf.textInput "userChangePasswordDto" "oldPassword" "form.user.old-password.label" "" "password"/>
    <@bf.textInput "userChangePasswordDto" "password" "form.user.new-password.label" "" "password"/>
    <@bf.textInput "userChangePasswordDto" "confirmedPassword" "form.user.confirmed-password.label" "" "password"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <@bf.submitButton "form.user.password.submit.label"/>
</form>
