<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script>
        function deleteTariff(id) {
            $.ajax({
                url: 'delete?id=' + id,
                type: 'GET',
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        location.href = "all";
                    } else {
                        $('#deleteError').prepend(data).show();
                    }

                }
            });
        }
    </script>
</head>
<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-10">
            <h2 class="page-header">All tariffs</h2>
        </div>
        <!-- /.col-lg-12 -->
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Existing tariffs
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="deleteError" class="alert alert-danger alert-dismissable" style="display: none">
                        </div>
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
                        <!-- /.table-responsive -->
                    </div>
                    <!-- /.panel-body -->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Add new tariff
                    </div>
                    <form:form action="${pageContext.request.contextPath}/admin/tariff/add" commandName="tariffBean">
                        <div class="panel-body">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Forms</label>
                                    Title:
                                    <form:input path="title" name="title" type="text" class="form-control"
                                                placeholder="Enter title"/>
                                    <form:errors path="title" cssClass="error"/>
                                </div>
                                <div class="form-group">
                                    Cost:
                                    <form:input path="cost" name="cost" type="number" class="form-control"
                                                placeholder="Enter cost"/>
                                    <form:errors path="cost" cssClass="error"/>
                                </div>
                                <input type="submit" class="btn btn-primary" value="OK">
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Options</label>
                                    <c:forEach var="option" items="${optionList}">
                                        <div class="checkbox">
                                            <label>
                                                <input name="options" value="${option.id}"
                                                       type="checkbox">${option.title}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>

            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
</div>
</body>

</html>
