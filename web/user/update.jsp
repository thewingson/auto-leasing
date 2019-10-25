<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/24/2019
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.almat.model.User" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<h3>Update</h3>
<%
    User userToUpdate = (User) request.getAttribute("user");
%>
<form action = "user" method = "POST">
    First name: <input type = "text" name = "firstName" placeholder="<%=userToUpdate.getFirstName()%>">
    <br />
    Last name: <input type = "text" name = "lastName" placeholder="<%=userToUpdate.getLastName()%>"/>
    <br />
    Email: <input type = "text" name = "email" placeholder="<%=userToUpdate.getEmail()%>"/>
    <br />
    Username: <input type = "text" name = "username" placeholder="<%=userToUpdate.getUsername()%>"/>
    <br />
    Password: <input type = "text" name = "password" placeholder="<%=userToUpdate.getPassword()%>"/>
    <input type = "hidden" name = "id" value="<%=userToUpdate.getId()%>"/>
    <input type = "hidden" name = "method" value="update"/>
    <input type = "submit" value = "Submit" />
</form>
</body>
</html>
