<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <title></title>
</head>
<body>
<p>Name:${contract.client.firstName}</p>

<p>Lastname:${contract.client.lastName}</p>

<p>Phone number:${contract.phoneNumber}</p>


<p>Current tariff: ${contract.tariff.title} </p>
<a href="/select_tariff?contract_id=${contract.id}">Change tariff</a>
<p>Actual options:</p>
<c:forEach var="option" items="${contract.options}">
    ${option.title} <a href="/disable_option?contract_id=${contract.id}&option_id=${option.id}">Disable</a><br>
</c:forEach>
<a href="/select_options?contract_id=${contract.id}">Enable options</a>

<p></p>
</body>
</html>
