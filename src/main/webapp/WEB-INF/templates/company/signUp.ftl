<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>sign up page</title>
</head>
<body>

<div>
    <form method="post" action="/company/auth/sign_up">
        <div>
            <label>What is your first name?</label>
            <input type="text" name="first_name">
        </div>
        <div>
            <label>What is your last name?</label>
            <input type="text" name="last_name">
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
            <label>Email</label>
            <input type="text" name="email">
        </div>
        <div>
            <label>What is a phone number of your organization?</label>
            <input type="text" name="phone">
        </div>
        <div>
            <label>Token</label>
            <input type="text" name="token">
        </div>
        <div>

        </div>
        <input type="submit">
    </form>
</div>

</body>
</html>
