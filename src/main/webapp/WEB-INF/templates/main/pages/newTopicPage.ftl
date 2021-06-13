<#import "../userMacros.ftl" as macros>
<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>
<html lang="en">
<@macros.head name="create a new topic!"></@macros.head>
<body>
<h1>Create a new topic!</h1>
<#if errors??>
    <div>
        <p>Please, check the following:</p>
        <#list errors as error>
            <p>${error}</p>
        </#list>
    </div>
</#if>
<div>
    <form method="post" action="/add/topic">
        <div>
            <p>Please enter a topic name</p>
            <input type="text" name="topic">
        </div>
        <div>
            <p>Tell us more about your problem</p>
            <input type="text" name="description">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div>
            <p>Send it to us!</p>
            <input type="submit">
        </div>
    </form>
</div>
</body>
</html>