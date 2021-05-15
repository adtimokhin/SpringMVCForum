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
        </tr>
    </#list>
</table>
</html>