<#import "../userMacros.ftl" as macros>
<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>
<html lang="en">
<@macros.head name="create a new topic!"></@macros.head>
<body>
<h1>Create a new topic!</h1>
<div>
    <@sf.form action="/add/topic" method="post" modelAttribute="topic">
        <div>
            <p>Please enter a topic name</p>
            <@sf.input path="topic"/>
            <@sf.errors path="topic"/>
        </div>
        <div>
            <p>Tell us more about your problem</p>
            <@sf.input path="description"/>
            <@sf.errors path="description"/>
        </div>
        <div>
            <p>Send it to us!</p>
            <input type="submit">
        </div>
    </@sf.form>
</div>
</body>
</html>