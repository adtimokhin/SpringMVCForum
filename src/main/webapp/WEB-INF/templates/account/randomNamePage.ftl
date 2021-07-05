<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account. Change name</title>
</head>
<body>
<h1>Random name page</h1>
<div>
    <p>
        How do you like name:<br>${userName.getName()} ${userSurname.getSurname()} ?
    </p>
    <a href="/account/generate_random_name"> Generate another name</a>
    <form method="post" action="/account/save_new_name">
        <p>If you are happy with this name press here:</p>
        <input type="hidden" name="userName" value="${userName.getId()}">
        <input type="hidden" name="userSurname" value="${userSurname.getId()}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Save new name">
    </form>
</div>
</body>
</html>