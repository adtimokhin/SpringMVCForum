<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
</head>
<body>
<h1>
    ${reportingUser.getFullName()} (${reportingUser.id}) had reported ${reportedUser.getFullName()} (${reportedUser.id})
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
    <p>Topic ${topic.id} created by user with name: ${topic.getUser().getFullName()}</p>
    <p><u>Topic description:</u></p>
    <p><b>${topic.description}</b></p>
</#if>

<form method="post" action="/admin/update/block/user">
    <p> You want to ban user? (${reportedUser.id})</p>
    <label>
        <input type="text" name="reason">
    </label>
    <input type="hidden" value="${reportId}" name="reportId">
    <input type="submit">
</form>

</body>
</html>