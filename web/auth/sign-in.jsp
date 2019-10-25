<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/24/2019
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign-In</title>
</head>
<body>
    <h3>Sign in</h3>
    <form action="auth" method="POST">
        Username: <input type="text" name="username">
        <br/>
        Password: <input type="text" name="password"/>
        <input type="hidden" value="signIn" name="method"/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>
