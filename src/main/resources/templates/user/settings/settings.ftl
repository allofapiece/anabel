<#import "../../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />
<#import "../../layout/security.ftl" as security />
<#import "../../macro/bootstrap-form.ftl" as bf />

<@n.page>
    <#if tab?has_content && ['general', 'security']?seq_contains(tab)>
        <#assign tab = tab />
    <#elseif RequestParameters.tab?has_content && ['general', 'security']?seq_contains(RequestParameters.tab)>
        <#assign tab = RequestParameters.tab />
    <#else>
        <#assign tab = 'general'/>
    </#if>
    <div class="col-12">
        <div class="row">
            <div class="col-12 ">
                <nav class="spot" aria-label="breadcrumb">
                    <ol class="breadcrumb mb-0">
                        <li class="breadcrumb-item"><a href="/${security.auth.slug}">${security.displayName}</a></li>
                        <li class="breadcrumb-item active"
                            aria-current="page"><@spring.message "breadcrumb.settings.settings"/></li>
                    </ol>
                </nav>
            </div>
        </div>
        <hr/>
        <div class="row">
            <div class="col-2">
                <div class="spot nav nav-pills flex-column" id="settings-tab" role="tablist"
                     aria-orientation="vertical">
                    <a class="nav-link ${tab?matches('general')?string('active', '')}" id="general-tab" href="#general"
                       data-toggle="pill" role="tab" aria-controls="general" aria-selected="true">
                        <@spring.message "profile.setting.general.tab"/>
                    </a>
                    <a class="nav-link ${tab?matches('security')?string('active', '')}" id="security-tab"
                       href="#security"
                       data-toggle="pill" role="tab" aria-controls="security" aria-selected="true">
                        <@spring.message "profile.setting.security.tab"/>
                    </a>
                </div>
            </div>
            <div class="col-8">
                <div class="spot tab-content" id="tab-content">
                    <div class="tab-pane fade ${tab?matches('general')?string('show active', '')}" id="general"
                         role="tabpanel" aria-labelledby="general-tab">
                        <div class="spot-content">
                            <h3><@spring.message "profile.setting.general.heading"/></h3>
                            <hr/>
                            <div class="mt-4">
                                <#include "slug.ftl"/>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade ${tab?matches('security')?string('show active', '')}" id="security"
                         role="tabpanel" aria-labelledby="security-tab">
                        <div class="spot-content">
                            <h3><@spring.message "profile.setting.security.heading"/></h3>
                            <hr/>
                            <div class="mt-4">
                                <#include "change-password.ftl"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-3">
            </div>
        </div>
    </div>
</@n.page>
