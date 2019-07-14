<#ftl output_format="HTML" strip_whitespace=true>

<#import "/spring.ftl" as spring />

<#assign xhtmlCompliant = true in spring>
<#assign fmu = statics['com.pinwheel.anabel.util.FreeMarkerUtils']/>
<#assign iu = statics['com.pinwheel.anabel.util.InflectorUtils']/>

<#-- This file contains form-related macros for use in the other Freemarker template files.
     The generated HTML is intended for use with Twitter Bootstrap 4 based forms. Refactored implementation of
     https://gist.github.com/jkuipers/5658672 version. -->


<#--
 * radioButtons
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param options a map (value=label) of all the available options.
 * @param checkId does path refer to an entity and should the selected check be done based on that entity's id (defaults to false).
-->
<#macro radioButtons entity property messageKey options checkId=false>
    <@spring.bind "${entity}.${property}"/>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
    <div class="form-group ${error}">
        <#-- use id for selected check if property is an entity -->
        <#if checkId && spring.status.value??><#assign stringStatusValue=spring.status.actualValue.id?string in spring></#if>
        <legend class="col-form-label"><#if messageKey?index_of(' ') != -1>${messageKey}<#else><@spring.message messageKey/></#if></legend>
        <#list options?keys as value>
            <#assign id="${spring.status.expression?replace('[','')?replace(']','')}${value_index}">
            <input type="radio" id="${id}" class="form-check-input" name="${spring.status.expression}"
                   value="${value}"<#if spring.stringStatusValue == value?string> checked</#if>/>
            <label class="form-check-label" for="${id}">${options[value]}</label>
        </#list>
        <#if error?has_content>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </#if>
    </div>
</#macro>

<#--
 * textInput
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param initAttributes any additional attributes for the element (such as class or CSS styles or size). Passed
 *        attributes will be extended by default attributes such as `form-controll`, `is-invalid` classes via
          addClass method of FreeMarkerUtils.
 * @param type input type: defaults to "text".
-->
<#macro textInput entity property messageKey initAttributes="" type="text">
    <@spring.bind "${entity}.${property}"/>

    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
    <#assign resAttrs = fmu.addClass(initAttributes, "form-control")/>

    <#if spring.status.errors.getFieldErrors(property)?has_content>
        <#assign resAttrs = fmu.addClass(resAttrs, "is-invalid")/>
    </#if>

    <div class="form-group">
        <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
        <label for="${id}"><#if messageKey?index_of(' ') != -1>${messageKey}<#else><@spring.message messageKey/></#if></label>
        <input type="${type}" id="${spring.status.expression?replace('[','')?replace(']','')}"
               name="${spring.status.expression}"
               placeholder="<@placeholder entity property "text-input"/>"
               value="<#if type!="password">${spring.stringStatusValue}</#if>" ${resAttrs?no_esc}<@spring.closeTag/>
        <#if error?has_content>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </#if>
        <#nested>
    </div>
</#macro>

<#--
 * textArea
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param initAttributes any additional attributes for the element (such as class or CSS styles or size). Passed
 *        attributes will be extended by default attributes such as `form-controll`, `is-invalid` classes via
          addClass method of FreeMarkerUtils.
-->
<#macro textArea entity property messageKey initAttributes="">
    <@spring.bind "${entity}.${property}"/>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
    <#assign attributes = fmu.addClass(initAttributes, "form-control")/>

    <div class="form-group ${error}">
        <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
        <label for="${id}"><#if messageKey?index_of(' ') != -1>${messageKey}<#else><@spring.message messageKey/></#if></label>
        <textarea id="${id}" name="${spring.status.expression}" ${attributes}
                  placeholder="<@placeholder entity property "text-area"/>">
            ${spring.stringStatusValue}
        </textArea>
        <#if error?has_content>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </#if>
    </div>
</#macro>

<#--
 * singleSelect
 *
 * Show a selectbox (dropdown) input element allowing a single value to be chosen
 * from a list of options.
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param options a map (value=label) or list (value) of all the available options.
 * @param initAttributes any additional attributes for the element (such as class or CSS styles or size). Passed
 *        attributes will be extended by default attributes such as `form-controll`, `is-invalid` classes via
          addClass method of FreeMarkerUtils.
 * @param checkId does path refer to an entity and should the selected check be done based on that entity's id (defaults to false).
 * @param emptyOption should an empty first option be inserted (defaults to false); an empty option is also created when an empy
                      string makes up the value of the option.
-->
<#macro singleSelect entity property messageKey options initAttributes="" checkId=false emptyOption=false>
    <@spring.bind "${entity}.${property}"/>

<#-- use id for selected check if property is an entity -->
    <#if checkId && spring.status.value??><#assign stringStatusValue=spring.status.actualValue.id?string in spring></#if>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
    <#assign attributes = fmu.addClass(initAttributes, "form-control")/>

    <#if spring.status.errors.getFieldErrors(property)?has_content>
        <#assign attributes = fmu.addClass(attributes, "is-invalid")/>
    </#if>

    <div class="form-group">
        <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
        <label for="${id}"><#if messageKey?index_of(' ') != -1>${messageKey}<#else><@spring.message messageKey/></#if></label>
        <select id="${id}" name="${spring.status.expression}" ${attributes?no_esc}>
            <#if emptyOption>
                <option value=""><@placeholder entity property "single-select"/></option>
            </#if>
            <#if options?is_hash>
                <#list options?keys as value>
                    <#assign label><#if value = ""><@spring.message "form.select.placeholder"/><#else>${options[value]}</#if></#assign>
                    <option value="${value}" <@spring.checkSelected value/>>${label}</option>
                </#list>
            <#else>
                <#list options as value>
                    <#assign label><#if value = ""><@spring.message "form.select.placeholder"/><#else>${value}</#if></#assign>
                    <option value="${value}" <@spring.checkSelected value/>>${label}</option>
                </#list>
            </#if>
        </select>
        <#if error?has_content>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </#if>
    </div>
</#macro>

<#--
 * multiSelect
 *
 * Show a selectbox (dropdown) input element allowing a single value to be chosen
 * from a list of options.
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param options a map (value=label) or list (value) of all the available options.
 * @param initAttributes any additional attributes for the element (such as class or CSS styles or size). Passed
 *        attributes will be extended by default attributes such as `form-controll`, `is-invalid` classes via
          addClass method of FreeMarkerUtils.
-->
<#macro multiSelect entity property messageKey options initAttributes="">
    <@spring.bind "${entity}.${property}"/>

<#-- use id for selected check if property is an entity -->
    <#if checkId && spring.status.value??><#assign stringStatusValue=spring.status.actualValue.id?string in spring></#if>
    <#assign error><#if spring.status.errorMessages?has_content>error</#if></#assign>
    <#assign attributes = fmu.addClass(initAttributes, "form-control")/>

    <#if spring.status.errors.getFieldErrors(property)?has_content>
        <#assign attributes = fmu.addClass(attributes, "is-invalid")/>
    </#if>

    <div class="form-group">
        <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
        <label for="${id}"><#if messageKey?index_of(' ') != -1>${messageKey}<#else><@spring.message messageKey/></#if></label>
        <select multiple="multiple" id="${id}" name="${spring.status.expression}" ${attributes?no_esc}>
            <#list options?keys as value>
                <#assign isSelected = spring.contains(spring.status.actualValue?default([""]), value)>
                <option value="${value}"<#if isSelected> selected="selected"</#if>>${options[value]}</option>
            </#list>
        </select>
        <#if error?has_content>
            <div class="invalid-feedback d-block">
                <@spring.showErrors "" 'display: block;'/>
            </div>
        </#if>
    </div>
</#macro>

<#--
 * checkboxes
 *
 * Show checkboxes.
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param options a map (value=label) of all the available options.
 * @param initAttributes any additional attributes for the element (such as class or CSS styles or size). Passed
 *        attributes will be extended by default attributes such as `form-controll`, `is-invalid` classes via
          addClass method of FreeMarkerUtils.
-->
<#macro checkboxes entity property options initAttributes="">
    <@spring.bind "${entity}.${property}"/>
    <div class="form-group">
        <#list options?keys as value>
            <#assign id="${spring.status.expression?replace('[','')?replace(']','')}${value_index}">
            <#assign isSelected = spring.contains(spring.status.actualValue?default([""]), value)>
            <div class="form-check">
                <input type="checkbox" id="${id}" name="${spring.status.expression}"
                       value="${value}"<#if isSelected> checked="checked"</#if> ${initAttributes}/>
                <label for="${id}">${options[value]}</label>
                <input type="hidden" name="_${spring.status.expression}" value="on"/>
            </div>
        </#list>
    </div>
    <#if error?has_content>
        <div class="invalid-feedback d-block">
            <@spring.showErrors "" 'display: block;'/>
        </div>
    </#if>
</#macro>

<#--
 * submitButton
 *
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param styleClass specify type of the bootstrap button (such as primary, success and etc).
 * @param initAttributes any additional attributes for the element (such as class or CSS styles or size). Passed
 *        attributes will be extended by default attributes such as `btn` class via addClass method of FreeMarkerUtils.
-->
<#macro submitButton messageKey styleClass='primary' initAttributes=''>
    <#assign attrs = fmu.addClass(initAttributes, "btn")/>
    <#if styleClass?has_content>
        <#assign attrs = fmu.addClass(attrs, "btn-${styleClass}")/>
    </#if>
    <button type="submit" ${attrs?no_esc}>
        <#if messageKey?index_of(' ') != -1 || messageKey?index_of('.') == -1>
            ${messageKey}
        <#else>
            <@spring.messageText messageKey springMacroRequestContext.getMessage("form.submit.label")/>
        </#if>
    </button>
</#macro>

<#--
 * checkbox
 *
 * Show a single checkbox.
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
 * @param messageKey key to lookup in resource bundle. If messageKey is simple string, it will be used as it is.
 * @param attributes any additional attributes for the element (such as class or CSS styles or size).
-->
<#macro checkbox entity property messageKey attributes="">
    <@spring.bind "${entity}.${property}" />
    <#assign id="${spring.status.expression?replace('[','')?replace(']','')}">
    <#assign isSelected = spring.status.value?? && spring.status.value?string=="true">
    <div class="form-group">
        <input type="checkbox" id="${id}" name="${id}"<#if isSelected> checked="checked"</#if> ${attributes}/>
        <label for="${id}"><#if messageKey?index_of(' ') != -1>${messageKey}<#else><@spring.message messageKey/></#if></label>
        <input type="hidden" name="_${id}" value="on"/>
    </div>
</#macro>

<#--
 * identifier
 *
 * Hidden input for specifying of entity id.
 *
 * @param entity the name of the entity to bind to. Entity and property names will be concatenated for binding.
 * @param property the name of the field of entity to bind to. Entity and property names will be concatenated for binding.
-->
<#macro identifier entity property>
    <@spring.bind "${entity}.${property}" />
    <input type="hidden" name="id" value="${spring.stringStatusValue}"/>
</#macro>

<#--
 * placeholder
 *
 * Macro for calculating of placeholder.
 *
 * @param entity used for defining message key in resource bundle.
 * @param property used for defining message key in resource bundle.
 * @param type type of bootstrap input, such as (single-select, multi-select and etc). Used for default placeholder.
-->
<#macro placeholder entity property type>
    <@spring.messageText "form.${iu.camel2Hyphen((entity?index_of('Dto') == -1)?string(entity, entity?replace('Dto', '')))}.${iu.camel2Hyphen(property)}.placeholder"
    springMacroRequestContext.getMessage("form.${type}.placeholder")/>
</#macro>
