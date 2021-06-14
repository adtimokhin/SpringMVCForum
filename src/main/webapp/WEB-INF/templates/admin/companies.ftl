<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
</head>

<table>
    <tr>
        <td>Name</td>
        <td>URL</td>
        <td>Email</td>
        <td>Phone</td>
        <td>Location</td>
        <td>
            <#if verifiable == true>
                Verified?
            </#if>
        </td>
    </tr>
    <#list companies as c>
        <tr>
            <td>${c.getName()}</td>
            <td>${c.getWebsiteURL()}</td>
            <td>${c.getEmail()}</td>
            <td>${c.getPhone()}</td>
            <td>${c.getLocation()}</td>
            <td>
                <#if verifiable == true>
                    <#if c.isVerified()>
                        <p>Yes</p>
                    <#else>
                        <p>No</p>
                    </#if>
                    <#else >
                    <div>
                        <form action="/admin/verify/company" method="post">
                            <input type="hidden" value="${c.getId()}" name="id">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                            <input type="submit" value="verify">
                        </form>
                    </div>
                </#if>

            </td>
        </tr>
    </#list>
</table>
</html>

