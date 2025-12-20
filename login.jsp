<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>

<style>
body {
    font-family: Arial;
    background: rgb(255, 255, 0), 0, 128);
}
.box {
    width: 350px;
    margin: 100px auto;
    background: rgb(64, 0, 0), 255, 64)28, 128, 255);
    padding: 20px;
    border-radius: 6px;
}
input, button {
    width: 100%;
    padding: 8px;
    margin-top: 10px;
}
button {
    background: #3498db;
    color: white;
    border: none;
}
</style>
</head>

<body>

<div class="box">
    <h2>Login</h2>

    <form action="login" method="post">
        Email:
        <input type="email" name="email" required>

        Password:
        <input type="password" name="password" required>

        <button type="submit">Login</button>
    </form>

    <p style="color:red">${error}</p>
</div>

</body>
</html>
