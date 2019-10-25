<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/21/2019
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.almat.model.Car" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Cars</title>
</head>
<body>
<form action="car" method="POST">
    Mark: <input type="text" name="mark">
    <br/>
    Model: <input type="text" name="model"/>
    <br/>
    Year: <input type="text" name="registeredNumber"/>
    <input type="submit" value="Submit"/>
</form>

<h3>Cars</h3>
<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">Mark</th>
        <th style="border: 2px solid black">Model</th>
        <th style="border: 2px solid black">Year</th>
        <th style="border: 2px solid black">Rentor</th>
        <th style="border: 2px solid black">Action</th>
    </tr>
    </thead>
    <tbody style="border: 2px solid black">
    <%
        List<Car> cars = null;
        if(request.getAttribute("cars")!= null){
            cars = (List) request.getAttribute("cars");
        }
    %>

    <%
        if(cars != null){
            for (Car c : cars) {
    %>
    <tr >
        <td style="border: 1px solid black"><%=c.getMark()%>
        </td>
        <td style="border: 1px solid black"><%=c.getModel()%>
        </td>
        <td style="border: 1px solid black"><%=c.getRegisteredNumber()%>
        </td>
        <td style="border: 1px solid black"><%=c.getRentor()%>
        </td>
        <td style="border: 1px solid black">
            <a class="button" href="?method=update&id=<%=c.getId()%>">Edit</a>
            <a class="button" href="?method=delete&id=<%=c.getId()%>">Delete</a>

            <% if(request.getSession().getAttribute("username") != null ){ %>
<%--            //&& c.getRentor() == null--%>
                <a class="button" href="?method=rent&id=<%=c.getId()%>">Rent</a>
            <% } %>
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
