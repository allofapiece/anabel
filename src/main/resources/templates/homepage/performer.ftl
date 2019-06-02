<#import "/spring.ftl" as s/>
<#import "tag.ftl" as t/>

<#macro performer index>
    <li>
        <a href="#" class="d-flex flex-column align-items-center">
            <div class="account-logo-wrap mx-auto mb-2">
                <div class="account-logo border rounded-circle">
                    <div class="account-logo-thumbnail thumb-container thumb-container-cover rounded-circle">
                        <div class="img-wrap">
                            <img class="bg-white rounded-circle"
                                 src="https://s3.amazonaws.com/mortarr-develop/images/company/cropped/U5ae141e8e7c3cd7199494b52.jpg"
                                 alt="Bruce Knutson Architects">
                        </div>
                    </div>
                </div>
            </div>
            <div class="account-name d-flex flex-column mb-6">
                <h3 class="name-heading">Bruce Knutson Architects</h3>
                <div class="homepage-tags-list justify-content-center mt-2">
                    <#list 1..7 as n>
                        <div class="tag">
                            <div class="tag-body">
                                <p class="tag-name">javascript</p>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
            <button class="btn btn-primary w-100 mt-4 mt-xl-3"><@s.message "homepage.section.second.performer.button"/></button>
        </a>
    </li>
</#macro>
