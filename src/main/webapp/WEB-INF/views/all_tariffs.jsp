<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>All tariffs</title>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-10">
            <h2 class="page-header">All tariffs</h2>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Existing tariffs
                    </div>
                    <div class="panel-body">
                        <div id="deleteError" class="alert alert-danger alert-dismissable" style="display: none">
                        </div>
                        <c:choose>
                            <c:when test="${not empty tariffList}">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>Status</th>
                                            <th>Title</th>
                                            <th>Cost</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="tariff" items="${tariffList}">
                                            <tr>
                                                <td>
                                                    <a class="deleteTariff" onclick="deleteTariff(${tariff.id})">
                                                        <i class="fa fa-times fa-fw"></i>
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="edit?id=${tariff.id}">
                                                            ${tariff.title}
                                                    </a>
                                                </td>
                                                <td>${tariff.cost}</td>
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
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Add new tariff
                    </div>
                    <form:form action="${pageContext.request.contextPath}/tariff/add" commandName="tariffBean"
                               onsubmit="addTariff();return false;">
                        <div class="panel-body">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger alert-dismissable">
                                        ${error}
                                </div>
                            </c:if>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    Title:
                                    <form:errors path="title" cssClass="error"/>
                                    <form:input path="title" name="title" type="text" class="form-control"
                                                placeholder="Enter title"/>
                                </div>
                                <div class="form-group">
                                    Cost:
                                    <span id="costError" class="error" style="display: none"></span>
                                    <form:errors path="cost" cssClass="error"/>
                                    <form:input path="cost" name="cost" type="number" class="form-control"
                                                placeholder="Enter cost"/>
                                </div>
                                <input type="submit" class="btn btn-primary" value="OK">
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Options</label>
                                    <c:choose>
                                        <c:when test="${not empty optionList}">
                                            <c:forEach var="option" items="${optionList}">
                                                <div class="checkbox">
                                                    <label>
                                                        <input name="options" value="${option.id}"
                                                               type="checkbox">${option.title}
                                                    </label>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div>There is no options yet.</div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
