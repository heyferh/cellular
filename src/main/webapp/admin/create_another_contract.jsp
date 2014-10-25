<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Create contract</title>
    <style>
        .form-group {
            margin: 40px 400px;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div class="form-group">
    <form method="post" action="register_new_contract">
        <fieldset>
            <legend>Fill requested forms</legend>
            <p><input type="number" class="form-control" name="phonenumber" placeholder="Phone number"></p>
        </fieldset>
        <fieldset>
            <p>Your tariff is: ${tariff.title}</p>
            <legend>Choose options to add</legend>
            <c:forEach var="option" items="${optionList}">
                <input type="checkbox" name="options" value="${option.id}">
                ${option.title}<br>
                Activation cost: ${option.activationCost}<br>
                Cost: ${option.cost}
                <br>
            </c:forEach>
            <input type="hidden" name="tariff_id" value="${tariff.id}">
            <input type="hidden" name="client_id" value="${client_id}">
            <input type="submit" value="Send">
        </fieldset>
    </form>
</div>
</body>
</html>
