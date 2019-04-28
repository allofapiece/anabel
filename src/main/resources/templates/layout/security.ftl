<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        email = user.getUsername()
        displayName = user.getDisplayName()
        isAdmin = user.isAdmin()
        currentUserId = user.getId()
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
        currentUserId = -1
    >
</#if>
