<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restore password</title>
</head>
<body>
<div>
    <#if error??>
       <p>${error}</p>
    </#if>
</div>
<form action="/restore_password" method="post">
    <div>
        <p>Enter your email</p>
        <input name="email" type="text">
    </div>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit">
</form>
<a href="/sign_up">Sign up?</a>
</body>
</html>