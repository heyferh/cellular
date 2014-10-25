<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="../style.css">
    <title>Contract</title>
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
        <legend>Client information</legend>
        <p>Name: ${contract.client.firstName}</p>

        <p>Lastname: ${contract.client.lastName}</p>

        <p>Phone number: ${contract.phoneNumber}</p>

        <p>Balance: ${contract.balance}</p>
    </fieldset>
    <fieldset>
        <legend>Status</legend>
        <c:if test="${contract.blockedByOperator}">
            <p>Blocked <a href="unblock?contract_id=${contract.id}">Unblock</a></p>
        </c:if>
        <c:if test="${not contract.blockedByOperator}">
            <p>Active <a href="block?contract_id=${contract.id}">Block</a></p>
        </c:if>
    </fieldset>
    <fieldset>
        <legend>Tariff</legend>
        <p>Current tariff: ${contract.tariff.title} </p>
        <a href="select_tariff?contract_id=${contract.id}">Change tariff</a>
    </fieldset>
    <fieldset>
        <legend>Options</legend>
        <p>Actual options:</p>
        <c:forEach var="option" items="${contract.options}">
            ${option.title} <a href="disable_option?contract_id=${contract.id}&option_id=${option.id}">Disable</a><br>
        </c:forEach>
        <a href="select_options?contract_id=${contract.id}">Enable options</a>
    </fieldset>
</div>
</body>
</html>
