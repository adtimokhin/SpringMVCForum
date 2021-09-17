<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restore password</title>
</head>
<body>
<div>
    <#if errors??>
        <p>Some problems occurred when you tried to restore your password:</p>
        <#list errors as error>
            <p>${error}</p>
        </#list>
    </#if>
</div>
<form action="/restore_password/${token}" method="post">
    <div>
        <p>Enter your new password</p>
        <input name="passwordOne" type="password">
    </div>
    <div>
        <p>Enter your new password again</p>
        <input name="passwordTwo" type="password">
    </div>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit">
</form>

</body>
</html>