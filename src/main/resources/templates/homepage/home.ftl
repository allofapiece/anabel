<#import "../layout/landing.ftl" as m>
<#import "/spring.ftl" as s/>
<#import "tag.ftl" as t/>
<#import "performer.ftl" as p/>

<@m.page>
    <div id="homepage">
        <div class="homepage-banner homepage-banner-first mb-3" style="background-image: url('/static/assets/img/homepage/banner-first.jpg')">
            <div class="homepage-banner-inner">
                <div class="container">
                    <div class="row">
                        <div class="col-12 position-static">
                            <div class="banner-content">
                                <div class="row">
                                    <div class="col-12 col-lg-4">
                                        <h1 class="banner-heading display-1">Anabel</h1>
                                        <blockquote class="banner-quote"><@s.message "homepage.banner.first.general.text"/></blockquote>
                                    </div>
                                    <div class="col-12 col-md-6 col-lg-4 banner-info">
                                        <h2 class="display-4"><@s.message "homepage.banner.first.client.heading"/></h2>
                                        <blockquote class="banner-quote">
                                            <@s.message "homepage.banner.first.client.text"/> <a href="#"><@s.message "homepage.banner.first.client.text2"/></a>.

                                        </blockquote>
                                        <hr class="hr-warning mt-auto">
                                        <a href="javascript:;" role="button" class="btn btn-warning btn-lg btn-block"><@s.message "homepage.banner.first.client.button"/></a>
                                    </div>
                                    <div class="col-12 col-md-6 col-lg-4 banner-info">
                                        <h2 class="display-4"><@s.message "homepage.banner.first.performer.heading"/></h2>
                                        <blockquote class="banner-quote"><@s.message "homepage.banner.first.performer.text"/></blockquote>
                                        <hr class="hr-danger mt-auto">
                                        <a href="javascript:;" role="button" class="btn btn-danger btn-lg btn-block"><@s.message "homepage.banner.first.performer.button"/></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="homepage-section homepage-section-first mb-5">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <h2 class="display-1"><@s.message "homepage.section.first.heading"/></h2>
                    </div>
                </div>
                <div class="row mt-4 flex-row-reverse section-side-text">
                    <div class="col-12 col-lg-5">
                        <h3 class=""><@s.message "homepage.section.first.technology.heading"/></h3>
                        <p class=""><@s.message "homepage.section.first.technology.text"/></p>
                    </div>
                    <div class="col-12 col-lg-7">
                        <div class="homepage-tags-list">
                            <#list 1..5 as n>
                                <@t.tag n/>
                            </#list>
                        </div>
                        <a href="#" class="float-right mt-2"><@s.message "homepage.section.first.link.technology"/></a>
                    </div>
                </div>
                <hr>
                <div class="row mt-3 flex-row-reverse section-side-text">
                    <div class="col-12 col-lg-5">
                        <h3><@s.message "homepage.section.first.establishments.heading"/></h3>
                        <p><@s.message "homepage.section.first.establishments.text"/></p>
                    </div>
                    <div class="col-12 col-lg-7">
                        <div class="homepage-tags-list">
                            <#list 1..5 as n>
                                <@t.tag n/>
                            </#list>
                        </div>
                        <a href="#" class="float-right mt-2"><@s.message "homepage.section.first.link.establishments"/></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="homepage-banner homepage-banner-second" style="background-image: url('/static/assets/img/homepage/banner-second.jpg')">
            <div class="homepage-banner-inner">
                <div class="container">
                    <div class="row">
                        <div class="col-12 position-static">
                            <div class="banner-content">
                                <div class="row">
                                    <div class="col-12 col-md-6 banner-info">
                                        <h2 class="display-4"><@s.message "homepage.banner.second.commitment.heading"/></h2>
                                        <blockquote class="banner-quote">
                                            <@s.message "homepage.banner.second.commitment.text"/>
                                            <span class="badge badge-danger"><@s.message "homepage.banner.second.commitment.text2"/></span> <@s.message "particle.and"/> <@s.message "particle.the"/> <span class="badge badge-primary"><@s.message "homepage.banner.second.commitment.text3"/></span>:
                                            <@s.message "homepage.banner.second.commitment.text4"/>
                                        </blockquote>
                                        <blockquote class="banner-quote"><@s.message "homepage.banner.second.commitment.ps.text"/> <a href="#"><@s.message "homepage.banner.second.commitment.ps.text2"/></a>.</blockquote>
                                    </div>
                                    <div class="col-12 col-md-6 banner-info">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="homepage-section mb-5">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <h2 class="display-1"><@s.message "homepage.section.second.heading"/></h2>
                        <blockquote class="banner-quote"><@s.message "homepage.section.second.text"/>:</blockquote>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="homepage-performers-list mb-2">
                            <ul class="d-flex flex-wrap justify-content-around p-0">
                                <#list 1..4 as n>
                                    <@p.performer n/>
                                </#list>
                            </ul>
                        </div>
                        <a href="#"><@s.message "homepage.section.second.link.performers-search"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@m.page>
