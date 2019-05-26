<#import "../layout/main.ftl" as m>
<#import "../layout/partials/forms.ftl" as forms />
<#import "/spring.ftl" as spring />

<@m.page>
    <h3><@spring.message "form.order.heading.create"/></h3>

    <@spring.bind "orderDto"/>

    <form class="mt-4" action="/orders/new" method="post">
        <@forms.dto_input "order" "title"/>
        <@forms.dto_input "order" "description"/>
        <@forms.dto_input "order" "price" "number"/>

        <button type="submit" class="btn btn-primary float-right"><@spring.message "form.order.sign-up.label"/></button>

        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</@m.page>
