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
    <form action = "car" method = "POST">
        Mark: <input type = "text" name = "mark">
        <br />
        Model: <input type = "text" name = "model" />
        <br />
        Year: <input type = "text" name = "registeredNumber" />
        <input type = "submit" value = "Submit" />
    </form>

    <h3>Cars</h3>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Mark</th>
            <th>Model</th>
            <th>Year</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        // TODO: if you make use of JSTL, this code will become a lot simpler and nicer
            <%
                List<Car> cars = (List) request.getAttribute("cars");
            %>

            <%
                for (Car c : cars) {
            %>
            <tr>
                <td><%=c.getMark()%></td>
                <td><%=c.getModel()%></td>
                <td><%=c.getRegisteredNumber()%></td>
                <td>
<%--                    <a href="update?id=<%=c.getId()%>">Edit</a>--%>
                    <form action = "car" method = "GET">
                        <input type = "hidden" value="update" name="method" />
                        <input type = "hidden" value="<%=c.getId()%>" name="id" />
                        <input type = "submit" value = "Edit" />
                    </form>
                    &nbsp;&nbsp;&nbsp;&nbsp;
<%--                    <a href="car/delete?id=<%=c.getId()%>">Delete</a>--%>
                    <form action = "car" method = "POST">
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
