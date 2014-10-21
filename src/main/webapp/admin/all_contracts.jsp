<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="../style.css">
    <title>All contracts</title>
</head>
<body>
<c:forEach var="client" items="${clientList}">
    <div class="container">
        <div class="client"><p>${client.firstName}</p></div>
        <div class="contracts">
            <c:forEach var="contract" items="${client.contracts}">
                <p id="${contract.id}">
                    <a href="contract_details?id=${contract.id}">${contract.phoneNumber}</a></p>
            </c:forEach>
            <p><a href="add_another_contract?client_id=${client.id}">+</a></p>
        </div>
    </div>
</c:forEach>
</body>
</html>
