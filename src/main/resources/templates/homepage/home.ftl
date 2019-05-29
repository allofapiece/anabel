<#import "../layout/landing.ftl" as m>
<#import "/spring.ftl" as s/>

<@m.page>
    <div id="homepage">
        <div class="homepage-banner homepage-banner-first" style="background-image: url('/static/assets/img/homepage/banner-first.jpg')">
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
                                            <@s.message "homepage.banner.first.client.text"/>
                                            <a href="#"><@s.message "homepage.banner.first.client.text2"/></a>.
                                        </blockquote>
                                        <hr>
                                        <a href="javascript:;" role="button" class="btn btn-warning btn-lg btn-block"><@s.message "homepage.banner.first.client.button"/></a>
                                    </div>
                                    <div class="col-12 col-md-6 col-lg-4 banner-info">
                                        <h2 class="display-4"><@s.message "homepage.banner.first.performer.heading"/></h2>
                                        <blockquote class="banner-quote"><@s.message "homepage.banner.first.performer.text"/></blockquote>
                                        <hr>
                                        <a href="javascript:;" role="button" class="btn btn-danger btn-lg btn-block"><@s.message "homepage.banner.first.performer.button"/></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="homepage-section mt-3">
            <div class="container">
                <div class="row">
                    <div class="col-6">
                        <h2 class="display-1"><@s.message "homepage.section.first.heading"/></h2>
                        <p><@s.message "homepage.section.first.text"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@m.page>
