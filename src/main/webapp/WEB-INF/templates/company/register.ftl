<#import "../main/userMacros.ftl" as macros>
<html>
<@macros.head name="Company Register"></@macros.head>
<body>
<h1>Register your company!</h1>
<div>
    <form method="post" action="/company/auth/register">
        <div>
            <label>
                <input name="companyName" type="text">
            </label>
            <label>
                <input name="websiteURL" type="text">
            </label>
            <label>
                <input name="email" type="email">
            </label>
            <label>
                <input name="phone" type="text">
            </label>
            <label>
                <input name="location" type="text">
            </label>
            <label>
                <input name="token" type="number">
            </label>
            <label>
                <input type="submit" value="register your company!">
            </label>
        </div>
    </form>
</div>
</body>
</html>