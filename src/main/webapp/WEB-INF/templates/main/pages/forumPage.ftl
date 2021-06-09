<#import "../userMacros.ftl" as macros>
    <!DOCTYPE html>
    <html lang="en">
    <@macros.head name="Topics"></@macros.head>
    <body>
    <h1>This is the forum page.</h1>
    <table>
        <tr>
            <td>Topic name</td>
            <td>Created by</td>
            <td></td>
        </tr>
        <#list topics as topic>
            <tr>
                <td>${topic.topic}</td>
                <td>${topic.user.getFullName()}</td>
                <td><a href="/topic/${topic.id}">Enter discussion</a></td>
            </tr>
        </#list>
    </table>
    <a href="/login">Login page</a>
    <a href="/sign_up">Sign up page</a>
    <a href="/logout">You may also logout</a>
    </body>
    </html>
