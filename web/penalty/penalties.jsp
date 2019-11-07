<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 11/6/2019
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Penalties</title>
</head>
<body>

<c:set var="role" value="${sessionScope.role}"/>
<c:set var="penalties" value="${requestScope.penalties}"/>

<ul>
    <li><a class="button" href="/car?method=getList">Cars</a></li>
    <c:if test="${sessionScope.username == null}">
        <li><a class="button" href="/auth?method=signIn">Sign In</a></li>
        <li><a class="button" href="/auth?method=signUp">Sign Up</a></li>
    </c:if>
    <c:if test="${sessionScope.username != null}">
        <li><a class="button" href="/auth?method=signOut">Sign Out</a></li>
    </c:if>
    <c:if test="${role.equals('USER')}">
        <li><a class="button" href="/car?method=myCars">My Cars</a></li>
    </c:if>
</ul>

<h3>Penalties</h3>
<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">ID</th>
        <th style="border: 2px solid black">Fee Amount</th>
        <th style="border: 2px solid black">Description</th>
        <th style="border: 2px solid black">Action</th>
    </tr>
    </thead>
    <tbody style="border: 2px solid black">

    <c:forEach items="${penalties}" var="penalty">
        <tr>
            </td>
            <td>${penalty.id}</td>
            <td>${penalty.feeAmount}</td>
            <td>${penalty.description}</td>
            <td>
                    <a class="button" href="?method=payPenaltyDo&id=${penalty.id}">Pay</a>
            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>
</body>
</html>

