<#macro page>
<html>
    <#include "head.ftl">
    <body>
    <div id="app">
        <#include "header.ftl">
        <main class="flex-grow-1">
            <#nested>
        </main>
        <#include "footer.ftl">
        <#include "scripts.ftl">
    </div>
    </body>
</html>
</#macro>
