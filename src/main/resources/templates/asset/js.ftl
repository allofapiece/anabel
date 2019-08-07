<#ftl output_format="HTML" strip_whitespace=true>
<#import "/spring.ftl" as spring />

<#assign imports = (assets['js'])![]>

<#list imports as import>
    <${import.tag} src="${import.src}"></${import.tag}>
</#list>
