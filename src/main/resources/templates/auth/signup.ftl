<#import "../layout/main.ftl" as m>
<#import "/spring.ftl" as spring />

<@m.page>
    <@spring.bind "userDto"/>

    <h3><@spring.message "form.user.heading.create-account"/></h3>

    <form class="mt-4" action="/signup" method="post">
        <div class="form-group">
            <label><@spring.message "form.user.display-name.label"/></label>
            <@spring.formInput "userDto.displayName" "class=\"form-control
                ${(spring.status.errors.getFieldErrors('displayName')?size != 0)?string('is-invalid', '')}\""/>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </div>
        <div class="form-group">
            <label><@spring.message "form.user.email.label"/></label>
            <@spring.formInput "userDto.email" "class=\"form-control
                ${(spring.status.errors.getFieldErrors('email')?size != 0)?string('is-invalid', '')}\"" "email"/>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </div>
        <div class="form-group">
            <label><@spring.message "form.user.password.label"/></label>
            <@spring.formPasswordInput "userDto.password" "class=\"form-control
                ${(spring.status.errors.getFieldErrors('password')?size != 0)?string('is-invalid', '')}\""/>
            <div class="invalid-feedback d-block ">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </div>
        <div class="form-group">
            <label><@spring.message "form.user.confirm-password.label"/></label>
            <@spring.formPasswordInput "userDto.confirmedPassword" "class=\"form-control
                ${(spring.status.errors.getFieldErrors('confirmPassword')?size != 0)?string('is-invalid', '')}\""/>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </div>
        <p class="d-inline"><@spring.message "link.account-already-exist"/> <a
                    href="/login"><@spring.message "form.user.sign-in.label"/>.</a></p>
        <button type="submit" class="btn btn-primary float-right"><@spring.message "form.user.sign-up.label"/></button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</@m.page>
