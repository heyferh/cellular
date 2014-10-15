<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:forEach var="tariff" items="${tariffList}">
    ${tariff}
    <a href="/remove_tariff?tariff_id=${tariff.id}">Remove</a>
    <a href="/edit_options?tariff_id=${tariff.id}">Edit options</a> <br>
</c:forEach>
<a href="/create_new_tariff">Create</a>
</body>
</html>
