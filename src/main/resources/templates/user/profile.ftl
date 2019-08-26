<#import "../layout/nolanding.ftl" as n>
<#import "/spring.ftl" as spring />
<#import "../layout/security.ftl" as security />

<@n.page>
    <#if tab?has_content && ['general']?seq_contains(tab)>
        <#assign tab = tab />
    <#elseif RequestParameters.tab?has_content && ['general', 'security']?seq_contains(RequestParameters.tab)>
        <#assign tab = RequestParameters.tab />
    <#else>
        <#assign tab = 'general'/>
    </#if>
    <div class="col-12 col-lg-2 d-flex flex-column align-items-end">
        <#if security.currentUserId == user.id >
            <a href="/user/settings" class="btn btn-transparent-primary action">
                <@spring.message "link.account.settings"/>
                <i class="icon-gear"></i>
            </a>
            <a href="/user/edit" class="btn btn-transparent-primary mb-2 action">
                <@spring.message "link.account.edit"/>
                <i class="icon-pencil"></i>
            </a>
        </#if>
    </div>
    <div class="col-12 col-lg-8 mb-2">
        <div class="spot tab-content" id="tab-content">
            <div class="tab-pane fade ${tab?matches('general')?string('show active', '')}" id="general"
                 role="tabpanel" aria-labelledby="general-tab">
                <div class="spot-content">
                    <h3>${user.fullName}</h3>
                    <#if user.about?has_content>
                        <blockquote>${user.about}</blockquote>
                    </#if>
                    <hr/>
                </div>
            </div>
        </div>
    </div>
</@n.page>
