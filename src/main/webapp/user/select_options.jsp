<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<fieldset>
    <legend>Choose option to enable</legend>
    <c:forEach var="option" items="${optionList}">
        ${option.title} <a href="enable_option?contract_id=${contract_id}&option_id=${option.id}">Enable</a><br>
    </c:forEach>
</fieldset>
</body>
</html>
