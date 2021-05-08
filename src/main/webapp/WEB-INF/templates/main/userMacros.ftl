<#macro head name="Default head name">
    <head>
        <meta charset="UTF-8">
        <title>${name}</title>
    </head>
</#macro>

<#macro newTopicFullPage role="student" pageName="Default page Name">
    <#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
    <!DOCTYPE html>
    <html lang="en">
    <@head name=pageName></@head>
    <body>
    <h1>Create a new topic!</h1>
    <div>
        <@sf.form action="/${role}/add/topic" method="post" modelAttribute="topic">
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
</#macro>

<#macro mainForumFullPage role="student" pageName="Default head name">
    <#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
    <!DOCTYPE html>
    <html lang="en">
    <@head name=pageName></@head>
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
                <td><a href="/${role}/topic/${topic.id}">Enter discussion</a></td>
            </tr>
        </#list>
    </table>
    <a href="/login">Login page</a>
    <a href="/sign_up">Sign up page</a>
    <a href="/logout">You may also logout</a>
    </body>
    </html>
</#macro>

<#macro topicFullPage role="student" pageName="Default head name">
    <!DOCTYPE html>
    <html lang="en">
    <@head name=pageName></@head>
    <body>
    <h1>This is a topic page.</h1>
    <p>If you see this message that means that you have entered a certain topic</p>

    <div>
        <h3>${topic.topic}</h3>
        <p>${topic.description}</p>
    </div>
    <div>
        <p>Post by:</p>
        <p>
            ${topic.user.getFullName()}
        </p>
    </div>

    <div>
        <h5>Comments</h5>
        <table>
            <tr>
                <td>Comment left by:</td>
                <td></td>
                <td>Tags</td>
                <td></td>
            </tr>
            <#list comments as comment>
                <tr>
                    <td>${comment.user.getFullName()}</td>
                    <td>${comment.getText()}</td>
                    <td>
<#--                        <#list comment.getTagNames() as name>-->
<#--                            <p>${name}</p>-->
<#--                        </#list>-->
<#--                        ${comment.getTagNames()}-->
                    </td>
                    <td>
                        <#if likedComments?seq_index_of(comment.getId()) == -1>
                            <div>
                                <p>Not liked</p>
                                <form method="post" action="/${role}/add/like">
                                    <input hidden name="comment" value="${comment.getId()}">
                                    <input type="submit">
                                </form>
                            </div>
                        <#else>
                            <p>Liked</p>
                            <div>
                                <form method="post" action="/${role}/remove/like">
                                    <input hidden name="comment" value="${comment.getId()}">
                                    <input type="submit" value="Unlike">
                                </form>
                            </div>
                        </#if>
                    </td>
                </tr>
            </#list>
        </table>
    </div>

    <div>
        <form action="/${role}/add/comment" method="post">
            <input type="text" name="msg">
            <input hidden="hidden" value="${topic.getId()}" name="topicId">
            <div>
               <#list commentTags as tag>
                   <input type="checkbox" id="${tag.getId()}" value="${tag.getId()}" name="tags">
                   <label for="${tag.getId()}">${tag.getTagName()}</label>
               </#list>
            </div>
            <input type="submit">
        </form>
    </div>
    </body>
    </html>
</#macro>