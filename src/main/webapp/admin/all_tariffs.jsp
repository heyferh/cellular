<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All tariffs</title>
    <style>
        .form-group {
            margin: 40px 400px;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div class="form-group">
    <fieldset>
        <legend>All tariffs</legend>
        <c:forEach var="tariff" items="${tariffList}">
            Title: ${tariff.title} <br>Cost: ${tariff.cost}<br>
            <a href="remove_tariff?tariff_id=${tariff.id}">Remove</a> |
            <a href="edit_options?tariff_id=${tariff.id}">Edit options</a> <br><hr>
        </c:forEach>
        <a href="create_tariff">Create</a>
    </fieldset>
</div>
</body>
</html>
