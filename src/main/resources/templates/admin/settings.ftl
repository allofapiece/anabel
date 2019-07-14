<#import "../layout/nolanding.ftl" as n>
<#import "../layout/pager.ftl" as p>
<#import "/spring.ftl" as spring />

<@n.page>
    <div class="col-12 mb-4">
        <h1>Settings</h1>
    </div>
    <div class="col-12">
        <a class="btn btn-primary" href="/admin/settings/form">Add Setting</a>
    </div>
    <div class="col-12 mt-3">
        <#if page.hasContent()>
            <table class="table table-responsive">
                <tr>
                    <th>id</th>
                    <th>key</th>
                    <th>type</th>
                    <th>value</th>
                    <th>status</th>
                    <th>created at</th>
                    <th>updated at</th>
                    <th>actions</th>
                </tr>
                <#list page.content as siteSetting>
                    <tr>
                        <td>${siteSetting.id}</td>
                        <td>${siteSetting.key}</td>
                        <td>${siteSetting.type.option}</td>
                        <td>${siteSetting.value}</td>
                        <td>${siteSetting.status}</td>
                        <td>${siteSetting.createdAt}</td>
                        <td>${siteSetting.updatedAt}</td>
                        <td>
                            <a href="/admin/settings/form?id=${siteSetting.id}"><i class="icon-pencil"></i></a>
                            <a href="/admin/settings/delete/${siteSetting.id}"><i class="icon-trash"></i></a>
                        </td>
                    </tr>
                <#else>
                    No settings
                </#list>
            </table>
        <#else>
            <p>No settings yet.</p>
        </#if>
    </div>
    <div class="col-12 mt-3">
        <@p.pager page url />
    </div>
</@n.page>
