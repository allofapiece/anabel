<#import "layout/main.ftl" as m>

<@m.page>
    <div>
        Привет
        ${message}
        Перейдите по <a href="${link}"> ссылке</a> для активации вашего аккаунта.
    </div>
</@m.page>