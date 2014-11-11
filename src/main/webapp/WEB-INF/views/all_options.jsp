<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script>
        $()
    </script>
</head>

<body>
<jsp:include page="navigation.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-10">
            <h2 class="page-header">All Options</h2>
        </div>
        <!-- /.col-lg-12 -->
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Existing options
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="deleteSuccess" class="alert alert-success alert-dismissable" style="display: none">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            Deleted.
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Status</th>
                                    <th>Title</th>
                                    <th>Cost</th>
                                    <th>Activation cost</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="option" items="${optionList}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/option/delete?id=${option.id}">
                                                <i id="deleteOption" class="fa fa-times fa-fw"></i>
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
                        Add new option
                    </div>
                    <!-- /.panel-heading -->
                    <form:form action="${pageContext.request.contextPath}/option/all" commandName="optionBean">
                        <div class="panel-body">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    Title:
                                    <form:input path="title" name="title" type="text" class="form-control"
                                                placeholder="Enter title"
                                                id="inputError"/>
                                    <form:errors path="title" cssClass="error"/>
                                </div>
                                <div class="form-group">
                                    Cost:
                                    <form:input path="cost" name="cost" type="number" class="form-control"
                                                placeholder="Enter cost"/>
                                    <form:errors path="cost" cssClass="error"/>
                                </div>
                                <div class="form-group">
                                    Activation Cost:
                                    <form:input path="activationCost" name="activationCost" type="number"
                                                class="form-control"
                                                placeholder="Enter activation cost"/>
                                    <form:errors path="activationCost" cssClass="error"/>
                                </div>
                                <input type="submit" class="btn btn-primary" value="OK">
                            </div>
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>Required options</label>
                                    <c:forEach var="option" items="${optionList}">
                                        <div class="checkbox requiredOptions">
                                            <label>
                                                <input name="requiredOptions" value="${option.id}"
                                                       type="checkbox">${option.title}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>Incompatible options</label>
                                    <c:forEach var="option" items="${optionList}">
                                        <div class="checkbox incompatibleOptions">
                                            <label>
                                                <input name="incompatibleOptions" value="${option.id}"
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
    <!-- /#wrapper -->
    <script>
        $(document).ready(function () {
            $('#dataTables-example').dataTable();
        });
    </script>
</div>
</body>

</html>
