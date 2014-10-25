<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account</title>
    <style>
        .form-group {
            margin: 40px 400px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="form-group">
    <fieldset>
        <legend>Contracts</legend>
        <c:forEach var="contract" items="${client.contracts}">
            <p><a href="contract_details?contract_id=${contract.id}">${contract.phoneNumber}</a></p>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>
