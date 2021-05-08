<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>sign up page</title>
</head>
<body>

<div>
    <form method="post" action="/sign_up">
        <div>
            <label>Email</label>
            <input type="text" name="email">
        </div>
        <div>
            <label>Password</label>
            <input type="password" name="password">
        </div>
        <div>
            <label>Re-enter your password</label>
            <input type="password" name="secondPassword">
        </div>
        <div>
            <label>Choose your role in this project</label>
            <div>
                <label>
                    <input type="radio" name="role" value="ROLE_STUDENT" checked="checked">
                </label> Student <br>
                <label>
                    <input type="radio" name="role" value="ROLE_PARENT">
                </label> Parent <br>
                <#--                TODO: when the time comes, delete the option of becoming admin that easily.-->
                <label>
                    <input type="radio" name="role" value="ROLE_ADMIN">
                </label> Admin <br>
            </div>
        </div>
        <input type="submit">
    </form>
</div>

</body>
</html>