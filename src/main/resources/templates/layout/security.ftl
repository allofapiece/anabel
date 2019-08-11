<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        auth = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        email = auth.getUsername()
        displayName = auth.getDisplayName()
        isAdmin = auth.isAdmin()
        currentUserId = auth.getId()
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
        currentUserId = -1
    >
</#if>
