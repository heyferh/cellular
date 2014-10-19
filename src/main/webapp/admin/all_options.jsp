<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
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
        <p>${option.title} <a href="delete_option?option_id=${option.id}">Remove</a></p>
    </c:forEach>
</fieldset>
</body>
</html>