<#ftl output_format="HTML" strip_whitespace=true>

<#import "/spring.ftl" as spring />

<#assign xhtmlCompliant = true in spring>
<#assign fmu = statics['com.pinwheel.anabel.util.FreeMarkerUtils']/>
<#assign iu = statics['com.pinwheel.anabel.util.InflectorUtils']/>

<#macro toJsVar javaVar varName = ''>
    <#if javaVar?is_boolean || javaVar?is_number>
        ${javaVar}
    <#elseif javaVar?is_string>
        '${javaVar}'
    <#elseif javaVar?is_collection>
        [
        <#list javaVar as var>
            <@toJsVar var/>,
        </#list>
        ]
    <#elseif javaVar?is_hash>
        {
        <#list javaVar?keys as key>
            ${key}:${javaVar[key]}<#if key?counter != javaVar?size>,</#if>
        </#list>
        }
    <#else>
        {
        <#assign it = fmu.it(javaVar)/>
        <#list it?keys as key>
            ${key}:<@toJsVar it[key]/><#if key?counter != it?size>,</#if>
        </#list>
        }
    </#if>
</#macro>
