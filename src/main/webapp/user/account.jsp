<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<fieldset>
    <legend>Info</legend>
    <p>Name: ${client.firstName}</p>

    <p>Lastname: ${client.lastName}</p>
</fieldset>
<fieldset>
    <legend>Contracts</legend>
    <c:forEach var="contract" items="${client.contracts}">
        <a href="contract_details?contract_id=${contract.id}">${contract.phoneNumber}</a>
    </c:forEach>
</fieldset>
</body>
</html>
