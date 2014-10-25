<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Select</title>
    <style>
        .form-group {
            margin: 40px 400px;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div class="form-group">
    <form method="POST" action="change_tariff">
        <fieldset>
            <legend>Choose tariff to replace current</legend>
            <c:forEach var="tariff" items="${tariffList}">
                <input type="radio" name="new_tariff" value="${tariff.id}"> ${tariff.title}<br>
            </c:forEach>
            <input type="hidden" name="contract_id" value="${contract_id}">
            <input type="submit" value="Choose">
        </fieldset>
    </form>
</div>
</body>
</html>
