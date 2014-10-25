<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Create</title>
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
        <legend>Create new option</legend>
        <form method="post" action="all_options">
            <p><input type="text" class="form-control" name="title" placeholder="Title"></p>

            <p><input type="number" class="form-control" name="activationcost" placeholder="Activation Cost"></p>

            <p><input type="number" class="form-control" name="cost" placeholder="Cost"></p>

            <p><input type="submit" value="Create"></p>
        </form>
    </fieldset>
</div>
<div class="form-group">
    <fieldset>
        <legend>Options</legend>
        <c:forEach var="option" items="${optionList}">
            <a href="delete_option?option_id=${option.id}">Remove</a><br> Title: ${option.title}<br>
                <a href="manage_options?option_id=${option.id}&action=require">Manage requirements</a> |
                <a href="manage_options?option_id=${option.id}&action=incompatible">Manage compatibilities </a><br><hr>
        </c:forEach>
    </fieldset>
</div>
</body>
</html>