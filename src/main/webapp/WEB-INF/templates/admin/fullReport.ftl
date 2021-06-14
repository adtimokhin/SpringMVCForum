<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
</head>
<body>
<h1>
    ${reportingUser.getFullName()} (id:${reportingUser.id}) had reported ${reportedUser.getFullName()}
    (id:${reportedUser.id})
</h1>

<p>
    Report cause:<br>
</p>
<p>
    ${cause.title}
</p>
<h3>Source reported:</h3>
<#if comment ??>
    <p>Comment ${comment.id} left by user with name: ${comment.getUser().getFullName()}</p>
    <p><u>Comment text:</u></p>
    <p><b>${comment.getText()}</b></p>
<#else>
    <#if topic??>
        <p>Topic ${topic.id} created by user with name: ${topic.getUser().getFullName()}</p>
        <p><u>Topic description:</u></p>
        <p><b>${topic.description}</b></p>
    <#else>
        <p> Answer ${answer.getId()} created by user with name ${answer.getUser().getFullName()}</p>
        <p><u>Answer text:</u></p>
        <p><b>${answer.getText()}</b></p>
    </#if>
</#if>

<form method="post" action="/admin/update/block/user">
    <p> You want to ban user? (${reportedUser.id})</p>
    <label>
        <input type="text" name="reason">
    </label>
    <input type="hidden" value="${reportId}" name="reportId">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <input type="submit">
</form>
<h1>OR</h1>
<form method="post" action="/admin/update/dismiss/report">
    <p> You want to dismiss this report? (${reportedUser.id})</p>
    <label>
        <input type="text" name="reason">
    </label>
    <input type="hidden" value="${reportId}" name="reportId">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <input type="submit">
</form>

</body>
</html>