<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Edit tariff</title>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Manage tariff options</h2>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        ${tariff.title}
                    </div>
                    <div class="panel-body">
                        <div id="deleteError" class="alert alert-danger alert-dismissable" style="display: none">
                        </div>
                        <div class="col-lg-6">
                            <div class="panel-body">
                                Current options:
                                <c:choose>
                                    <c:when test="${not empty tariff.options}">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>Title</th>
                                                    <th>Cost</th>
                                                    <th>Activation Cost</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="option" items="${tariff.options}">
                                                    <tr>
                                                        <td>
                                                            <a onclick="removeTariffOption(${option.id},${tariff.id})"
                                                               style="cursor: pointer">
                                                                <i class="fa fa-minus fa-fw"></i>
                                                            </a>
                                                        </td>
                                                        <td>${option.title}</td>
                                                        <td>${option.cost}</td>
                                                        <td>${option.activationCost}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>There is no options yet.</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="panel-body">
                                Available options:
                                <c:choose>
                                    <c:when test="${not empty availableOptions}">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>Title</th>
                                                    <th>Cost</th>
                                                    <th>Activation cost</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="option" items="${availableOptions}">
                                                    <tr>
                                                        <td>
                                                            <a onclick="addTariffOption(${option.id},${tariff.id})"
                                                               style="cursor: pointer">
                                                                <i class="fa fa-plus fa-fw"></i>
                                                            </a>
                                                        </td>
                                                        <td>${option.title}</td>
                                                        <td>${option.cost}</td>
                                                        <td>${option.activationCost}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>There is no options yet.</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
