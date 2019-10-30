<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/24/2019
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Sign-Up</title>
</head>
<body>
    <h3>Sign up</h3>
    <form action="auth" method="POST">
        First name: <input type="text" name="firstName">
        <br/>
        Lastname: <input type="text" name="lastName">
        <br/>
        Email: <input type="text" name="email">
        <br/>
        Username: <input type="text" name="username">
        <br/>
        Password: <input type="text" name="password"/>
        <br/>
        Confirm Password: <input type="text" name="confirmPassword"/>
        <input type="hidden" value="signUp" name="method"/>
        <input type="submit" value="Submit"/>
    </form>

</body>
</html>
