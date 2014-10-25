<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Create contract</title>
    <style>
        .form-group {
            margin: 40px 500px;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div class="form-group">
    <form method="post" action="add_contract">
        <fieldset>
            <legend>Fill forms</legend>
            <p><input type="text" class="form-control" name="firstname" placeholder="First name"></p>

            <p><input type="text" class="form-control" name="lastname" placeholder="Last name"></p>

            <p><input type="date" class="form-control" name="dayofbirth" placeholder="BD"></p>

            <p><input type="number" class="form-control" name="idcard" placeholder="ID Card"></p>

            <p><input type="text" class="form-control" name="address" placeholder="Address"></p>

            <p><input type="email" class="form-control" name="email" placeholder="Email"></p>

            <p><input type="password" class="form-control" name="password" placeholder="Password"></p>

            <p><input type="number" class="form-control" name="phonenumber" placeholder="Phone Number"></p>
        </fieldset>
        <fieldset>
            <legend>Your tariff is: ${tariff.title}
                <br>
                Choose options
            </legend>
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
</div>
</body>
</html>
