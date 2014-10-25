<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<fieldset>
    <legend>All tariffs</legend>
    <c:forEach var="tariff" items="${tariffList}">
        Title: ${tariff.title} Cost: ${tariff.cost}
        <a href="remove_tariff?tariff_id=${tariff.id}">Remove</a>
        <a href="edit_options?tariff_id=${tariff.id}">Edit options</a> <br>
    </c:forEach>
    <a href="create_tariff">Create</a>
</fieldset>
</body>
</html>
