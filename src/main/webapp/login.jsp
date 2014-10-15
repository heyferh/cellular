<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>login page</title>
</head>
<body>
<form method="post" action="all" enctype="application/x-www-form-urlencoded">
    <p>email<input type="email" name="email"></p>
    <p>password<input type="password" name="password"></p>

    <p><input type="submit"></p>
</form>
</body>
</html>