<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <script src="jquery-2.1.1.min.js"></script>
    <title>All contracts</title>
</head>
<body>
<%@include file="menu.jsp" %>
<c:forEach var="client" items="${clientList}">
    <div class="container">
        <div class="client"><p>${client.firstName}</p></div>
        <div class="contracts">
            <c:forEach var="contract" items="${client.contracts}">
                <p id="${contract.id}"
                   onclick="window.location.href='/contract?clicked_id=${contract.id}'">${contract.phoneNumber}</p>
            </c:forEach>
        </div>
    </div>
</c:forEach>
</body>
</html>
