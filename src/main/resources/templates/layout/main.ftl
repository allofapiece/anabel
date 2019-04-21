<#macro page>
<html>
    <#include "head.ftl">
    <body>
    <div id="app">
        <#include "header.ftl">
        <main class="flex-grow-1">
            <#include "alert.ftl">

            <div class="container-fluid mt-3">
                <div class="row">
                    <div class="offset-2 col-3">
                        <#nested>
                    </div>
                </div>
            </div>
        </main>
        <#include "footer.ftl">
        <#include "scripts.ftl">
    </div>
    </body>
</html>
</#macro>
