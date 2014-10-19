<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:if test="${action eq 'require'}">
    <fieldset>
        <legend>Select required for "${option.title}" options</legend>
        <c:forEach var="option" items="${option.requiredOptions}">
            <p><input type="checkbox" name="required" value="${option.id}" checked> ${option.title}</p>
        </c:forEach>
        <c:forEach var="option" items="${optionList}">
            <p><input type="checkbox" name="required" value="${option.id}"> ${option.title}</p>
        </c:forEach>
    </fieldset>
</c:if>
<c:if test="${action eq 'compatible'}">
    <fieldset>
        <legend>Select incompatible with "${option.title}" options</legend>
        <c:forEach var="option" items="${option.incompatibleOptions}">
            <p><input type="checkbox" name="incompatible" value="${option.id}" checked> ${option.title}</p>
        </c:forEach>
        <c:forEach var="option" items="${optionList}">
            <p><input type="checkbox" name="incompatible" value="${option.id}"> ${option.title}</p>
        </c:forEach>
    </fieldset>
</c:if>
</body>
</html>
