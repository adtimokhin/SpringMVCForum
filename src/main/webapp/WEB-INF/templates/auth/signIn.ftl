<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
<#if error??>
    <p>Cannot find user with this credentials</p>
</#if>
<form action="/login/process" method="post">
    <div>
        <p>Enter email or phone number</p>
        <input name="email" type="text">
    </div>
    <div>
        <p>Enter your password</p>
        <input name="password" type="password">
    </div>
    <input type="submit">
</form>
<a href="/sign_up">Sign up?</a>
</body>
</html>