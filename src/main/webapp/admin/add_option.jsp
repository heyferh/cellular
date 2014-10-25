<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<form method="post" action="add_option">
    <fieldset>
        <legend>Choose options to add</legend>
        <c:forEach var="option" items="${optionList}">
            <input type="checkbox" name="options" value="${option.id}"> ${option.title}<br>
        </c:forEach>
        <input type="hidden" name="tariff_id" value="${tariff_id}">
        <input type="submit">
    </fieldset>
</form>
</body>
</html>
