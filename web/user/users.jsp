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
<table class="table table-bordered">
    <thead>
    <tr>
        <th>First name</th>
        <th>Last name</th>
        <th>Email</th>
        <th>Username</th>
        <th>Password</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<User> users = (List) request.getAttribute("users");
    %>

    <%
        for (User c : users) {
    %>
    <tr>
        <td><%=c.getFirstName()%></td>
        <td><%=c.getLastName()%></td>
        <td><%=c.getEmail()%></td>
        <td><%=c.getUsername()%></td>
        <td><%=c.getPassword()%></td>
        <td>
            <%--                    <a href="update?id=<%=c.getId()%>">Edit</a>--%>
            <form action = "user" method = "GET">
                <input type = "hidden" value="update" name="method" />
                <input type = "hidden" value="<%=c.getId()%>" name="id" />
                <input type = "submit" value = "Edit" />
            </form>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <%--                    <a href="car/delete?id=<%=c.getId()%>">Delete</a>--%>
            <form action = "user" method = "POST">
                <input type = "hidden" value="delete" name="method" />
                <input type = "hidden" value="<%=c.getId()%>" name="id" />
                <input type = "submit" value = "Delete" />
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>

</table>
</body>
</html>
