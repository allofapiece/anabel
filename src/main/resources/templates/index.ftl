<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <link href='https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900|Material+Icons'
          rel="stylesheet"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui"/>

    <title>Anabel</title>
</head>
<body>
<div id="app"></div>
<script>
    var alertType
    var alertMessage
    var alertCode
    var alertStrategy
    var alertActionText
    var alertActionLink
    var alertActionType

    <#if alert??>
    alertType = '${alert.type}'
    alertMessage = '${alert.message}'
    alertCode = '${alert.code}'
    alertStrategy = '${alert.strategy}'

    <#if alert.actions?size != 0>
    alertActionText = '${alert.actions[0].text}'
    alertActionLink = '${alert.actions[0].link}'
    alertActionType = '${alert.actions[0].type}'
    </#if>
    </#if>
</script>
<script src="http://localhost:8000/main.js"></script>
</body>
</html>
