<#include "security.ftl">
<#import "/spring.ftl" as spring>

<header>
        <nav class="navbar navbar-expand-md navbar-light">
            <div class="container">
                <a class="navbar-brand" href="/">Anabel</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-content"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbar-content">
                    <ul class="navbar-nav mr-auto">
                        <#if isAdmin>
                            <li class="nav-item">
                                <a class="nav-link" href="/admin">Admin panel</a>
                            </li>
                        </#if>
                    </ul>
                    <#if user??>
                        <a class="btn btn-primary" href="/profile"><@spring.message "link.account-profile"/></a>
                        <form class="ml-2 mb-0" action="/logout" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-secondary" type="submit"><@spring.message "form.user.logout.label"/></button>
                        </form>
                    <#else>
                        <a class="btn btn-transparent-primary" href="/login"><@spring.message "form.user.sign-in.label"/></a>
                        <a class="btn btn-primary ml-2" href="/signup"><@spring.message "form.user.sign-up.label"/></a>
                    </#if>
                </div>
            </div>

        </nav>

</header>
