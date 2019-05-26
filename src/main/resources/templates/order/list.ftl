<div class="card-columns">
    <#list orders as order>

        <div class="order card">
            <div class="card-header">
                <h3>${order.title}</h3>
            </div>
            <div class="card-body">
                <p class="order-description">${order.description}</p>
            </div>
            <ul class="list-group list-group-flush">
                <#if order.status == "ACTIVE">
                    <li class="list-group-item list-group-item-success">Order is active.</li>
                </#if>
            </ul>
        </div>
    </#list>
</div>
