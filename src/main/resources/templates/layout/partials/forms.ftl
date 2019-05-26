<#import "/spring.ftl" as spring />

<#macro dto_input entity field type="text">
    <div class="form-group">
        <label><@spring.message "form.${entity}.${field}.label"/></label>
        <@spring.formInput "${entity}Dto.${field}" "class=\"form-control
                ${(spring.status.errors.getFieldErrors(field)?size != 0)?string('is-invalid', '')}\"" "${type}"/>
        <div class="invalid-feedback d-block">
            <@spring.showErrors "" 'display: block;'/>
        </div>
    </div>
</#macro>
