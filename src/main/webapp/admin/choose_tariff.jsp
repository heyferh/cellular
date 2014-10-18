<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="POST" action="create_contract">
    <fieldset>
        <legend>Choose tariff</legend>
        <c:forEach var="tariff" items="${tariffList}">
            <input type="radio" name="tariff_id" value="${tariff.id}">Title: ${tariff.title} Cost: ${tariff.cost}<br>
        </c:forEach>
        <input type="submit">
    </fieldset>
</form>
</body>
</html>
