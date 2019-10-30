<%--
  Created by IntelliJ IDEA.
  User: Almat_Rakhmetolla
  Date: 10/18/2019
  Time: 11:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<c:set var="car" value="${requestScope.car}"/>
<h3>Update</h3>
<form action="car" method="POST">
    Mark: <input type="text" name="mark" placeholder="${car.mark}">
    <br/>
    Model: <input type="text" name="model" placeholder="${car.model}"/>
    <br/>
    Number: <input type="text" name="registeredNumber" placeholder="${car.registeredNumber}"/>
    <input type="hidden" name="id" value="${car.id}"/>
    <input type="hidden" name="method" value="update"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
