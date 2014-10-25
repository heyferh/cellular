<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit options</title>
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
        <legend>Choose options</legend>
        <c:forEach var="option" items="${tariff.options}">
            ${option.title} <a href="remove_option?tariff_id=${tariff.id}&option_id=${option.id}">Remove</a><br><hr>
        </c:forEach>
        <a href="add_option?tariff_id=${tariff.id}">Add options</a><br>
    </fieldset>
</div>
</body>
</html>
