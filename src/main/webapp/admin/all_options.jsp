<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<fieldset>
    <legend>Create new option</legend>
    <form method="post" action="all_options">
        <p>Title: <input type="text" name="title"></p>

        <p>Activation cost: <input type="number" name="activationcost"></p>

        <p>Cost: <input type="number" name="cost"></p>

        <p><input type="submit"></p>
    </form>
</fieldset>
<fieldset>
    <legend>Options</legend>
    <c:forEach var="option" items="${optionList}">
        <p><a href="delete_option?option_id=${option.id}">Remove</a> ${option.title}
            <a href="manage_options?option_id=${option.id}&action=require">Manage requirements</a>
            <a href="manage_options?option_id=${option.id}&action=incompatible">Manage compatibilities </a></p>
    </c:forEach>
</fieldset>
</body>
</html>