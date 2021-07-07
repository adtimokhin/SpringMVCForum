<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
</head>
<body>
<h1>Account page</h1>

<div>
    <p>Your rating is: ${user.getRating()}</p>
    <p>Your rating status: ${user.getRatingStatus().getName()}</p>
    <p>Rating is awarded for activity on the website. The purpose of the rating is to give people that read your
        comments and topics a feel how involved you are into this problem </p>
</div>
<div>
    <h3>Your name:</h3>

    <p>${user.getFullName()} <br>This is a name that is used on the forum to identify you. It was randomly generated
        when you have created your
        account.<br> If you don't like this name you can change it: </p>
    <a href="/account/generate_random_name">Click here</a>
</div>

<div>
    <p>
        You also may change your password.
    </p>
    <a href="/account/change_password">Click here</a>
</div>
</body>
</html>