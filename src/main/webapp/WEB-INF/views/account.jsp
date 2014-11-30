<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <title>Account</title>
</head>

<body>
<c:import url="imports.jsp"></c:import>
<div id="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/home"><img src="/cellular/app/js/logo.png"></a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li>${pageContext.request.userPrincipal.name}
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/j_spring_security_logout">
                    <i class="fa fa-sign-out fa-fw"></i>
                    Logout
                </a>
            </li>
        </ul>
    </nav>
</div>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Account</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Info
                </div>
                <div class="panel-body">
                    <div class="col-lg-2">
                        <p>Name:</p>

                        <p>Last name:</p>

                        <p>Address:</p>

                        <p>ID card:</p>

                    </div>
                    <div class="col-lg-4">
                        <p>${client.firstName}</p>

                        <p>${client.lastName}</p>

                        <p>${client.address}</p>

                        <p>${client.idCard}</p>

                    </div>
                    <div class="col-lg-2">

                        <p>Email:</p>

                        <p>Contract:</p>
                    </div>
                    <div class="col-lg-4">
                        <p>${client.email}</p>

                        <p>
                            <select class="form-control" onchange="javascript:location.href = this.value;">
                                <option selected disabled>choose contract</option>
                                <c:forEach var="contract" items="${client.contracts}">
                                    <option value="account_details?id=${contract.id}&clientID=${client.id}">${contract.phoneNumber}</option>
                                </c:forEach>
                            </select>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
