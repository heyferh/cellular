<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">

<head>
    <title>Add contract</title>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Add another contract</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-10">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Step 1. Choose phone number
                </div>
                <div class="panel-body">
                    <div id="addContractError" class="alert alert-danger alert-dismissable" style="display: none">
                    </div>
                    <div class="col-lg-6">
                        <div class="form-group">
                            Phone number:
                            <span id="phoneNumberError" class="error"></span>
                            <input id="phoneNumber" class="form-control" onblur="checkNumber()"/>
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
                        <input id="submit" type="submit" class="btn btn-primary" value="Create"
                               onclick="addContract(${client_id})">
                        <input type="hidden" name="client_id" value="${client_id}">
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
</body>
</html>
