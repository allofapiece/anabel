<#import "../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />

<@n.page>
    <a data-toggle="modal" href="#modal-add-section" class="btn btn-primary">Add Section</a>
    <div class="row">
        <div class="sections-manage col-lg-4 col-sm-12">
            <ul class="list-group">
                <#list sections as section>
                    <li class="section-manage my-2 list-group-item" data-id="${section.id}">
                        ${section.name}
                        <span class="section-delete">
                        <i class="icon-times"></i>
                    </span>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</@n.page>

<#include "../modal/site-setting-form.ftl">
