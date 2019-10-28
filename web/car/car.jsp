<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/28/2019
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="kz.almat.model.dto.CarDTO" %>
<html>
<head>
    <title>CAR</title>
</head>
<body>

<h3>Car</h3>
<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">ID</th>
        <th style="border: 2px solid black">Mark</th>
        <th style="border: 2px solid black">Model</th>
        <th style="border: 2px solid black">Year</th>
        <th style="border: 2px solid black">Rentor</th>
        <th style="border: 2px solid black">Action</th>
    </tr>
    </thead>
    <tbody style="border: 2px solid black">
    <%
        CarDTO car = null;
        if (request.getAttribute("car") != null) {
            car = (CarDTO) request.getAttribute("car");
        }
    %>

    <%
        if (car != null) {
    %>
    <tr>
        <td style="border: 1px solid black"><a class="button" href="?method=getOne&id=<%=car.getId()%>"><%=car.getId()%>
        </a>
        </td>
        <td style="border: 1px solid black"><%=car.getMark()%>
        </td>
        <td style="border: 1px solid black"><%=car.getModel()%>
        </td>
        <td style="border: 1px solid black"><%=car.getRegisteredNumber()%>
        </td>
        <td style="border: 1px solid black"><%=car.getRentor_id()%>
        </td>
        <td style="border: 1px solid black">
            <a class="button" href="?method=update&id=<%=car.getId()%>">Edit</a>
            <a class="button" href="?method=delete&id=<%=car.getId()%>">Delete</a>

            <% if (request.getSession().getAttribute("username") != null && car.getRentor_id() == null) { %>
            <%--            //&& c.getRentor() == null--%>
            <a class="button" href="?method=rent&id=<%=car.getId()%>">Rent</a>
            <% } %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>

</table>
</body>
</html>

