<#import "../../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />
<#import "../../layout/security.ftl" as security />

<@n.page>
    <#if tab?has_content && ['general']?seq_contains(tab)>
        <#assign tab = tab />
    <#elseif RequestParameters.tab?has_content && ['general', 'security']?seq_contains(RequestParameters.tab)>
        <#assign tab = RequestParameters.tab />
    <#else>
        <#assign tab = 'general'/>
    </#if>
    <div class="col-2 d-flex flex-column align-items-end">
        <div class="spot nav nav-pills flex-column w-100" id="settings-tab" role="tablist"
             aria-orientation="vertical">
            <a class="nav-link ${tab?matches('general')?string('active', '')}" id="general-tab" href="#general"
               data-toggle="pill" role="tab" aria-controls="general" aria-selected="true">
                <@spring.message "profile.edit.general.tab"/>
            </a>
        </div>
    </div>
    <div class="col-8">
        <div class="spot tab-content" id="tab-content">
            <div class="tab-pane fade ${tab?matches('general')?string('show active', '')}" id="general"
                 role="tabpanel" aria-labelledby="general-tab">
                <div class="spot-content">
                    <h3><@spring.message "profile.edit.general.heading"/></h3>
                    <hr/>
                    <div class="mt-4">
                        <#include "general.ftl"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@n.page>
