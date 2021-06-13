<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>sign up page</title>
</head>
<body>

<div>
    <#if errors??>
        <div>
            <p>Please, check the following:</p>
            <#list errors as error>
                <p>${error}</p>
            </#list>
        </div>
    </#if>

    <form method="post" action="/admin/create/admin">
        <div>
            <label>Email</label>

            <input type="text" name="email"
                    <#if email??>
                        value="${email}"
                    </#if>
            >
        </div>
        <div>
            <label>Password</label>
            <input type="password" name="password">
        </div>
        <div>
            <label>Re-enter your password</label>
            <input type="password" name="secondPassword">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit">
    </form>
</div>

</body>
</html>