<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css">
    <script src="../js/jquery-1.11.1.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <style type="text/css">
        .form-group {
            margin: 40px 400px;
        }

        .alert-info {
            padding: 10px;
            margin: 40px 400px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="alert-info" align="right">
    <a href="account">Welcome, ${client.firstName} ${client.lastName}</a><br>
    <a href="logout">Logout </a>
</div>
</body>
</html>