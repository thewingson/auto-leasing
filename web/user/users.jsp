<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/24/2019
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.almat.model.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
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
    <%
        List<User> users = null;
        if(request.getAttribute("users") != null){
            users = (List) request.getAttribute("users");
        }
    %>

    <%
        if(users != null){
            for (User u : users) {
    %>
    <tr>
        <td style="border: 1px solid black"><%=u.getFirstName()%></td>
        <td style="border: 1px solid black"><%=u.getLastName()%></td>
        <td style="border: 1px solid black"><%=u.getEmail()%></td>
        <td style="border: 1px solid black"><%=u.getUsername()%></td>
        <td style="border: 1px solid black"><%=u.getPassword()%></td>
        <td style="border: 1px solid black">

            <a class="button" href="?method=update&id=<%=u.getId()%>">Edit</a>
            <a class="button" href="?method=delete&id=<%=u.getId()%>">Delete</a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>

</table>
</body>
</html>
