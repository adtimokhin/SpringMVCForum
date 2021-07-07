<#import "../userMacros.ftl" as macros>
<!DOCTYPE html>
<html lang="en">
<@macros.head name="Topics"></@macros.head>
<body>
<h1>This is the forum page.</h1>
<table>
    <tr>
        <td>Topic name</td>
        <td>Created by</td>
        <td></td>
    </tr>
    <#list topics as topic>
        <tr>
            <td>${topic.topic}</td>
            <td>${topic.user.getFullName()} ${topic.user.getRatingStatus().getName()}</td>
            <td><a href="/topic/${topic.id}">Enter discussion</a></td>
        </tr>
    </#list>
</table>
<a href="/login">Login page</a>
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Logout">
</form>
</body>
</html>
