<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
<div>
    <#if error??>
        <#if error == "User banned">
            <p>You have been banned from using the forum.</p>
        <#else>
            <#if error == "Unverified email">
                <p>You didn't verify your email yet</p>
            <#else>
                <p>Cannot find user with this credentials.</p>
            </#if>
        </#if>
    </#if>
</div>
<form action="/login/process" method="post">
    <div>
        <p>Enter your email</p>
        <input name="email" type="text">
    </div>
    <div>
        <p>Enter your password</p>
        <input name="password" type="password">
    </div>
    <div>
        <p>Remember me?</p>
        <input type="checkbox" name="remember-me"/>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit">
</form>
<a href="/restore_password">I forgot my password</a>
<a href="/sign_up">Sign up?</a>
</body>
</html>