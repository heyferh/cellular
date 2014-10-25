<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<c:forEach var="option" items="${tariff.options}">
    ${option.title} <a href="remove_option?tariff_id=${tariff.id}&option_id=${option.id}">Remove</a><br>
</c:forEach>
<a href="add_option?tariff_id=${tariff.id}">Add options</a><br>
</body>
</html>
