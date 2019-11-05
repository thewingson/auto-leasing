<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/21/2019
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cars</title>
</head>
<body>

<c:set var="role" value="${sessionScope.role}"/>
<c:set var="cars" value="${requestScope.cars}"/>

<ul>
    <c:if test="${sessionScope.username == null}">
        <li><a class="button" href="/auth?method=signIn">Sign In</a></li>
        <li><a class="button" href="/auth?method=signUp">Sign Up</a></li>
    </c:if>
    <c:if test="${sessionScope.username != null}">
        <li><a class="button" href="/auth?method=signOut">Sign Out</a></li>
    </c:if>

</ul>

<form action="car" method="POST">
    Mark: <input type="text" name="mark">
    <br/>
    Model: <input type="text" name="model"/>
    <br/>
    Number: <input type="text" name="registeredNumber"/>
    <input type="hidden" name="method" value="create"/>
    <input type="submit" value="Submit"/>
</form>

<h3>Cars</h3>
<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">ID</th>
        <th style="border: 2px solid black">Mark</th>
        <th style="border: 2px solid black">Model</th>
        <th style="border: 2px solid black">Number</th>
        <%--<th style="border: 2px solid black">Rentor</th>--%>
        <th style="border: 2px solid black">Action</th>
    </tr>
    </thead>
    <tbody style="border: 2px solid black">

    <c:forEach items="${cars}" var="car">
    <tr>
        <td style="border: 1px solid black"><a class="button" href="?method=getOne&id=${car.id}">${car.id}</a>
        </td>
        <td>${car.mark}</td>
        <td>${car.model}</td>
        <td>${car.registeredNumber}</td>
        <%--<td>${car.rentor_id}</td>--%>

        <td>
            <c:if test="${role.equals('USER')}">
                <a class="button" href="?method=rent&id=${car.id}">Rent</a>
            </c:if>
            <c:if test="${role.equals('ADMIN')}">
                <a class="button" href="?method=update&id=${car.id}">Edit</a>
                <a class="button" href="?method=delete&id=${car.id}">Delete</a>
            </c:if>
        </td>
    </tr>
    </c:forEach>

    </tbody>

</table>
</body>
</html>
