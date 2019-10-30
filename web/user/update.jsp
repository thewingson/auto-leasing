<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/24/2019
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update</title>
</head>
<body>
<c:set var="user" value="${requestScope.user}"/>

<h3>Update</h3>
<form action = "user" method = "POST">
    First name: <input type = "text" name = "firstName" placeholder="${user.firstName}">
    <br />
    Last name: <input type = "text" name = "lastName" placeholder="${user.lastName}"/>
    <br />
    Email: <input type = "text" name = "email" placeholder="${user.email}"/>
    <br />
    Username: <input type = "text" name = "username" placeholder="${user.username}"/>
    <br />
    Password: <input type = "text" name = "password" placeholder="${user.password}"/>
    <input type = "hidden" name = "id" value="${user.id}"/>
    <input type = "hidden" name = "method" value="update"/>
    <input type = "submit" value = "Submit" />
</form>
</body>
</html>
