<#import "../userMacros.ftl" as macros>

<!DOCTYPE html>
<html lang="en">
<@macros.head name="${topic.topic}"></@macros.head>
<body>
<h1>This is a topic page.</h1>
<p>If you see this message that means that you have entered a certain topic</p>
<#if theCreator == true>
    <#if closed == true>
        <div>
            <form action="/update/topic/open" method="post">
                <input type="hidden" name="topicId" value="${topic.id}">
                <input type="submit" value="Open the discussion again.">
            </form>
        </div>
    <#else>
        <div>
            <form action="/update/topic/close" method="post">
                <input type="hidden" name="topicId" value="${topic.id}">
                <input type="submit" value="Close the discussion">
            </form>
        </div>
    </#if>
</#if>
<div>
    <h3>${topic.topic}</h3>
    <p>${topic.description}</p>
</div>
<div>
    <p>Post by:</p>
    <p>
        ${topic.user.getFullName()}
    </p>
    <div>
        <form method="post" action="/add/report">
            <input type="hidden" name="commentOrTopicId" value="${topic.getId()}">
            <input type="hidden" name="isComment" value="false">
            <input type="hidden" name="reportedUserId" value="${topic.getUser().getId()}">
            <input type="hidden" name="causeId" value="1">
            <input type="submit" value="Report">
        </form>
    </div>
</div>

<div>
    <h5>Comments</h5>
    <#if flaggedComments??>
        <p>Flagged Comments</p>
        <@macros.commentSectionNoRole comments = flaggedComments flaggable=false closed=closed ></@macros.commentSectionNoRole>
        <p>All Comments:</p>
    </#if>
    <@macros.commentSectionNoRole comments=comments flaggable=true closed=closed ></@macros.commentSectionNoRole>
</div>
<#if closed==false>
    <div>
        <form action="/add/comment" method="post">
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
</#if>
</body>
</html>