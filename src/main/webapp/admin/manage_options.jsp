<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<c:if test="${action eq 'require'}">
    <form method="POST" action="manage_required_options">
        <fieldset>
            <legend>Select required for "${option.title}" options</legend>
            <c:forEach var="option" items="${option.requiredOptions}">
                <p><input type="checkbox" name="required" value="${option.id}" checked> ${option.title}</p>
            </c:forEach>
            <c:forEach var="option" items="${optionList}">
                <p><input type="checkbox" name="required" value="${option.id}"> ${option.title}</p>
            </c:forEach>
            <p><input type="submit"></p>

            <p><input type="hidden" name="option_id" value="${option.id}"></p>
            <p><input type="hidden" name="action" value="require"></p>
        </fieldset>
    </form>
</c:if>
<c:if test="${action eq 'compatible'}">
    <form method="POST" action="manage_incompatible_options">
        <fieldset>
            <legend>Select incompatible with "${option.title}" options</legend>
            <c:forEach var="option" items="${option.incompatibleOptions}">
                <p><input type="checkbox" name="incompatible" value="${option.id}" checked> ${option.title}</p>
            </c:forEach>
            <c:forEach var="option" items="${optionList}">
                <p><input type="checkbox" name="incompatible" value="${option.id}"> ${option.title}</p>
            </c:forEach>
            <p><input type="submit"></p>

            <p><input type="hidden" name="option_id" value="${option.id}"></p>
            <p><input type="hidden" name="action" value="incompatible"></p>
        </fieldset>
    </form>
</c:if>
</body>
</html>
