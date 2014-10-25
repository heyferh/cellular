<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<form method="post" action="add_contract">
    <fieldset>
        <legend>Please, fill the requested forms</legend>

        <p>First name: <input type="text" name="firstname"></p>

        <p>Last name: <input type="text" name="lastname"></p>

        <p> Day of birth: <input type="date" name="dayofbirth"></p>

        <p>ID card: <input type="number" name="idcard"></p>

        <p>Address: <input type="text" name="address"></p>

        <p>Email: <input type="email" name="email"></p>

        <p>Password: <input type="password" name="password"></p>

        <p>Phone number: <input type="number" name="phonenumber"></p>
    </fieldset>
    <fieldset>
        <p>Your tariff is: ${tariff.title}</p>
        <legend>Choose options to add</legend>
        <c:forEach var="option" items="${optionList}">
            <input type="checkbox" name="options" value="${option.id}">
            Title: ${option.title}
            Activation cost: ${option.activationCost}
            Cost: ${option.cost}
            <br>
        </c:forEach>
        <input type="hidden" name="tariff_id" value="${tariff.id}">
        <input type="submit">
    </fieldset>
</form>
</body>
</html>
