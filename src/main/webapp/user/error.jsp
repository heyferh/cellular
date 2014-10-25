<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Error</title>
    <style>
        .alert-danger {
            margin: 40px 400px;
            padding: 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="alert-danger">
    <p onclick="history.go(-1);">${message}<br>Press to return.</p>
</div>
</body>
</html>
