<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/18/2019
  Time: 11:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.almat.model.Car" %>
<html>
  <head>
    <title>Update</title>
  </head>
  <body>
    <h3>Update</h3>
    <%
      Car carToUpdate = (Car) request.getAttribute("car");
    %>
    <form action = "car" method = "POST">
      Mark: <input type = "text" name = "mark" placeholder="<%=carToUpdate.getMark()%>">
      <br />
      Model: <input type = "text" name = "model" placeholder="<%=carToUpdate.getModel()%>"/>
      <br />
      Year: <input type = "text" name = "registeredNumber" placeholder="<%=carToUpdate.getRegisteredNumber()%>"/>
      <input type = "hidden" name = "id" value="<%=carToUpdate.getId()%>"/>
      <input type = "hidden" name = "method" value="update"/>
      <input type = "submit" value = "Submit" />
    </form>
  </body>
</html>
