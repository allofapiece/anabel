<#ftl output_format="HTML" strip_whitespace=true>
<#import "/spring.ftl" as spring />

<#assign imports = (assets['css'])![]>

<#list imports as import>
    <${import.tag} rel="${import.rel}" href="${import.href}"/>
</#list>