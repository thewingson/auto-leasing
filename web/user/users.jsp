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
    <title>Users</title>
</head>
<body>
<c:set var="role" value="${sessionScope.role}"/>
<c:set var="users" value="${requestScope.users}"/>
<form action = "user" method = "POST">
    First name: <input type = "text" name = "firstName">
    <br />
    Last name: <input type = "text" name = "lastName" />
    <br />
    Email: <input type = "text" name = "email" />
    <br />
    Username: <input type = "text" name = "username" />
    <br />
    Password: <input type = "text" name = "password" />
    <input type = "submit" value = "Submit" />
</form>

<h3>Users</h3>
<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">First name</th>
        <th style="border: 2px solid black">Last name</th>
        <th style="border: 2px solid black">Email</th>
        <th style="border: 2px solid black">Username</th>
        <th style="border: 2px solid black">Password</th>
        <th style="border: 2px solid black">Action</th>

    </tr>
    </thead>
    <tbody>

    <c:forEach items="${users}" var="user">
        <tr>
            <td style="border: 1px solid black">${user.firstName}</td>
            <td style="border: 1px solid black">${user.lastName}</td>
            <td style="border: 1px solid black">${user.email}</td>
            <td style="border: 1px solid black">${user.username}</td>
            <td style="border: 1px solid black">${user.password}</td>
            <td style="border: 1px solid black">

            <c:if test="${role.equals('ADMIN')}">
                <a class="button" href="?method=update&id=${user.id}">Edit</a>
                <a class="button" href="?method=delete&id=${user.id}">Delete</a>
            </c:if>
            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>
</body>
</html>
