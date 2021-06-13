<#import "../main/userMacros.ftl" as macros>
<html>
<@macros.head name="Company Register"></@macros.head>
<body>
<h1>Register your company!</h1>
<#if errors??>
    <div>
        <p>Please, check the following:</p>
        <#list errors as error>
            <p>${error}</p>
        </#list>
    </div>
</#if>
<div>
    <form method="post" action="/company/auth/register">
        <div>
            <label>
                <p>company Name</p>
                <input name="companyName" type="text">
            </label>
            <label>
                <p>website URL</p>
                <input name="websiteURL" type="text">
            </label>
            <label>
                <p>organization's email</p>
                <input name="email" type="email">
            </label>
            <label>
                <p>organization's phone</p>
                <input name="phone" type="text">
            </label>
            <label>
                <p>organization's location</p>
                <input name="location" type="text">
            </label>
            <label>
                <p>how many tokens would you need?</p>
                <input name="token" type="number">
            </label>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <label>
                <input type="submit" value="register your company!">
            </label>
        </div>
    </form>
</div>
</body>
</html>