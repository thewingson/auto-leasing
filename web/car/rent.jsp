<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 11/4/2019
  Time: 12:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Rent</title>
</head>
<body>
<c:set var="car" value="${requestScope.car}"/>
<h3>Rent</h3>
<form action="car" method="POST">
    Driver License: <input type="text" name="driverLicense">
    <br/>
    Start Date: <input type="text" name="startDate" />
    <br/>
    End Date: <input type="text" name="endDate" />
    <input type="hidden" name="id" value="${car.id}"/>
    <input type="hidden" name="method" value="rent"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
