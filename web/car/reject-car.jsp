<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 11/6/2019
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Reject Car</title>
</head>
<body>

<c:set var="role" value="${sessionScope.role}"/>
<c:set var="car" value="${requestScope.car}"/>
<c:set var="rentor" value="${requestScope.rentor}"/>

<ul>
    <li><a class="button" href="/car?method=getList">Cars</a></li>
    <c:if test="${sessionScope.username == null}">
        <li><a class="button" href="/auth?method=signIn">Sign In</a></li>
        <li><a class="button" href="/auth?method=signUp">Sign Up</a></li>
    </c:if>
    <c:if test="${sessionScope.username != null}">
        <li><a class="button" href="/auth?method=signOut">Sign Out</a></li>
    </c:if>
    <c:if test="${role.equals('ADMIN')}">
        <li><a class="button" href="/car?method=returnRequests">Return Requests</a></li>
    </c:if>
</ul>

<h3>Return Requests</h3>

<table class="table table-bordered" style="border: 2px solid black">
    <thead>
    <tr>
        <th style="border: 2px solid black">ID</th>
        <th style="border: 2px solid black">Mark</th>
        <th style="border: 2px solid black">Model</th>
        <th style="border: 2px solid black">Number</th>
        <th style="border: 2px solid black">Rentor</th>
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
            <td style="border: 1px solid black">${car.registeredNumber}
            </td>

            <td style="border: 1px solid black">${rentor.firstName} | ${rentor.lastName}
        </td>
        </tr>
    </c:if>

    </tbody>

</table>

<h3>Reject Car</h3>
<form action="car" method="POST">
    Fee amount: <input type="text" name="feeAmount" >
    <br/>
    Description: <input type="text" name="description" />
    <br/>
    <input type="hidden" name="id" value="${car.id}"/>
    <input type="hidden" name="method" value="rejectReturnDo"/>
    <input type="submit" value="Submit"/>
</form>


</body>
</html>
