<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create tariff</title>
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
        <legend>Create new tariff</legend>
    <form method="post" action="create_tariff">
        <p><input type="text" class="form-control" name="title" placeholder="Title"></p>
        <p><input type="number" class="form-control" name="price" placeholder="Price"></p>
        <fieldset>
            <legend>Available options</legend>
            <c:forEach var="option" items="${optionList}">
                <input type="checkbox" name="options" value="${option.id}"> ${option.title}<br>
            </c:forEach>
        </fieldset>
        <input type="submit" value="Create">
    </form>
    </fieldset>
</div>
</body>
</html>
