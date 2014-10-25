<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Choose tariff</title>
    <style>
        .form-group {
            margin: 40px 400px;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div class="form-group">
    <c:if test="${empty client_id}">
        <form method="POST" action="fill_forms">
            <fieldset>
                <legend>Choose tariff</legend>
                <c:forEach var="tariff" items="${tariffList}">
                    <input type="radio" name="tariff_id"
                           value="${tariff.id}"> Title: ${tariff.title}
                    <br>
                    Cost: ${tariff.cost}
                    <br><hr>
                </c:forEach>
                <input type="submit" value="Choose">
            </fieldset>
        </form>
    </c:if>
    <c:if test="${not empty client_id}">
        <form method="POST" action="fill_contract_info">
            <fieldset>
                <legend>Choose tariff</legend>
                <c:forEach var="tariff" items="${tariffList}">
                    <input type="radio" name="tariff_id"
                           value="${tariff.id}">Title: ${tariff.title}<br>
                    Cost: ${tariff.cost}
                    <br><hr>
                </c:forEach>
                <input type="hidden" name="client_id" value="${client_id}">
                <input type="submit" value="Choose">
            </fieldset>
        </form>
    </c:if>
</div>
</body>
</html>
