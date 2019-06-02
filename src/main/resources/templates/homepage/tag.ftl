<#import "/spring.ftl" as s/>

<#macro tag index>
    <a class="tag in-row-sm-1 in-row-lg-<#if (index > 1)>2<#else>1</#if> in-row-xl-<#if (index > 2)>3<#else>2</#if>">
        <div class="tag-body">
            <h3 class="tag-name">python</h3>
            <div class="tag-rating"><i class="icon-bolt"></i>233</div>
        </div>
    </a>
</#macro>
