<#--users-->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
</head>
<table>
    <tr>
        <td>Name</td>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.getFullName()}</td>
            <td>
                <div>
                    <form action="/admin/update/unblock/user" method="post">
                        <input type="hidden" name="userId" value="${user.getId()}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                        <input type="submit">
                    </form>
                </div>
            </td>
        </tr>
    </#list>
</table>
</html>