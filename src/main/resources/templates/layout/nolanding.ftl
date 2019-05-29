<#macro page>
    <html>
    <#include "head.ftl">
    <body>
    <div id="app">
        <#include "header.ftl">
        <main class="flex-grow-1">
            <#include "alert.ftl">
            <div class="container mt-3">
                <div class="row">
                    <#nested>
                </div>
                <#--                    <#include "../section/sections-menu.ftl">-->
            </div>

        </main>
        <#include "footer.ftl">
        <#include "scripts.ftl">
    </div>
    </body>
    </html>
</#macro>