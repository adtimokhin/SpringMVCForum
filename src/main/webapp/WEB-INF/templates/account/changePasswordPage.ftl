<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account. Change password</title>
</head>
<body>
<h1>Changing password page</h1>
<#if error??>
    <p>Couldn't change password. Some errors had occurred:</p>
    <#list error as e>
        <p>${e}</p>
    </#list>
</#if>
<div>
<form method="post" action="/account/change_password">
    <p>Enter your current password:</p>
    <input type="password" name="oldPassword">
    <p>Enter your new password:</p>
    <input type="password" name="newPassword">
    <p>Enter your new password again:</p>
    <input type="password" name="newPasswordRepeated">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="change password">
</form>
</div>
<div>
    <form method="post" action="/restore_password">
        <input type="hidden" name="email" value="${email}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Forgot your password?">
    </form>
</div>
</body>
</html>