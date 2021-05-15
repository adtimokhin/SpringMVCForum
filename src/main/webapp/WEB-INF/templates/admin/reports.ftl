<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
</head>
<table>
    <tr>
        <td>comment id</td>
        <td>topic id</td>
        <td>reported user</td>
        <td>reporting user</td>
        <td>cause</td>
        <td>_____________</td>

    </tr>
    <#list reports as report>
        <tr>
            <td>${report.getComment().getId()}</td>
            <td>____</td>
            <td>${report.getReportedUser().getFullName()}</td>
            <td>${report.getReportingUser().getFullName()}</td>
            <td>${report.cause_id}</td>
            <td>Later this will take to the detailed analyse page</td>
        </tr>
    </#list>
</table>
</html>