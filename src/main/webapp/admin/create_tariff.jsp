<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="post" action="create_tariff">
    Title: <input type="text" name="title">
    Price: <input type="text" name="price">
    <fieldset>
        <legend>Available options</legend>
        <c:forEach var="option" items="${optionList}">
            <input type="checkbox" name="options" value="${option.id}"> ${option.title}<br>
        </c:forEach>
    </fieldset>
    <input type="submit">
</form>
</body>
</html>
