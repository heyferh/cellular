<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>All contracts</title>
    <style>
        .bs-example {
            margin: 40px 350px;
        }

        .navbar-form {
            margin: 80px 350px;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<h2 align="center">All contracts</h2>

<div align="center">
    <form class="navbar-form" role="search" action="search" method="POST">
        <div class="form-group">
            <input type="text" class="form-control" name="phonenumber">
        </div>
        <input type="submit" class="btn-primary bttn" value="Search">
    </form>
</div>


<div class="bs-example">
    <table class="table">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Contracts</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="client" items="${clientList}">
            <tr>
                <td>${client.firstName}</td>
                <td>${client.lastName}</td>
                <td>
                    <c:forEach var="contract" items="${client.contracts}">
                        <a href="contract_details?id=${contract.id}">${contract.phoneNumber}</a>
                        <br>
                    </c:forEach>
                    <a href="add_another_contract?client_id=${client.id}">+</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>