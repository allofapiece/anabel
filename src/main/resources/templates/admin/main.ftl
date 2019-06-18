<#import "../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />

<@n.page>
    <div class="card-columns">
        <#--<div class="card">
            <div class="card-body">
                <h5 class="card-title"><a href="/admin/sections">Sections manager</a></h5>
                <p class="card-text">Manage site sections, that will be displayed for users.</p>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">${sectionsCount} sections now.</li>
            </ul>
        </div>-->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title"><a href="/admin/settings">Settings manager</a></h5>
                <p class="card-text">Manage site settings.</p>
            </div>
        </div>
    </div>
</@n.page>
