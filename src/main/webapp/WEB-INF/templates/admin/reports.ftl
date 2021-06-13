<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
</head>
<body>
<table>
    <tr>
        <td>comment id</td>
        <td>topic id</td>
        <td>reported user</td>
        <td>reporting user</td>
        <td>cause</td>
        <td></td>

    </tr>
    <#list reports as report>
        <tr>
            <#if report.getComment()??>
                <td>${report.getComment().getId()}</td>
                <td>____</td>
                <#else>
                    <td>____</td>
                    <td>${report.getTopic().getId()}</td>
            </#if>
            <td>${report.getReportedUser().getFullName()}</td>
            <td>${report.getReportingUser().getFullName()}</td>
            <td>${report.cause.title}</td>
            <td>
                <a href="/admin/get/report/${report.id}"> View report details</a>
            </td>
        </tr>
    </#list>
</table>
</body>
</html>