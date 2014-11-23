<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
</head>

<body>
<c:import url="imports.jsp"></c:import>
<div id="wrapper">
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/contract/all">logo.png</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li>${pageContext.request.userPrincipal.name}
            </li>
            <li><a href="${pageContext.request.contextPath}/j_spring_security_logout"><i
                    class="fa fa-sign-out fa-fw"></i> Logout</a>
            </li>
        </ul>
        <!-- /.navbar-top-links -->
    </nav>
</div>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Account</h2>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Info
                </div>
                <!-- /.panel-heading -->
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
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->
</body>

</html>
