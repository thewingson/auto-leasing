<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/28/2019
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CAR</title>
</head>
<body>

<c:set var="role" value="${sessionScope.role}"/>
<c:set var="car" value="${requestScope.car}"/>

<h3>Car</h3>
<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">ID</th>
        <th style="border: 2px solid black">Mark</th>
        <th style="border: 2px solid black">Model</th>
        <th style="border: 2px solid black">Number</th>
        <th style="border: 2px solid black">Rentor</th>
        <th style="border: 2px solid black">Action</th>
    </tr>
    </thead>
    <tbody style="border: 2px solid black">

    <c:if test="${car != null }">
        <tr>

            <td style="border: 1px solid black"><a class="button" href="?method=getOne&id=${car.id}">${car.id}
            </a>
            </td>

            <td style="border: 1px solid black">${car.mark}
            </td>
            <td style="border: 1px solid black">${car.model}
            </td>
            <td style="border: 1px solid black">${car.number}
            </td>
            <td style="border: 1px solid black">${car.rentor_id}
            </td>

            <td style="border: 1px solid black">
                <c:if test="${role.equals('ADMIN')}">
                    <a class="button" href="?method=update&id=${car.id}">Edit</a>
                    <a class="button" href="?method=delete&id=${car.id}">Delete</a>
                </c:if>

                <c:if test="${car.rentor_id == 0 && sessionScope.username != null && role.equals('USER')}">
                    <a class="button" href="?method=rent&id=${car.id}">Rent</a>
                </c:if>
            </td>
        </tr>
    </c:if>

    </tbody>

</table>
</body>
</html>

