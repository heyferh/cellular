<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<form:form action="new" method="post" commandName="clientBean">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header">Create contract</h2>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Step 1. Create new user
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="col-lg-6">
                            <div class="form-group">
                                ID card: <form:errors path="idCard" cssClass="error"/>
                                <form:input path="idCard" class="form-control" placeholder="" type="text"/>
                            </div>
                            <div class="form-group">
                                Email: <form:errors path="email" cssClass="error"/>
                                <form:input path="email" class="form-control" placeholder="" type="email"/>
                            </div>
                            <div class="form-group">
                                Phone number: <span id="phoneError" class="error"></span>
                                <input name="phoneNumber" id="phoneNumber" class="form-control" placeholder=""
                                       type="number" onchange="checkNumber()"/>
                            </div>
                            <div class="form-group">
                                Password: <form:errors path="password" cssClass="error"/>
                                <form:input path="password" class="form-control" placeholder="" type="password"/>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="form-group">
                                Name: <form:errors path="firstName" cssClass="error"/>
                                <form:input path="firstName" class="form-control" placeholder="" type="text"/>
                            </div>
                            <div class="form-group">
                                Last Name: <form:errors path="lastName" cssClass="error"/>
                                <form:input path="lastName" class="form-control" placeholder="" type="text"/>
                            </div>
                            <div class="form-group">
                                Birth date: <form:errors path="dayOfBirth" cssClass="error"/>
                                <form:input path="dayOfBirth" class="form-control" placeholder="" type="date"/>
                            </div>
                            <div class="form-group">
                                Address: <form:errors path="address" cssClass="error"/>
                                <form:input path="address" class="form-control" placeholder="" type="text"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Step 2. Choose tariff and options
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="col-lg-6">
                            <div class="form-group">
                                <c:forEach var="tariff" items="${tariffList}">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="tariff_id" value="${tariff.id}"
                                                   onclick="getOptions(${tariff.id})">${tariff.title}.
                                            Cost: ${tariff.cost}
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                            <input id="submit" type="submit" class="btn btn-primary" value="Create">
                        </div>
                        <div class="col-lg-6">
                            <div class="checkbox options">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>
</body>
</html>
